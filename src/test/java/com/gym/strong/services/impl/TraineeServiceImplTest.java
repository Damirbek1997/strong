package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.entities.User;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.TrainingTypeModel;
import com.gym.strong.models.UserModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.CreateUserModel;
import com.gym.strong.models.crud.UpdateTraineeModel;
import com.gym.strong.models.crud.UpdateUserModel;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.services.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    UserMapper userMapper;
    @Mock
    TrainerService trainerService;

    private Trainer trainer;
    private Trainee trainee;
    private TrainingType trainingType;
    private User trainerData;
    private User traineeData;

    private TrainerModel trainerModel;
    private TraineeModel traineeModel;
    private TrainingTypeModel trainingTypeModel;
    private UserModel trainerDataModel;
    private UserModel traineeDataModel;

    private CreateTraineeModel createTraineeModel;
    private UpdateTraineeModel updateTraineeModel;
    private CreateUserModel createUserModel;
    private UpdateUserModel updateUserModel;
    private Date birthDate;

    private List<Long> traineeIds;
    private List<Long> trainerIds;

    @BeforeEach
    void setUp() {
        birthDate = new Date();

        // Entities
        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("Bench-Press");

        traineeData = new User();
        traineeData.setId(1L);
        traineeData.setFirstName("Ivan");
        traineeData.setLastName("Ivanov");
        traineeData.setUsername("Ivan.Ivanov");
        traineeData.setIsActive(true);

        trainerData = new User();
        trainerData.setId(1L);
        trainerData.setFirstName("John");
        trainerData.setLastName("Smith");
        trainerData.setUsername("John.Smith");
        trainerData.setIsActive(true);

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(trainerData);
        trainer.setTrainingType(trainingType);

        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");
        trainee.setUser(traineeData);

        // Models
        trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("Bench-Press");

        traineeDataModel = new UserModel();
        traineeDataModel.setId(1L);
        traineeDataModel.setFirstName("Ivan");
        traineeDataModel.setLastName("Ivanov");
        traineeDataModel.setUsername("Ivan.Ivanov");
        traineeDataModel.setIsActive(true);

        trainerDataModel = new UserModel();
        trainerDataModel.setId(1L);
        trainerDataModel.setFirstName("John");
        trainerDataModel.setLastName("Smith");
        trainerDataModel.setUsername("John.Smith");
        trainerDataModel.setIsActive(true);

        trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(trainerDataModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");
        traineeModel.setUserModel(traineeDataModel);

        // create, update models
        createUserModel = new CreateUserModel();
        createUserModel.setFirstName("Ivan");
        createUserModel.setLastName("Ivanov");

        createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setAddress("Moscow");
        createTraineeModel.setCreateUserModel(createUserModel);
        createTraineeModel.setBirthday(birthDate);
//        createTraineeModel.setTrainerIds(birthDate);

        updateUserModel = new UpdateUserModel();
        updateUserModel.setId(1L);
        updateUserModel.setFirstName("A");
        updateUserModel.setLastName("B");
//        updateUserModel.setIsActive("B");

        updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setId(1L);
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("Bishkek");
        updateTraineeModel.setUpdateUserModel(updateUserModel);
//        updateTraineeModel.setTrainerIds(updateUserModel);

        traineeIds = new ArrayList<>();
        traineeIds.add(1L);
        traineeIds.add(2L);
        traineeIds.add(3L);

        trainerIds = new ArrayList<>();
        trainerIds.add(1L);
        trainerIds.add(2L);
        trainerIds.add(3L);
    }
    // cucumber, spok посмотреть на структуру тестов
    // assert должен быть
    @Test
    // name of methods must be informative
    void whenGetAll() {
        when(traineeDao.getAll())
                .thenReturn(Collections.singletonList(trainee));
        List<Trainee> trainees = traineeDao.getAll();

        when(traineeMapper.toModelList(trainees)).thenReturn(Collections.singletonList(traineeModel));
        List<TraineeModel> traineeModelList = traineeService.getAll(traineeIds);

        assertThat(traineeModelList).hasSize(1);
        verify(traineeService, times(1)).getAll(traineeIds);
    }

    @Test
    void getAllIn() {
        when(traineeDao.getAllIn(traineeIds)).thenReturn(Collections.singletonList(trainee));
        List<Trainee> trainees = traineeDao.getAllIn(traineeIds);

        assertThat(trainees).hasSize(1);
        verify(traineeDao).getAllIn(traineeIds);

        when(traineeService.getAll(traineeIds)).thenReturn(Collections.singletonList(traineeModel));
        List<TraineeModel> traineeModelList = traineeService.getAll(traineeIds);

        assertThat(traineeModelList).hasSize(1);
        verify(traineeService, times(1)).getAll(traineeIds);
    }

    @Test
    void getById() {
        when(traineeDao.getById(1L)).thenReturn(trainee);
        when(traineeMapper.toModel(trainee)).thenReturn(traineeModel);

        TraineeModel response = traineeService.getById(1L);
        // сравнивать по обьектам, а не полям (assertG, assertZ)
        assertEquals(1L, response.getId());
        assertEquals("Moscow", response.getAddress());
        assertEquals(birthDate, response.getBirthday());
        assertEquals(1L, response.getUserModel().getId());
        assertEquals("Ivan", response.getUserModel().getFirstName());
        assertEquals("Ivanov", response.getUserModel().getLastName());
        assertEquals("Ivan.Ivanov", response.getUserModel().getUsername());
        assertEquals(true, response.getUserModel().getIsActive());
    }

    @Test
    void create() {
        when(userMapper.toEntity(createUserModel)).thenReturn(traineeData);

        if (createTraineeModel.getTrainerIds() != null) {
            when(trainerService.getAllIn(trainerIds))
                    .thenReturn(Collections.singletonList(trainerModel));
            List<TrainerModel> trainerModels = trainerService.getAllIn(trainerIds);
            when(trainerMapper.toEntityList(trainerModels))
                    .thenReturn(Collections.singletonList(trainer));
        }

        doNothing()
                .when(traineeDao)
                .save(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.create(createTraineeModel);
        assertEquals(1L, response.getId());
        assertEquals("Moscow", response.getAddress());
        assertEquals(birthDate, response.getBirthday());
        assertEquals(1L, response.getUserModel().getId());
        assertEquals("Ivan", response.getUserModel().getFirstName());
        assertEquals("Ivanov", response.getUserModel().getLastName());
        assertEquals("Ivan.Ivanov", response.getUserModel().getUsername());
        assertEquals(true, response.getUserModel().getIsActive());
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}