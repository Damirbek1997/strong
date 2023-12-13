package com.example.strong.services.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.repository.TraineeRepository;
import com.example.strong.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainerService trainerService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    private final WorkloadService workloadService;

    @Override
    public TraineeModel getByUsername(String username) {
        TraineeModel traineeModel = traineeMapper.toModel(getEntityByUsername(username));
        log.debug("Getting Trainee: {} by username {}", traineeModel, username);
        return traineeModel;
    }

    @Override
    public Trainee getEntityByUsername(String username) {
        return traineeRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("There is no Trainee with username: " + username));
    }

    @Override
    @Transactional
    public ResponseCredentialsModel create(CreateTraineeModel createTraineeModel) {
        String password = userService.generatePassword();

        Trainee trainee = new Trainee();
        trainee.setFirstName(createTraineeModel.getFirstName());
        trainee.setLastName(createTraineeModel.getLastName());
        trainee.setUsername(userService.generateUsername(createTraineeModel.getFirstName(), createTraineeModel.getLastName()));
        trainee.setPassword(encryptionService.encode(password));
        trainee.setActive(true);
        trainee.setUsername(userService.getUniqueUsername(trainee.getUsername()));

        if (createTraineeModel.getBirthday() != null) {
            trainee.setBirthday(createTraineeModel.getBirthday());
        }

        if (createTraineeModel.getAddress() != null) {
            trainee.setAddress(createTraineeModel.getAddress());
        }

        traineeRepository.save(trainee);
        log.debug("Created Trainee with model {}", createTraineeModel);
        return ResponseCredentialsModel.builder()
                .username(trainee.getUsername())
                .password(password)
                .build();
    }

    @Override
    @Transactional
    public TraineeModel update(Long id, UpdateTraineeModel updateTraineeModel) {
        Trainee trainee = getEntityById(id);
        trainee.setFirstName(updateTraineeModel.getFirstName());
        trainee.setLastName(updateTraineeModel.getLastName());

        if (updateTraineeModel.getBirthday() == null) {
            trainee.setBirthday(updateTraineeModel.getBirthday());
        }

        if (updateTraineeModel.getAddress() != null) {
            trainee.setAddress(updateTraineeModel.getAddress());
        }

        String username = userService.generateUsername(updateTraineeModel.getFirstName(), updateTraineeModel.getLastName());
        trainee.setUsername(userService.getUniqueUsername(username));

        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.save(trainee));
        log.debug("Updated Trainee with model {}", updateTraineeModel);
        return traineeModel;
    }

    @Override
    @Transactional
    public List<ResponseTrainerModel> updateTrainerList(Long id, List<String> usernames) {
        Trainee trainee = getEntityById(id);

        List<Trainer> trainer = trainerService.getAllEntitiesByUsernames(usernames);
        trainee.setTrainers(new HashSet<>(trainer));
        traineeRepository.save(trainee);

        log.debug("Added Trainee's trainer list with usernames: {}", usernames);
        return trainer.stream()
                .map(trainerMapper::toResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        Trainee trainee = getEntityByUsername(username);
        traineeRepository.deleteByUsername(username);
        workloadService.delete(trainee.getTrainings());
        log.debug("Deleted Trainee with username {}", username);
    }

    private Trainee getEntityById(Long id) {
        return traineeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no Trainee with id: " + id));
    }
}
