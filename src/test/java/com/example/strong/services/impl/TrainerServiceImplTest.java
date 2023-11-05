package com.example.strong.services.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.entities.TrainingType;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.TrainingTypeModel;
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
    void getAllInUsernameList_withValidUsernameList_shouldReturnTrainerList() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setActive(true);

        List<String> trainerUsernames = new ArrayList<>();
        trainerUsernames.add("Ivan.Ivanov");

        when(trainerRepository.findAllByUsernameIn(trainerUsernames))
                .thenReturn(Collections.singletonList(trainer));

        List<Trainer> response = trainerService.getAllEntitiesByUsernames(trainerUsernames);

        assertEquals(1, response.size());
        verify(trainerRepository).findAllByUsernameIn(trainerUsernames);
    }

    @Test
    void getAllNotBusyTrainers_shouldReturnResponseTrainerModelList() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setActive(true);
        trainer.setTrainingType(trainingType);

        ResponseTrainerModel responseTrainerModel = new ResponseTrainerModel();
        responseTrainerModel.setFirstName("Ivan");
        responseTrainerModel.setLastName("Ivanov");
        responseTrainerModel.setUsername("Ivan.Ivanov");
        responseTrainerModel.setSpecializationId(1L);

        when(trainerRepository.getAllNotBusyTrainers())
                .thenReturn(Collections.singletonList(trainer));
        when(trainerMapper.toResponseModel(trainer))
                .thenReturn(responseTrainerModel);

        List<ResponseTrainerModel> responseTrainerModels = trainerService.getAllNotBusyTrainers();

        assertEquals(1, responseTrainerModels.size());
        verify(trainerRepository).getAllNotBusyTrainers();
    }

    @Test
    void getByUsername_withValidUsername_shouldReturnTrainerModel() {
        String username = "Ivan.Ivanov";

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setActive(true);

        when(trainerRepository.findByUsername(username))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.getByUsername(username);
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

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setActive(true);
        trainer.setTrainingType(trainingType);

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

        ResponseCredentialsModel responseCredentialsModel = trainerService.create(createTrainerModel);
        assertEquals(responseCredentialsModel.getUsername(), trainer.getUsername());
    }

    @Test
    void update_withValidData_shouldReturnTrainerModel() {
        Long id = 1L;

        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setFirstName("Petya");
        updateTrainerModel.setLastName("Petrov");
        updateTrainerModel.setActive(true);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov");
        trainer.setActive(true);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Petya");
        trainerModel.setLastName("Petrov");
        trainerModel.setUsername("Petya.Petrov");
        trainerModel.setActive(true);

        when(trainerRepository.findById(id))
                .thenReturn(Optional.of(trainer));

        when(userService.generateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName()))
                .thenReturn(trainer.getUsername());
        when(trainerRepository.countByUsernameLike(trainer.getUsername()))
                .thenReturn(0L);

        when(trainerRepository.save(any()))
                .thenReturn(trainer);
        when(trainerMapper.toModel(trainer))
                .thenReturn(trainerModel);

        TrainerModel response = trainerService.update(id, updateTrainerModel);
        assertEquals(trainerModel, response);
    }

    @Test
    void update_withValidDataAndTrainingType_shouldReturnTrainerModel() {
        Long id = 1L;

        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setFirstName("Ivan");
        updateTrainerModel.setLastName("Ivanov");
        updateTrainerModel.setTrainingTypeId(2L);
        updateTrainerModel.setActive(true);

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
        trainer.setActive(true);
        trainer.setTrainingType(trainingType);

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Petya");
        trainerModel.setLastName("Petrov");
        trainerModel.setUsername("Petya.Petrov");
        trainerModel.setActive(true);
        trainerModel.setTrainingTypeId(1L);

        when(trainerRepository.findById(id))
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

        TrainerModel response = trainerService.update(id, updateTrainerModel);
        assertEquals(trainerModel, response);
    }
}