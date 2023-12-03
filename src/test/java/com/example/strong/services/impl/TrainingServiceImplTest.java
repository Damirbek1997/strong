package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.entities.Training;
import com.example.strong.entities.TrainingType;
import com.example.strong.enums.WorkloadActionType;
import com.example.strong.mappers.impl.TrainingMapper;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.repository.TrainingRepository;
import com.example.strong.services.TraineeService;
import com.example.strong.services.TrainerService;
import com.example.strong.services.WorkloadService;
import com.example.strong.specifications.TrainingSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    TrainingMapper trainingMapper;
    @Mock
    TrainingSpecification trainingSpecification;
    @Mock
    WorkloadService workloadService;

    @Test
    void getAllByTraineesUsername_withValidUsername_shouldReturnTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        Date periodFrom = new Date();
        Date periodTo = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setActive(true);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("TypeName");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("TypeName");

        Training training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Training");
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(10L);
        training.setTrainingType(trainingType);

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName(trainer.getFirstName());
        trainingModel.setTrainingTypeModel(trainingTypeModel);
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training);
        String username = "Petya.Petrov";

        Specification specification = mock(Specification.class);
        when(trainingSpecification.getTraineeSpecificationBy(username, periodFrom, periodTo, trainer.getFirstName(), trainingType.getId()))
                .thenReturn(specification);

        when(trainingRepository.findAll(specification))
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAllByTraineeUsername(username, periodFrom, periodTo, trainer.getFirstName(), trainingType.getId());

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).findAll(specification);
    }

    @Test
    void getAllByTrainersUsername_withValidUsername_shouldReturnAllTrainingModels() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        Date periodFrom = new Date();
        Date periodTo = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setActive(true);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("TypeName");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("TypeName");

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
        trainingModel.setTrainerName(trainer.getFirstName());
        trainingModel.setTrainingTypeModel(trainingTypeModel);
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training);
        String username = "Trainer.Trainer";

        Specification specification = mock(Specification.class);
        when(trainingSpecification.getTrainerSpecificationBy(username, periodFrom, periodTo, trainee.getFirstName()))
                .thenReturn(specification);

        when(trainingRepository.findAll(specification))
                .thenReturn(Collections.singletonList(training));
        when(trainingMapper.toModelList(trainings))
                .thenReturn(Collections.singletonList(trainingModel));

        List<TrainingModel> trainingModels = trainingService.getAllByTrainerUsername(username, periodFrom, periodTo, trainee.getFirstName());

        assertEquals(1, trainingModels.size());
        verify(trainingRepository).findAll(specification);
    }

    @Test
    void create_withValidData_shouldReturnTrainingModel() {
        Date birthDate = new Date();
        Date trainingDate = new Date();

        CreateTrainingModel createTrainingModel = new CreateTrainingModel();
        createTrainingModel.setTraineeUsername("Ivan.Ivanov");
        createTrainingModel.setTrainerUsername("Trainer.Trainer");
        createTrainingModel.setTrainingDate(trainingDate);
        createTrainingModel.setTrainingName("Training");
        createTrainingModel.setTrainingDuration(10L);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("Trainer");
        trainer.setUsername("Trainer.Trainer");
        trainer.setActive(true);

        Training training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Training");
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(10L);

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName(trainer.getFirstName());
        trainingModel.setTrainingName("Training");
        trainingModel.setTrainingDate(trainingDate);
        trainingModel.setTrainingDuration(10L);

        when(traineeService.getEntityByUsername("Ivan.Ivanov"))
                .thenReturn(trainee);
        when(trainerService.getEntityByUsername("Trainer.Trainer"))
                .thenReturn(trainer);

        when(trainingRepository.save(any()))
                .thenReturn(training);
        doNothing()
                .when(workloadService)
                .create(any(Training.class), eq(WorkloadActionType.ADD));
        when(trainingMapper.toModel(any()))
                .thenReturn(trainingModel);

        TrainingModel response = trainingService.create(createTrainingModel);
        assertEquals(trainingModel, response);
    }
}