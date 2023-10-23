package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.entities.User;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.UserModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.models.crud.UpdateUserModel;
import com.example.strong.repository.TraineeRepository;
import com.example.strong.services.TrainerService;
import com.example.strong.services.UserService;
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
    TraineeRepository traineeRepository;
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

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        when(traineeRepository.findAll())
                .thenReturn(Collections.singletonList(trainee));
        when(traineeMapper.toModelList(trainees))
                .thenReturn(Collections.singletonList(traineeModel));

        List<TraineeModel> traineeModelList = traineeService.getAll();

        assertEquals(1, traineeModelList.size());
        verify(traineeRepository).findAll();
    }

    @Test
    void getAllByIn_withValidListOfTrainerIds_shouldReturnAllTraineeModels() {
        Date birthDate = new Date();

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        List<Long> traineeIds = new ArrayList<>();
        traineeIds.add(1L);

        when(traineeRepository.findAllByIdIn(traineeIds))
                .thenReturn(Collections.singletonList(trainee));
        when(traineeMapper.toModelList(trainees))
                .thenReturn(Collections.singletonList(traineeModel));

        List<TraineeModel> traineeModelList = traineeService.getAllByIn(traineeIds);

        assertEquals(1, traineeModelList.size());
        verify(traineeRepository).findAllByIdIn(traineeIds);
    }

    @Test
    void getById_withValidId_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Optional<Trainee> traineeOptional = Optional.of(trainee);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        when(traineeRepository.findById(1L))
                .thenReturn(traineeOptional);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.getById(1L);
        assertEquals(traineeModel, response);
    }

    @Test
    void getByUsername_withValidUsername_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        when(traineeRepository.findByUserUsername("Ivan.Ivanov"))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.getByUsername("Ivan.Ivanov");
        assertEquals(traineeModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setFirstName("Ivan");
        createUserModel.setLastName("Ivanov");

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setCreateUserModel(createUserModel);
        createTraineeModel.setAddress("Moscow");
        createTraineeModel.setBirthday(birthDate);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        when(userService.create(createUserModel))
                .thenReturn(user);

        when(traineeRepository.save(any()))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.create(createTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void create_withValidDataAndListTrainerIds_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(1L);

        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setFirstName("Ivan");
        createUserModel.setLastName("Ivanov");

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setCreateUserModel(createUserModel);
        createTraineeModel.setAddress("Moscow");
        createTraineeModel.setBirthday(birthDate);
        createTraineeModel.setTrainerIds(trainerIds);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        User user2 = new User();
        user2.setId(1L);
        user2.setFirstName("Trainer");
        user2.setLastName("Trainer");
        user2.setUsername("Trainer.Trainer");
        user2.setIsActive(true);

        UserModel userModel2 = new UserModel();
        userModel2.setId(1L);
        userModel2.setFirstName("Trainer");
        userModel2.setLastName("Trainer");
        userModel2.setUsername("Trainer.Trainer");
        userModel2.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel2);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<TrainerModel> trainerModels = new ArrayList<>();
        trainerModels.add(trainerModel);

        when(userService.create(createUserModel))
                .thenReturn(user);
        when(trainerService.getAllIn(trainerIds))
                .thenReturn(trainerModels);
        when(trainerMapper.toEntityList(trainerModels))
                .thenReturn(trainers);

        when(traineeRepository.save(any()))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.create(createTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void update_withValidDataAndUser_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setId(1L);
        updateUserModel.setFirstName("Petya");
        updateUserModel.setLastName("Petrov");

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setId(1L);
        updateTraineeModel.setUpdateUserModel(updateUserModel);
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("Bishkek");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Bishkek");

        when(traineeRepository.findById(updateTraineeModel.getId()))
                .thenReturn(Optional.of(trainee));
        when(userService.update(updateUserModel))
                .thenReturn(user);

        when(traineeRepository.save(any()))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.update(updateTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void update_withValidDataListTrainerIds_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(2L);

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setId(1L);
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("Bishkek");
        updateTraineeModel.setTrainerIds(trainerIds);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setUserModel(userModel);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Bishkek");

        User trainerUser = new User();
        trainerUser.setId(2L);
        trainerUser.setFirstName("Vadim");
        trainerUser.setLastName("Vadimov");
        trainerUser.setUsername("Vadim.Vadimov");
        trainerUser.setIsActive(true);

        UserModel trainerUserModel = new UserModel();
        trainerUserModel.setId(2L);
        trainerUserModel.setFirstName("Vadim");
        trainerUserModel.setLastName("Vadimov");
        trainerUserModel.setUsername("Vadim.Vadimov");
        trainerUserModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(trainerUser);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setUserModel(trainerUserModel);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<TrainerModel> trainerModels = new ArrayList<>();
        trainerModels.add(trainerModel);

        when(traineeRepository.findById(updateTraineeModel.getId()))
                .thenReturn(Optional.of(trainee));

        when(trainerService.getAllIn(trainerIds))
                .thenReturn(trainerModels);
        when(trainerMapper.toEntityList(trainerModels))
                .thenReturn(trainers);

        when(traineeRepository.save(any()))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.update(updateTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void deleteById_withValidId_shouldReturnVoid() {
        doNothing()
                .when(traineeRepository)
                .deleteById(1L);
        traineeService.deleteById(1L);
        verify(traineeRepository).deleteById(1L);
    }

    @Test
    void deleteByUsername_withValidUsername_shouldReturnVoid() {
        String username = "Ivan.Ivanov";
        doNothing()
                .when(traineeRepository)
                .deleteByUserUsername(username);
        traineeService.deleteByUsername(username);
        verify(traineeRepository).deleteByUserUsername(username);
    }
}