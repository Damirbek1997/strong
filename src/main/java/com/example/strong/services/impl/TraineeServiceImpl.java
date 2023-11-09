package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
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
import java.util.stream.Collectors;

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
    public TraineeModel getByUsername(String username) {
        TraineeModel traineeModel = traineeMapper.toModel(getEntityByUsername(username));
        log.debug("Getting Trainee: {} by username {}", traineeModel, username);
        return traineeModel;
    }

    @Override
    public Trainee getEntityByUsername(String username) {
        Trainee trainee = traineeRepository.findByUsername(username);

        if (trainee != null) {
            return trainee;
        }

        log.error("There is no Trainee with username {}", username);
        throw new BadRequestException("There is no Trainee with username: " + username);
    }

    @Override
    @Transactional
    public ResponseCredentialsModel create(CreateTraineeModel createTraineeModel) {
        validateFields(createTraineeModel);

        Trainee trainee = new Trainee();
        trainee.setFirstName(createTraineeModel.getFirstName());
        trainee.setLastName(createTraineeModel.getLastName());
        trainee.setUsername(userService.generateUsername(createTraineeModel.getFirstName(), createTraineeModel.getLastName()));
        trainee.setPassword(userService.generatePassword());
        trainee.setActive(true);
        trainee.setUsername(userService.getUniqueUsername(trainee.getUsername()));

        if (createTraineeModel.getBirthday() != null) {
            trainee.setBirthday(createTraineeModel.getBirthday());
        }

        if (createTraineeModel.getAddress() != null) {
            trainee.setAddress(createTraineeModel.getAddress());
        }


        traineeRepository.save(trainee);
        log.info("Created Trainee with model {}", createTraineeModel);
        return ResponseCredentialsModel.builder()
                .username(trainee.getUsername())
                .password(trainee.getPassword())
                .build();
    }

    @Override
    @Transactional
    public TraineeModel update(Long id, UpdateTraineeModel updateTraineeModel) {
        validateFields(updateTraineeModel);

        Trainee trainee = getEntityById(id);
        trainee.setFirstName(updateTraineeModel.getFirstName());
        trainee.setLastName(updateTraineeModel.getLastName());

        if (updateTraineeModel.getBirthday() == null) {
            trainee.setBirthday(updateTraineeModel.getBirthday());
        }

        if (updateTraineeModel.getAddress() != null) {
            trainee.setAddress(updateTraineeModel.getAddress());
        }

        String username = userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName());
        trainee.setUsername(userService.getUniqueUsername(username));

        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.save(trainee));
        log.info("Updated Trainee with model {}", updateTraineeModel);
        return traineeModel;
    }

    @Override
    public List<ResponseTrainerModel> updateTrainerList(Long id, List<String> usernames) {
        Trainee trainee = getEntityById(id);

        List<Trainer> trainer = trainerService.getAllEntitiesByUsernames(usernames);
        trainee.setTrainers(new HashSet<>(trainer));
        traineeRepository.save(trainee);

        log.info("Added Trainee's trainer list with usernames: {}", usernames);
        return trainer.stream()
                .map(trainerMapper::toResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        traineeRepository.deleteById(id);
        log.info("Deleted Trainee with id {}", id);
    }

    private Trainee getEntityById(Long id) {
        Optional<Trainee> traineeOptional = traineeRepository.findById(id);

        if (traineeOptional.isPresent()) {
            return traineeOptional.get();
        }

        log.error("There is no Trainee with id {}", id);
        throw new BadRequestException("There is no Trainee with id: " + id);
    }

    private void validateFields(CreateTraineeModel createTraineeModel) {
        userService.validateFields(createTraineeModel);
    }

    private void validateFields(UpdateTraineeModel updateTraineeModel) {
        userService.validateFields(updateTraineeModel);
    }
}
