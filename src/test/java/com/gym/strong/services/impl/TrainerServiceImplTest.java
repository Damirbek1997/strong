package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainer;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.models.crud.UpdateTrainerModel;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {
    @InjectMocks
    private TrainerServiceImpl trainerService;
    @Mock
    TrainerDao trainerDao;
    @Mock
    TrainerMapper trainerMapper;
    @Mock
    UserService userService;

    @Test
    void getAll_shouldReturnAllTrainerModels() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        when(trainerDao.getAll())
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAll();

        assertEquals(1, trainerModelList.size());
        verify(trainerDao).getAll();
    }

    @Test
    void getAllIn_withValidListOfTrainerIds_shouldReturnAllTrainerModels() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        List<Long> trainerIds = new ArrayList<>();
        trainerIds.add(1L);

        when(trainerDao.getAllIn(trainerIds))
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toModelList(trainers))
                .thenReturn(Collections.singletonList(trainerModel));

        List<TrainerModel> trainerModelList = trainerService.getAllIn(trainerIds);

        assertEquals(1, trainerModelList.size());
        verify(trainerDao).getAllIn(trainerIds);
    }

    @Test
    void getById_withValidId_shouldReturnTrainerModel() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        when(trainerDao.getById(1L))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.getById(1L);
        assertEquals(trainerModel, response);
    }

    @Test
    void create_withValidData_shouldReturnTrainerModel() {
        CreateTrainerModel createTrainerModel = new CreateTrainerModel();
        createTrainerModel.setFirstName("Ivan");
        createTrainerModel.setLastName("Ivanov");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setIsActive(true);

        when(trainerMapper.toEntity(createTrainerModel))
                .thenReturn(trainer);
        when(userService.isUsernameBusy(trainer.getUsername()))
                .thenReturn(false);

        doNothing()
                .when(trainerDao)
                .save(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.create(createTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void createAndRegenerateUsername_withValidData_shouldReturnTrainerModel() {
        CreateTrainerModel createTrainerModel = new CreateTrainerModel();
        createTrainerModel.setFirstName("Ivan");
        createTrainerModel.setLastName("Ivanov");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setIsActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov1");
        trainerModel.setIsActive(true);

        when(trainerMapper.toEntity(createTrainerModel))
                .thenReturn(trainer);
        when(userService.isUsernameBusy(trainer.getUsername()))
                .thenReturn(true);
        when(userService.regenerateUsername(trainer.getFirstName(), trainer.getLastName(), 1L))
                .thenReturn(trainer.getUsername() + 1);

        doNothing()
                .when(trainerDao)
                .save(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.create(createTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void update_withValidData_shouldReturnTrainerModel() {
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

        when(trainerDao.getById(updateTrainerModel.getId()))
                .thenReturn(trainer);
        when(userService.regenerateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName(),
                trainer.getFirstName(), trainer.getLastName()))
                .thenReturn(null);

        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.update(updateTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void updateAndRegenerateUsername_withValidData_shouldReturnTrainerModel() {
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

        when(trainerDao.getById(updateTrainerModel.getId()))
                .thenReturn(trainer);
        when(userService.regenerateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName(),
                trainer.getFirstName(), trainer.getLastName()))
                .thenReturn("Petya.Ivanov");

        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.update(updateTrainerModel);
        assertEquals(trainerModel, response);
    }
}