package com.example.strong.services.impl;

import com.example.strong.entities.*;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.mappers.impl.TrainingMapper;
import com.example.strong.models.*;
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

        User user = new User();
        user.setFirstName("Petya");
        user.setLastName("Petrov");
        user.setUsername("Petya.Petrov");
        user.setIsActive(true);

        User user2 = new User();
        user2.setFirstName("Ivan");
        user2.setLastName("Ivanov");
        user2.setUsername("Ivan.Ivanov");
        user2.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        UserModel userModel2 = new UserModel();
        userModel2.setFirstName("Ivan");
        userModel2.setLastName("Ivanov");
        userModel2.setUsername("Ivan.Ivanov");
        userModel2.setIsActive(true);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setUserModel(userModel);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel2);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);

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

        List<TrainingModel> trainingModels = trainingService.getAll();

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).findAll();
    }

    @Test
    void getAllTraineesByUsername_withValidUsername_shouldReturnAllTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        User user = new User();
        user.setFirstName("Petya");
        user.setLastName("Petrov");
        user.setUsername("Petya.Petrov");
        user.setIsActive(true);

        User user2 = new User();
        user2.setFirstName("Ivan");
        user2.setLastName("Ivanov");
        user2.setUsername("Ivan.Ivanov");
        user2.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        UserModel userModel2 = new UserModel();
        userModel2.setFirstName("Ivan");
        userModel2.setLastName("Ivanov");
        userModel2.setUsername("Ivan.Ivanov");
        userModel2.setIsActive(true);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setUserModel(userModel);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel2);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);

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

        List<TrainingModel> trainingModels = trainingService.getAllTraineesByUsername(username);

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).getAllTraineesByUsername(username);
    }

    @Test
    void getAllTrainersByUsername_withValidUsername_shouldReturnAllTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        User user = new User();
        user.setFirstName("Petya");
        user.setLastName("Petrov");
        user.setUsername("Petya.Petrov");
        user.setIsActive(true);

        User user2 = new User();
        user2.setFirstName("Ivan");
        user2.setLastName("Ivanov");
        user2.setUsername("Ivan.Ivanov");
        user2.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        UserModel userModel2 = new UserModel();
        userModel2.setFirstName("Ivan");
        userModel2.setLastName("Ivanov");
        userModel2.setUsername("Ivan.Ivanov");
        userModel2.setIsActive(true);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setUserModel(userModel);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel2);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);

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

        when(trainingRepository.getAllTraineesByUsername(username))
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAllTraineesByUsername(username);

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).getAllTraineesByUsername(username);
    }

    @Test
    void getById_withValidId_shouldReturnTrainingModel() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        User user = new User();
        user.setFirstName("Petya");
        user.setLastName("Petrov");
        user.setUsername("Petya.Petrov");
        user.setIsActive(true);

        User user2 = new User();
        user2.setFirstName("Ivan");
        user2.setLastName("Ivanov");
        user2.setUsername("Ivan.Ivanov");
        user2.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        UserModel userModel2 = new UserModel();
        userModel2.setFirstName("Ivan");
        userModel2.setLastName("Ivanov");
        userModel2.setUsername("Ivan.Ivanov");
        userModel2.setIsActive(true);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setUserModel(userModel);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel2);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);

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
        createTrainingModel.setTrainingTypeId(1L);
        createTrainingModel.setTrainingDate(trainingDate);
        createTrainingModel.setTrainingName("Training");
        createTrainingModel.setTrainingDuration(10L);

        User user = new User();
        user.setFirstName("Petya");
        user.setLastName("Petrov");
        user.setUsername("Petya.Petrov");
        user.setIsActive(true);

        User user2 = new User();
        user2.setFirstName("Ivan");
        user2.setLastName("Ivanov");
        user2.setUsername("Ivan.Ivanov");
        user2.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        UserModel userModel2 = new UserModel();
        userModel2.setFirstName("Ivan");
        userModel2.setLastName("Ivanov");
        userModel2.setUsername("Ivan.Ivanov");
        userModel2.setIsActive(true);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setUserModel(userModel);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel2);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);

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

        when(traineeService.getById(1L))
                .thenReturn(traineeModel);
        when(trainerService.getById(1L))
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

        TrainingModel response = trainingService.create(createTrainingModel);
        assertEquals(trainingModel, response);
    }
}