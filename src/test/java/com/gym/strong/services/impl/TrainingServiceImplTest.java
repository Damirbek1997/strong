package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.entities.Training;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.TrainingMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.TrainingModel;
import com.gym.strong.models.crud.CreateTrainingModel;
import com.gym.strong.repository.TrainingDao;
import com.gym.strong.services.TraineeService;
import com.gym.strong.services.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {
    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    TrainingDao trainingDao;
    @Mock
    TraineeService traineeService;
    @Mock
    TrainerService trainerService;
    @Mock
    TraineeMapper traineeMapper;
    @Mock
    TrainerMapper trainerMapper;
    @Mock
    TrainingMapper trainingMapper;

    @Test
    void getAll_shouldReturnAllTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Petya");
        trainee.setLastName("Petrov");
        trainee.setUsername("Petya.Petrov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        Training training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Training");
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(10L);

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTraineeModel(traineeModel);
        trainingModel.setTrainerModel(trainerModel);
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training);

        when(trainingDao.getAll())
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAll();

        assertEquals(1, trainingModels.size());
        verify(trainingDao).getAll();
    }

    @Test
    void getById_withValidId_shouldReturnTrainingModel() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Petya");
        trainee.setLastName("Petrov");
        trainee.setUsername("Petya.Petrov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        Training training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Training");
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(10L);

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTraineeModel(traineeModel);
        trainingModel.setTrainerModel(trainerModel);
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        when(trainingDao.getById(1L))
                .thenReturn(training);
        when(trainingMapper.toModel(training))
                .thenReturn(trainingModel);

        TrainingModel response = trainingService.getById(1L);
        assertEquals(trainingModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTrainingModel() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        CreateTrainingModel createTrainingModel = new CreateTrainingModel();
        createTrainingModel.setTraineeId(1L);
        createTrainingModel.setTrainerId(1L);
        createTrainingModel.setTrainingDate(trainingDate);
        createTrainingModel.setTrainingName("Training");
        createTrainingModel.setTrainingDuration(10L);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Petya");
        trainee.setLastName("Petrov");
        trainee.setUsername("Petya.Petrov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        Training training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Training");
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(10L);

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTraineeModel(traineeModel);
        trainingModel.setTrainerModel(trainerModel);
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        when(traineeService.getById(1L))
                .thenReturn(traineeModel);
        when(trainerService.getById(1L))
                .thenReturn(trainerModel);
        when(traineeMapper.toEntity(traineeModel))
                .thenReturn(trainee);
        when(trainerMapper.toEntity(trainerModel))
                .thenReturn(trainer);

        doNothing()
                .when(trainingDao)
                .save(any());
        when(trainingMapper.toModel(any()))
                .thenReturn(trainingModel);

        TrainingModel response = trainingService.create(createTrainingModel);
        assertEquals(trainingModel, response);
    }
}