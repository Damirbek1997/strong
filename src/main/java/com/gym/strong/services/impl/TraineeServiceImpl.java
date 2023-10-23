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
import lombok.extern.log4j.Log4j;

import java.util.HashSet;
import java.util.List;

@Log4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao traineeDao;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainerService trainerService;
    private final UserService userService;

    public TraineeServiceImpl(TraineeDao traineeDao, TraineeMapper traineeMapper, TrainerMapper trainerMapper, TrainerService trainerService,
                              UserService userService) {
        this.traineeDao = traineeDao;
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
        this.trainerService = trainerService;
        this.userService = userService;
    }

    @Override
    public List<TraineeModel> getAll() {
        List<Trainee> trainees = traineeDao.getAll();
        log.debug("Getting all Trainees: " + trainees);
        return traineeMapper.toModelList(trainees);
    }

    @Override
    public List<TraineeModel> getAll(List<Long> ids) {
        List<Trainee> trainees = traineeDao.getAllIn(ids);
        log.debug("Getting all Trainees: " + trainees + " by their ids: " + ids);
        return traineeMapper.toModelList(trainees);
    }

    @Override
    public TraineeModel getById(Long id) {
        Trainee trainee = traineeDao.getById(id);
        log.debug("Getting Trainee: " + trainee + " by his id: {}" + id);
        return traineeMapper.toModel(trainee);
    }

    @Override
    public TraineeModel create(CreateTraineeModel createTraineeModel) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(createTraineeModel.getFirstName());
        trainee.setLastName(createTraineeModel.getLastName());
        trainee.setUsername(userService.generateUsername(createTraineeModel.getFirstName(), createTraineeModel.getLastName()));
        trainee.setPassword(userService.generatePassword());
        trainee.setIsActive(true);

        if (createTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllIn(createTraineeModel.getTrainerIds());
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        traineeDao.save(trainee);
        log.info("Created Trainee with model " + createTraineeModel);
        log.debug("Generated username - " + trainee.getUsername() + " and password - " + trainee.getPassword() + " for Trainee: " + trainee.getId());
        return traineeMapper.toModel(trainee);
    }

    @Override
    public TraineeModel update(UpdateTraineeModel updateTraineeModel) {
        Trainee trainee = traineeDao.getById(updateTraineeModel.getId());
        String username = userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName(),
                trainee.getFirstName(), trainee.getLastName());

        trainee.setUsername(username);
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

        log.info("Updated Trainee with model " + updateTraineeModel);
        log.debug("Generated username - " + trainee.getUsername() + " for trainer: " + trainee.getId());
        return traineeMapper.toModel(trainee);
    }

    @Override
    public void deleteById(Long id) {
        traineeDao.delete(id);
        log.debug("Deleted trainee with id " + id);
    }
}
