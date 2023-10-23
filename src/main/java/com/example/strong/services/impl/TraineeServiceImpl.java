package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.repository.TraineeRepository;
import com.example.strong.services.TraineeService;
import com.example.strong.services.TrainerService;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainerService trainerService;
    private final UserService userService;

    @Override
    public List<TraineeModel> getAll() {
        return traineeMapper.toModelList(traineeRepository.findAll());
    }

    @Override
    public List<TraineeModel> getAllByIn(List<Long> ids) {
        return traineeMapper.toModelList(traineeRepository.findAllByIdIn(ids));
    }

    @Override
    public TraineeModel getById(Long id) {
        return traineeMapper.toModel(getEntityById(id));
    }

    @Override
    public TraineeModel getByUsername(String username) {
        return traineeMapper.toModel(traineeRepository.findByUserUsername(username));
    }

    @Override
    @Transactional
    public TraineeModel create(CreateTraineeModel createTraineeModel) {
        Trainee trainee = new Trainee();
        trainee.setUser(userService.create(createTraineeModel.getCreateUserModel()));
        trainee.setBirthday(createTraineeModel.getBirthday());
        trainee.setAddress(createTraineeModel.getAddress());

        if (createTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllIn(createTraineeModel.getTrainerIds());
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.save(trainee));
        log.info("Created Trainee with model {}", createTraineeModel);
        return traineeModel;
    }

    @Override
    @Transactional
    public TraineeModel update(UpdateTraineeModel updateTraineeModel) {
        Trainee trainee = getEntityById(updateTraineeModel.getId());

        if (updateTraineeModel.getUpdateUserModel() != null) {
            trainee.setUser(userService.update(updateTraineeModel.getUpdateUserModel()));
        }

        if (updateTraineeModel.getBirthday() == null) {
            trainee.setBirthday(updateTraineeModel.getBirthday());
        }

        if (updateTraineeModel.getAddress() != null) {
            trainee.setAddress(updateTraineeModel.getAddress());
        }

        if (updateTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllIn(updateTraineeModel.getTrainerIds());
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.save(trainee));
        log.info("Updated Trainee with model {}", updateTraineeModel);
        return traineeModel;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        traineeRepository.deleteById(id);
        log.info("Deleted Trainee with id {}", id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        traineeRepository.deleteByUserUsername(username);
        log.info("Deleted Trainee with username {}", username);
    }

    private Trainee getEntityById(Long id) {
        Optional<Trainee> traineeOptional = traineeRepository.findById(id);

        if (traineeOptional.isPresent()) {
            return traineeOptional.get();
        }

        log.error("There is no Trainee with id {}", id);
        throw new BadRequestException("There is no Trainee with id: " + id);
    }
}