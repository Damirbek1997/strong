package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.entities.User;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.UserModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
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

        when(traineeRepository.findAll())
                .thenReturn(Collections.singletonList(trainee));
        when(traineeMapper.toModelList(trainees))
                .thenReturn(Collections.singletonList(traineeModel));

        List<TraineeModel> traineeModelList = traineeService.getAll();

        assertEquals(1, traineeModelList.size());
        verify(traineeRepository).findAll();
    }

    @Test
    void getAllByIds_withValidListOfTrainerIds_shouldReturnAllTraineeModels() {
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

        when(traineeRepository.findAllByIdIn(traineeIds))
                .thenReturn(Collections.singletonList(trainee));
        when(traineeMapper.toModelList(trainees))
                .thenReturn(Collections.singletonList(traineeModel));

        List<TraineeModel> traineeModelList = traineeService.getAllByIds(traineeIds);

        assertEquals(1, traineeModelList.size());
        verify(traineeRepository).findAllByIdIn(traineeIds);
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

        Optional<Trainee> traineeOptional = Optional.of(trainee);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setIsActive(true);
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

        UserModel userModel = new UserModel();
        userModel.setId(1L);

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

        when(traineeRepository.findByUsername("Ivan.Ivanov"))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.getByUsername("Ivan.Ivanov");
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
        traineeModel.setUsername("Ivan.Ivanov1");
        traineeModel.setIsActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Moscow");

        when(userService.generateUsername(createTraineeModel.getFirstName(), createTraineeModel.getLastName()))
                .thenReturn(trainee.getUsername());
        when(userService.generatePassword())
                .thenReturn(any());

        when(traineeRepository.countByUsernameLike(trainee.getUsername()))
                .thenReturn(1L);

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

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setFirstName("Ivan");
        createTraineeModel.setLastName("Ivanov");
        createTraineeModel.setAddress("Moscow");
        createTraineeModel.setBirthday(birthDate);
        createTraineeModel.setTrainerIds(trainerIds);

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

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<TrainerModel> trainerModels = new ArrayList<>();
        trainerModels.add(trainerModel);

        when(userService.generateUsername(createTraineeModel.getFirstName(), createTraineeModel.getLastName()))
                .thenReturn(trainee.getUsername());
        when(userService.generatePassword())
                .thenReturn(any());

        when(traineeRepository.countByUsernameLike(trainee.getUsername()))
                .thenReturn(0L);

        when(trainerService.getAllByIds(trainerIds))
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

        when(traineeRepository.findById(updateTraineeModel.getId()))
                .thenReturn(Optional.of(trainee));

        when(userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName()))
                .thenReturn("Ivan.Ivanov");
        when(traineeRepository.countByUsernameLike(trainee.getUsername()))
                .thenReturn(0L);

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
        traineeModel.setAddress("Bishkek");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Vadim");
        trainer.setLastName("Vadimov");
        trainer.setUsername("Vadim.Vadimov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(2L);
        trainerModel.setFirstName("Vadim");
        trainerModel.setLastName("Vadimov");
        trainerModel.setUsername("Vadim.Vadimov");
        trainerModel.setIsActive(true);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<TrainerModel> trainerModels = new ArrayList<>();
        trainerModels.add(trainerModel);

        when(traineeRepository.findById(updateTraineeModel.getId()))
                .thenReturn(Optional.of(trainee));

        when(trainerService.getAllByIds(trainerIds))
                .thenReturn(trainerModels);
        when(trainerMapper.toEntityList(trainerModels))
                .thenReturn(trainers);

        when(userService.generateUsername(trainee.getFirstName(), trainee.getLastName()))
                .thenReturn(trainee.getUsername());
        when(traineeRepository.countByUsernameLike(trainee.getUsername()))
                .thenReturn(0L);

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
                .deleteByUsername(username);
        traineeService.deleteByUsername(username);
        verify(traineeRepository).deleteByUsername(username);
    }

    @Test
    void changePassword_withValidData_shouldReturnVoid() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setId(1L);
        userCredentialsModel.setNewPassword("newPassword");

        Trainee currentTrainee = new Trainee();
        currentTrainee.setId(1L);
        currentTrainee.setFirstName("Ivan");
        currentTrainee.setLastName("Ivanov");
        currentTrainee.setUsername("Ivan.Ivanov");
        currentTrainee.setIsActive(true);
        currentTrainee.setPassword("oldPassword");
        currentTrainee.setAddress("Moscow");

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setPassword("newPassword");
        trainee.setAddress("Moscow");

        when(traineeRepository.findById(1L))
                .thenReturn(Optional.of(currentTrainee));
        when(traineeRepository.save(any()))
                .thenReturn(trainee);

        traineeService.changePassword(userCredentialsModel);
        assertEquals(userCredentialsModel.getNewPassword(), trainee.getPassword());
    }

    @Test
    void activateById_withValidId_shouldReturnVoid() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(false);
        trainee.setAddress("Moscow");

        when(traineeRepository.findById(1L))
                .thenReturn(Optional.of(trainee));
        when(traineeRepository.save(any()))
                .thenReturn(trainee);

        traineeService.activateById(1L);
        assertEquals(true, trainee.getIsActive());
    }

    @Test
    void deactivateById_withValidId_shouldReturnVoid() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setAddress("Moscow");

        when(traineeRepository.findById(1L))
                .thenReturn(Optional.of(trainee));
        when(traineeRepository.save(any()))
                .thenReturn(trainee);

        traineeService.deactivateById(1L);
        assertEquals(false, trainee.getIsActive());
    }
}