package com.gym.strong.controllers;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.entities.TrainingType;
import com.gym.strong.entities.User;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.TrainingTypeModel;
import com.gym.strong.models.UserModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.UpdateTraineeModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

class TraineeControllerTest {
//    @InjectMocks
//    private final TraineeController traineeController;

    private CreateTraineeModel createTraineeModel;
    private UpdateTraineeModel updateTraineeModel;
    private TraineeModel traineeModel;
    private Trainee trainee;
    private List<Long> trainerIds;

    private UserModel traineeUserModel;
    private User traineeUser;
    private UserModel trainerUserModel;
    private User trainerUser;

    private TrainerModel trainerModel;
    private Trainer trainer;
    private HashSet<TrainerModel> trainerModels;
    private HashSet<Trainer> trainers;

    private TrainingTypeModel trainingTypeModel;
    private TrainingType trainingType;

    private Date birthDate = new Date();

    @BeforeEach
    public void beforeEach() {
        trainerIds = new ArrayList<>();
        trainerIds.add(1L);

        createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setUserId(1L);
        createTraineeModel.setBirthday(birthDate);
        createTraineeModel.setAddress("address");
        createTraineeModel.setTrainerIds(trainerIds);

        trainerIds.add(2L);

        updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setId(1L);
        updateTraineeModel.setUserId(1L);
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("address");
        updateTraineeModel.setTrainerIds(trainerIds);

        traineeUserModel = new UserModel();
        traineeUserModel.setId(1L);
        traineeUserModel.setFirstName("Trainee");
        traineeUserModel.setLastName("Trainee");
        traineeUserModel.setIsActive(true);
        traineeUserModel.setUsername("Trainee");
        traineeUserModel.setPassword("Trainee");

        traineeUser = new User();
        traineeUser.setId(1L);
        traineeUser.setFirstName("Trainee");
        traineeUser.setLastName("Trainee");
        traineeUser.setIsActive(true);
        traineeUser.setUsername("Trainee");
        traineeUser.setPassword("Trainee");

        trainerUserModel = new UserModel();
        trainerUserModel.setId(1L);
        trainerUserModel.setFirstName("Trainer");
        trainerUserModel.setLastName("Trainer");
        trainerUserModel.setIsActive(true);
        trainerUserModel.setUsername("Trainer");
        trainerUserModel.setPassword("Trainer");

        trainerUser = new User();
        trainerUser.setId(1L);
        trainerUser.setFirstName("Trainer");
        trainerUser.setLastName("Trainer");
        trainerUser.setIsActive(true);
        trainerUser.setUsername("Trainer");
        trainerUser.setPassword("Trainer");

        trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("Fitness");

        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("Fitness");

        trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setAddress("address");
        trainerModel.setBirthday(birthDate);
        trainerModel.setUserModel(trainerUserModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setAddress("address");
        trainer.setBirthday(birthDate);
        trainer.setUser(trainerUser);
        trainer.setTrainingType(trainingType);

        trainerModels = new HashSet<>();
        trainers = new HashSet<>();
        trainerModels.add(trainerModel);
        trainers.add(trainer);

        traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("address");
        traineeModel.setUserModel(traineeUserModel);
        traineeModel.setTrainerModels(trainerModels);

        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setBirthday(birthDate);
        trainee.setAddress("address");
        trainee.setUser(traineeUser);
        trainee.setTrainers(trainers);
    }

    @AfterEach
    public void afterEach() {
        createTraineeModel = null;
        updateTraineeModel = null;
        traineeModel = null;
        trainee = null;
        trainerIds = null;

        traineeUserModel = null;
        traineeUser = null;
        trainerUserModel = null;
        trainerUser = null;

        trainerModel = null;
        trainer = null;
        trainerModels = null;
        trainers = null;

        trainingTypeModel = null;
        trainingType = null;
        birthDate = null;
    }

    @Test
    public List<TraineeModel> getAll() {
        return null;
    }

    @Test
    public TraineeModel getById(Long id) {
        return null;
    }

    @Test
    public TraineeModel create() {
        return null;
    }

    @Test
    public TraineeModel update() {
        return null;
    }

    @Test
    public void deleteBy() {

    }
}
