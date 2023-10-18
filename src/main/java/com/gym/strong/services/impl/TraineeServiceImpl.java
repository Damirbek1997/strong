package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.UserMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.UpdateTraineeModel;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.services.TraineeService;
import com.gym.strong.services.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao traineeDao;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final UserMapper userMapper;
    private final TrainerService trainerService;

    @Override
    public List<TraineeModel> getAll() {
        return traineeMapper.toModelList(traineeDao.getAll());
    }

    @Override
    public List<TraineeModel> getAll(List<Long> ids) {
        return traineeMapper.toModelList(traineeDao.getAllIn(ids));
    }

    @Override
    public TraineeModel getById(Long id) {
        return traineeMapper.toModel(traineeDao.getById(id));
    }

    @Override
    public TraineeModel create(CreateTraineeModel createTraineeModel) {
        log.info("Creating trainee with model {}", createTraineeModel);
        Trainee trainee = new Trainee();
        trainee.setUser(userMapper.toEntity(createTraineeModel.getCreateUserModel()));
        trainee.setBirthday(createTraineeModel.getBirthday());
        trainee.setAddress(createTraineeModel.getAddress());
        List<TrainerModel> trainerModels = trainerService.getAllIn(createTraineeModel.getTrainerIds());
        trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));

        if (traineeDao.isTraineeExistWith(trainee.getUser().getFirstName(), trainee.getUser().getLastName())) {
            trainee.getUser().setUsername(regenerateUsername(trainee.getUser().getFirstName(), trainee.getUser().getLastName(), 1L));
        }

        traineeDao.save(trainee);
        return traineeMapper.toModel(trainee);
    }

    @Override
    public TraineeModel update(Long id, UpdateTraineeModel updateTraineeModel) {
        log.info("Updating trainee with model {}", updateTraineeModel);
        Trainee trainee = traineeDao.getById(id);
        traineeDao.delete(id);

        if (updateTraineeModel.getUpdateUserModel() != null) {
            trainee.setUser(userMapper.updateUserData(trainee.getUser(), updateTraineeModel.getUpdateUserModel()));

            if (traineeDao.isTraineeExistWith(trainee.getUser().getFirstName(), trainee.getUser().getLastName())) {
                trainee.getUser().setUsername(regenerateUsername(trainee.getUser().getFirstName(), trainee.getUser().getLastName(), 1L));
            }
        }

        if (updateTraineeModel.getBirthday() != null) {
            trainee.setBirthday(updateTraineeModel.getBirthday());
        }

        if (updateTraineeModel.getAddress() != null) {
            trainee.setAddress(updateTraineeModel.getAddress());
        }

        if (updateTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllIn(updateTraineeModel.getTrainerIds());
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        traineeDao.save(trainee);
        return traineeMapper.toModel(trainee);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting trainee with id {}", id);
        traineeDao.delete(id);
    }

    private String regenerateUsername(String firstName, String lastName, Long count) {
        String username = firstName + "." + lastName + count;

        if (traineeDao.isTraineeExistWith(firstName, lastName)) {
            count++;
            regenerateUsername(firstName, lastName, count);
        }

        return username;
    }
}
