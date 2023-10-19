package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.UpdateTraineeModel;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.services.TraineeService;
import com.gym.strong.services.TrainerService;
import com.gym.strong.services.UserService;
import com.gym.strong.util.UserUtil;
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
    private final TrainerService trainerService;
    private final UserService userService;

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
        Trainee trainee = traineeMapper.toEntity(createTraineeModel);

        if (traineeDao.isTraineeExistWith(trainee.getUsername())) {
            trainee.setUsername(userService.regenerateUsername(trainee.getFirstName(), trainee.getLastName(), 1L));
        }

        if (createTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllIn(createTraineeModel.getTrainerIds());
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        traineeDao.save(trainee);
        log.info("Created trainee with model {}", createTraineeModel);
        return traineeMapper.toModel(trainee);
    }

    @Override
    public TraineeModel update(UpdateTraineeModel updateTraineeModel) {
        Trainee trainee = traineeDao.getById(updateTraineeModel.getId());
        String username = userService.regenerateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName(),
                trainee.getFirstName(), trainee.getLastName());

        if (updateTraineeModel.getFirstName() != null && updateTraineeModel.getLastName() != null) {
            trainee.setFirstName(updateTraineeModel.getFirstName());
            trainee.setLastName(updateTraineeModel.getLastName());
        }

        if (updateTraineeModel.getFirstName() == null) {
            trainee.setLastName(updateTraineeModel.getLastName());
        }

        if (updateTraineeModel.getLastName() == null) {
            trainee.setFirstName(updateTraineeModel.getFirstName());
        }

        trainee.setUsername(UserUtil.generateUsername(trainee.getFirstName(), trainee.getLastName()));

        if (username != null) {
            trainee.setUsername(username);
        }

        if (updateTraineeModel.getIsActive() != null) {
            trainee.setIsActive(updateTraineeModel.getIsActive());
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

        log.info("Updated trainee with model {}", updateTraineeModel);
        return traineeMapper.toModel(trainee);
    }

    @Override
    public void deleteById(Long id) {
        traineeDao.delete(id);
        log.info("Deleted trainee with id {}", id);
    }
}
