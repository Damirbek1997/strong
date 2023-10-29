package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.entities.Training;
import com.example.strong.entities.TrainingType;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.mappers.impl.TrainingMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.repository.TrainingRepository;
import com.example.strong.services.TraineeService;
import com.example.strong.services.TrainerService;
import com.example.strong.services.TrainingTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {
    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    TrainingRepository trainingRepository;
    @Mock
    TraineeService traineeService;
    @Mock
    TrainerService trainerService;
    @Mock
    TrainingTypeService trainingTypeService;
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

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setFirstName("Trainer");
        trainerModel.setLastName("Trainer");
        trainerModel.setUsername("Trainer.Trainer");
        trainerModel.setIsActive(true);

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

        when(trainingRepository.findAll())
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAll(null);

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).findAll();
    }

    @Test
    void getAllTraineesByUsername_withValidUsername_shouldReturnAllTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setFirstName("Trainer");
        trainerModel.setLastName("Trainer");
        trainerModel.setUsername("Trainer.Trainer");
        trainerModel.setIsActive(true);

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
        String username = "Petya.Petrov";

        when(trainingRepository.getAllTraineesByUsername(username))
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAllTraineesByUsername(username, null);

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).getAllTraineesByUsername(username);
    }

    @Test
    void getAllTrainersByUsername_withValidUsername_shouldReturnAllTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setFirstName("Trainer");
        trainerModel.setLastName("Trainer");
        trainerModel.setUsername("Trainer.Trainer");
        trainerModel.setIsActive(true);

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
        String username = "Ivan.Ivanov";

        when(trainingRepository.getAllTrainersByUsername(username))
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAllTrainersByUsername(username, null);

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).getAllTrainersByUsername(username);
    }

    @Test
    void getById_withValidId_shouldReturnTrainingModel() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setFirstName("Trainer");
        trainerModel.setLastName("Trainer");
        trainerModel.setUsername("Trainer.Trainer");
        trainerModel.setIsActive(true);

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

        when(trainingRepository.findById(1L))
                .thenReturn(Optional.of(training));
        when(trainingMapper.toModel(training))
                .thenReturn(trainingModel);

        TrainingModel response = trainingService.getById(1L, null);
        assertEquals(trainingModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTrainingModel() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        CreateTrainingModel createTrainingModel = new CreateTrainingModel();
        createTrainingModel.setTraineeId(1L);
        createTrainingModel.setTrainerId(1L);
        createTrainingModel.setTrainingTypeId(1L);
        createTrainingModel.setTrainingDate(trainingDate);
        createTrainingModel.setTrainingName("Training");
        createTrainingModel.setTrainingDuration(10L);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setFirstName("Trainer");
        trainerModel.setLastName("Trainer");
        trainerModel.setUsername("Trainer.Trainer");
        trainerModel.setIsActive(true);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        Training training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setTrainingName("Training");
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(10L);

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTraineeModel(traineeModel);
        trainingModel.setTrainerModel(trainerModel);
        trainingModel.setTrainingTypeModel(trainingTypeModel);
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        when(traineeService.getById(1L, null))
                .thenReturn(traineeModel);
        when(trainerService.getById(1L, null))
                .thenReturn(trainerModel);
        when(traineeMapper.toEntity(traineeModel))
                .thenReturn(trainee);
        when(trainerMapper.toEntity(trainerModel))
                .thenReturn(trainer);
        when(trainingTypeService.getById(1L))
                .thenReturn(trainingType);

        when(trainingRepository.save(any()))
                .thenReturn(training);
        when(trainingMapper.toModel(any()))
                .thenReturn(trainingModel);

        TrainingModel response = trainingService.create(createTrainingModel, null);
        assertEquals(trainingModel, response);
    }
}