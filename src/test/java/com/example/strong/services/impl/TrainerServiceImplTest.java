package com.example.strong.services.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.entities.TrainingType;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
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

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);
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

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(1L);

        when(trainerRepository.findAllByIdIn(trainerIds))
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAllByIds(trainerIds);

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

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        when(trainerRepository.getAllNotBusyTrainers())
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAllNotBusyTrainers();

        assertEquals(1, trainerModelList.size());
        verify(trainerRepository).getAllNotBusyTrainers();
    }

    @Test
    void getById_withValidId_shouldReturnTrainerModel() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);
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

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(trainerRepository.findByUsername("Ivan.Ivanov"))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.getByUsername("Ivan.Ivanov");
        assertEquals(trainerModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTrainerModel() {
        CreateTrainerModel createTrainerModel = new CreateTrainerModel();
        createTrainerModel.setFirstName("Ivan");
        createTrainerModel.setLastName("Ivanov");
        createTrainerModel.setTrainingTypeId(1L);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(userService.generateUsername(createTrainerModel.getFirstName(), createTrainerModel.getLastName()))
                .thenReturn(trainer.getUsername());
        when(userService.generatePassword())
                .thenReturn(any());

        when(trainingTypeService.getById(createTrainerModel.getTrainingTypeId()))
                .thenReturn(trainingType);
        when(trainerRepository.countByUsernameLike(trainer.getUsername()))
                .thenReturn(0L);

        when(trainerRepository.save(any()))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.create(createTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void update_withValidDataAnsUser_shouldReturnTrainerModel() {
        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setId(1L);
        updateTrainerModel.setFirstName("Petya");
        updateTrainerModel.setLastName("Petrov");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Petya");
        trainerModel.setLastName("Petrov");
        trainerModel.setUsername("Petya.Petrov");
        trainerModel.setIsActive(true);

        when(trainerRepository.findById(updateTrainerModel.getId()))
                .thenReturn(Optional.of(trainer));

        when(userService.generateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName()))
                .thenReturn(trainer.getUsername());
        when(trainerRepository.countByUsernameLike(trainer.getUsername()))
                .thenReturn(0L);

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

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Petya");
        trainerModel.setLastName("Petrov");
        trainerModel.setUsername("Petya.Petrov");
        trainerModel.setIsActive(true);
        trainerModel.setTrainingTypeModel(trainingTypeModel);

        when(trainerRepository.findById(updateTrainerModel.getId()))
                .thenReturn(Optional.of(trainer));
        when(trainingTypeService.getById(updateTrainerModel.getTrainingTypeId()))
                .thenReturn(trainingType);

        when(userService.generateUsername(trainer.getFirstName(), trainer.getLastName()))
                .thenReturn(trainer.getUsername());
        when(trainerRepository.countByUsernameLike(trainer.getUsername()))
                .thenReturn(0L);

        when(trainerRepository.save(any()))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.update(updateTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void changePassword_withValidData_shouldReturnVoid() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setId(1L);
        userCredentialsModel.setNewPassword("newPassword");

        Trainer currentTrainer = new Trainer();
        currentTrainer.setId(1L);
        currentTrainer.setFirstName("Ivan");
        currentTrainer.setLastName("Ivanov");
        currentTrainer.setUsername("Ivan.Ivanov");
        currentTrainer.setIsActive(true);
        currentTrainer.setPassword("oldPassword");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);
        trainer.setPassword("newPassword");

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(currentTrainer));
        when(trainerRepository.save(any()))
                .thenReturn(trainer);

        trainerService.changePassword(userCredentialsModel);
        assertEquals(userCredentialsModel.getNewPassword(), trainer.getPassword());
    }

    @Test
    void activateById_withValidId_shouldReturnVoid() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(false);

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any()))
                .thenReturn(trainer);

        trainerService.activateById(1L);
        assertEquals(true, trainer.getIsActive());
    }

    @Test
    void deactivateById_withValidId_shouldReturnVoid() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any()))
                .thenReturn(trainer);

        trainerService.deactivateById(1L);
        assertEquals(false, trainer.getIsActive());
    }
}