package com.example.strong.services.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.entities.TrainingType;
import com.example.strong.entities.User;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.models.UserModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.models.crud.UpdateUserModel;
import com.example.strong.repository.TrainerRepository;
import com.example.strong.services.TrainingTypeService;
import com.example.strong.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {
    @InjectMocks
    private TrainerServiceImpl trainerService;
    @Mock
    TrainerRepository trainerRepository;
    @Mock
    TrainerMapper trainerMapper;
    @Mock
    UserService userService;
    @Mock
    TrainingTypeService trainingTypeService;

    @Test
    void getAll_shouldReturnAllTrainerModels() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        when(trainerRepository.findAll())
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAll();

        assertEquals(1, trainerModelList.size());
        verify(trainerRepository).findAll();
    }

    @Test
    void getAllIn_withValidListOfTrainerIds_shouldReturnAllTrainerModels() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(1L);

        when(trainerRepository.findAllByIdIn(trainerIds))
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAllIn(trainerIds);

        assertEquals(1, trainerModelList.size());
        verify(trainerRepository).findAllByIdIn(trainerIds);
    }

    @Test
    void getAllNotBusyTrainers_shouldReturnAllTrainerModels() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        when(trainerRepository.findAllByUserIsActiveIsTrue())
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAllNotBusyTrainers();

        assertEquals(1, trainerModelList.size());
        verify(trainerRepository).findAllByUserIsActiveIsTrue();
    }

    @Test
    void getById_withValidId_shouldReturnTrainerModel() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.getById(1L);
        assertEquals(trainerModel, response);
    }

    @Test
    void getByUsername_withValidUsername_shouldReturnTrainerModel() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(trainerRepository.findByUserUsername("Ivan.Ivanov"))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.getByUsername("Ivan.Ivanov");
        assertEquals(trainerModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTrainerModel() {
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setFirstName("Ivan");
        createUserModel.setLastName("Ivanov");

        CreateTrainerModel createTrainerModel = new CreateTrainerModel();
        createTrainerModel.setCreateUserModel(createUserModel);
        createTrainerModel.setTrainingTypeId(1L);
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Ivan");
        userModel.setLastName("Ivanov");
        userModel.setUsername("Ivan.Ivanov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(userService.create(createUserModel))
                .thenReturn(user);
        when(trainingTypeService.getById(createTrainerModel.getTrainingTypeId()))
                .thenReturn(trainingType);

        when(trainerRepository.save(any()))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.create(createTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void update_withValidDataAnsUser_shouldReturnTrainerModel() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setId(1L);
        updateUserModel.setFirstName("Petya");
        updateUserModel.setLastName("Petrov");

        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setId(1L);
        updateTrainerModel.setUpdateUserModel(updateUserModel);

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);

        when(trainerRepository.findById(updateTrainerModel.getId()))
                .thenReturn(Optional.of(trainer));
        when(userService.update(updateUserModel))
                .thenReturn(user);

        when(trainerRepository.save(any()))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.update(updateTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void update_withValidDataAndTrainingType_shouldReturnTrainerModel() {
        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setId(1L);
        updateTrainerModel.setTrainingTypeId(2L);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(2L);
        trainingTypeModel.setTypeName("type2");

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);

        UserModel userModel = new UserModel();
        userModel.setFirstName("Petya");
        userModel.setLastName("Petrov");
        userModel.setUsername("Petya.Petrov");
        userModel.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setUserModel(userModel);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(trainerRepository.findById(updateTrainerModel.getId()))
                .thenReturn(Optional.of(trainer));
        when(trainingTypeService.getById(updateTrainerModel.getTrainingTypeId()))
                .thenReturn(trainingType);

        when(trainerRepository.save(any()))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.update(updateTrainerModel);
        assertEquals(trainerModel, response);
    }
}