package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.entities.TrainingType;
import com.example.strong.entities.User;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setActive(true);
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
    void create_withValidData_shouldReturnResponseCredentialsModel() {
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
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov1");
        traineeModel.setActive(true);
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

        ResponseCredentialsModel response = traineeService.create(createTraineeModel);
        assertEquals(traineeModel.getUsername(), response.getUsername());
    }

    @Test
    void update_withValidDataAndUser_shouldReturnTraineeModel() {
        Date birthDate = new Date();

        Long id = 1L;

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setFirstName("Petya");
        updateTraineeModel.setLastName("Petrov");
        updateTraineeModel.setBirthday(birthDate);
        updateTraineeModel.setAddress("Bishkek");

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setActive(true);
        traineeModel.setBirthday(birthDate);
        traineeModel.setAddress("Bishkek");

        when(traineeRepository.findById(id))
                .thenReturn(Optional.of(trainee));

        when(userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName()))
                .thenReturn("Ivan.Ivanov");
        when(traineeRepository.countByUsernameLike(trainee.getUsername()))
                .thenReturn(0L);

        when(traineeRepository.save(any()))
                .thenReturn(trainee);
        when(traineeMapper.toModel(trainee))
                .thenReturn(traineeModel);

        TraineeModel response = traineeService.update(id, updateTraineeModel);
        assertEquals(traineeModel, response);
    }

    @Test
    void updateTraineesTrainerList_withValidDataListTrainerUsernames_shouldReturnListResponseTrainerModel() {
        Date birthDate = new Date();
        Long id = 1L;

        List<String> trainerUsernames = new ArrayList<>();
        trainerUsernames.add("Petya.Petrov");

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("trainingTypeName");

        Trainer trainer = new Trainer();
        trainer.setId(2L);
        trainer.setFirstName("Petya");
        trainer.setLastName("Petrov");
        trainer.setUsername("Petya.Petrov");
        trainer.setActive(true);
        trainer.setTrainingType(trainingType);

        ResponseTrainerModel responseTrainerModel = new ResponseTrainerModel();
        responseTrainerModel.setFirstName("Petya");
        responseTrainerModel.setLastName("Petrov");
        responseTrainerModel.setUsername("Petya.Petrov");
        responseTrainerModel.setSpecializationId(1L);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);

        when(traineeRepository.findById(id))
                .thenReturn(Optional.of(trainee));
        when(trainerService.getAllEntitiesByUsernames(trainerUsernames))
                .thenReturn(trainers);

        when(traineeRepository.save(any()))
                .thenReturn(trainee);
        when(trainerMapper.toResponseModel(trainer))
                .thenReturn(responseTrainerModel);

        List<ResponseTrainerModel> response = traineeService.updateTrainerList(id, trainerUsernames);
        assertEquals(1, response.size());
    }

    @Test
    void deleteById_withValidId_shouldReturnVoid() {
        doNothing()
                .when(traineeRepository)
                .deleteById(1L);
        traineeService.deleteById(1L);
        verify(traineeRepository).deleteById(1L);
    }
}