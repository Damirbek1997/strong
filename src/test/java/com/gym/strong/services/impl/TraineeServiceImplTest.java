package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.UpdateTraineeModel;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.services.TrainerService;
import com.gym.strong.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {
    @InjectMocks
    private TraineeServiceImpl traineeService;
    @Mock
    TraineeDao traineeDao;
    @Mock
    TraineeMapper traineeMapper;
    @Mock
    TrainerMapper trainerMapper;
    @Mock
    TrainerService trainerService;
    @Mock
    UserService userService;

    // cucumber, spok посмотреть на структуру тестов
    @Test
    void getAll_shouldReturnAllTraineeModels() {
        Date birthDate = new Date();

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

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        when(traineeDao.getAll())
                .thenReturn(Collections.singletonList(trainee));
        when(traineeMapper.toModelList(trainees))
                .thenReturn(Collections.singletonList(traineeModel));

        List<TraineeModel> traineeModelList = traineeService.getAll();

        assertEquals(1, traineeModelList.size());
        verify(traineeDao).getAll();
    }

    @Test
    void getAllIn_withValidListOfTrainerIds_shouldReturnAllTraineeModels() {
        Date birthDate = new Date();

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

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        List<Long> traineeIds = new ArrayList<>();
        traineeIds.add(1L);

        when(traineeDao.getAllIn(traineeIds))
                .thenReturn(Collections.singletonList(trainee));
        when(traineeMapper.toModelList(trainees))
                .thenReturn(Collections.singletonList(traineeModel));

        List<TraineeModel> traineeModelList = traineeService.getAll(traineeIds);

        assertEquals(1, traineeModelList.size());
        verify(traineeDao).getAllIn(traineeIds);
    }

    @Test
    void getById_withValidId_shouldReturnTraineeModel() {
        Date birthDate = new Date();

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

        when(traineeDao.getById(1L))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.getById(1L);
        assertEquals(traineeModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setFirstName("Ivan");
        createTraineeModel.setLastName("Ivanov");
        createTraineeModel.setAddress("Moscow");
        createTraineeModel.setBirthday(birthDate);

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

        when(userService.generateUsername(trainee.getFirstName(), trainee.getLastName()))
                .thenReturn(trainee.getUsername());
        when(userService.generatePassword())
                .thenReturn("asdasd");

        doNothing()
                .when(traineeDao)
                .save(any());
        when(traineeMapper.toModel(any()))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.create(createTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void create_withValidDataAndListTrainerIds_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(1L);

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setFirstName("Ivan");
        createTraineeModel.setLastName("Ivanov");
        createTraineeModel.setAddress("Moscow");
        createTraineeModel.setBirthday(birthDate);
        createTraineeModel.setTrainerIds(trainerIds);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Trainer");
        trainerModel.setLastName("Trainer");
        trainerModel.setUsername("Trainer.Trainer");
        trainerModel.setIsActive(true);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<TrainerModel> trainerModels = new ArrayList<>();
        trainerModels.add(trainerModel);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");
        trainee.setTrainers(new HashSet<>(trainers));

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setTrainerModels(trainerModels);

        when(userService.generateUsername(trainee.getFirstName(), trainee.getLastName()))
                .thenReturn(trainee.getUsername());
        when(userService.generatePassword())
                .thenReturn("asdsa");
        when(trainerService.getAllIn(trainerIds))
                .thenReturn(trainerModels);
        when(trainerMapper.toEntityList(trainerModels))
                .thenReturn(trainers);

        doNothing()
                .when(traineeDao)
                .save(any());
        when(traineeMapper.toModel(any()))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.create(createTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void update_withValidData_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setId(1L);
        updateTraineeModel.setFirstName("Petya");
        updateTraineeModel.setLastName("Petrov");
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("Bishkek");

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
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Bishkek");

        when(traineeDao.getById(updateTraineeModel.getId()))
                .thenReturn(trainee);
        when(userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName(),
                trainee.getFirstName(), trainee.getLastName()))
                .thenReturn(trainee.getUsername());

        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.update(updateTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void update_withValidDataAndListTrainerIds_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(2L);

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setId(1L);
        updateTraineeModel.setFirstName("Petya");
        updateTraineeModel.setLastName("Petrov");
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("Bishkek");
        updateTraineeModel.setTrainerIds(trainerIds);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setIsActive(true);

        TrainerModel trainerModel2 = new TrainerModel();
        trainerModel2.setId(2L);
        trainerModel2.setFirstName("Trainer2");
        trainerModel2.setLastName("Trainer2");
        trainerModel2.setUsername("Trainer2.Trainer2");
        trainerModel2.setIsActive(true);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<TrainerModel> trainerModels = new ArrayList<>();
        trainerModels.add(trainerModel2);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");
        trainee.setTrainers(new HashSet<>(trainers));

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Bishkek");
        traineeModel.setTrainerModels(trainerModels);

        when(traineeDao.getById(updateTraineeModel.getId()))
                .thenReturn(trainee);
        when(userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName(),
                trainee.getFirstName(), trainee.getLastName()))
                .thenReturn(null);

        when(trainerService.getAllIn(trainerIds))
                .thenReturn(trainerModels);
        when(trainerMapper.toEntityList(trainerModels))
                .thenReturn(trainers);

        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.update(updateTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void deleteById_withValidId_shouldReturnVoid() {
        doNothing()
                .when(traineeDao)
                .delete(1L);
        traineeService.deleteById(1L);
        verify(traineeDao).delete(1L);
    }
}