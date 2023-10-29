package com.example.strong.services.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.entities.Trainee;
import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.repository.TraineeRepository;
import com.example.strong.services.TraineeService;
import com.example.strong.services.TrainerService;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainerService trainerService;
    private final UserService userService;

    @Override
    @PreAuthenticated
    public List<TraineeModel> getAll(SecurityAuthentication authentication) {
        List<TraineeModel> traineeModels = traineeMapper.toModelList(traineeRepository.findAll());
        log.debug("Getting all Trainees: {}", traineeModels);
        return traineeModels;
    }

    @Override
    @PreAuthenticated
    public List<TraineeModel> getAllByIds(List<Long> ids, SecurityAuthentication authentication) {
        List<TraineeModel> traineeModels = traineeMapper.toModelList(traineeRepository.findAllByIdIn(ids));
        log.debug("Getting all Trainees: {} by ids {}", traineeModels, ids);
        return traineeModels;
    }

    @Override
    @PreAuthenticated
    public TraineeModel getById(Long id, SecurityAuthentication authentication) {
        TraineeModel traineeModel = traineeMapper.toModel(getEntityById(id));
        log.debug("Getting Trainee: {} by id", traineeModel);
        return traineeModel;
    }

    @Override
    @PreAuthenticated
    public TraineeModel getByUsername(String username, SecurityAuthentication authentication) {
        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.findByUsername(username));
        log.debug("Getting Trainee: {} by username {}", traineeModel, username);
        return traineeModel;
    }

    @Override
    @Transactional
    public TraineeModel create(CreateTraineeModel createTraineeModel) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(createTraineeModel.getFirstName());
        trainee.setLastName(createTraineeModel.getLastName());
        trainee.setUsername(userService.generateUsername(createTraineeModel.getFirstName(), createTraineeModel.getLastName()));
        trainee.setPassword(userService.generatePassword());
        trainee.setIsActive(true);
        trainee.setBirthday(createTraineeModel.getBirthday());
        trainee.setAddress(createTraineeModel.getAddress());

        Long amountOfUsers = traineeRepository.countByUsernameLike(trainee.getUsername());

        if (amountOfUsers > 0) {
            trainee.setUsername(trainee.getUsername() + amountOfUsers);
        }

        if (createTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllByIds(createTraineeModel.getTrainerIds(), null);
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.save(trainee));
        log.info("Created Trainee with model {}", createTraineeModel);
        return traineeModel;
    }

    @Override
    @Transactional
    @PreAuthenticated
    public TraineeModel update(UpdateTraineeModel updateTraineeModel, SecurityAuthentication authentication) {
        Trainee trainee = getEntityById(updateTraineeModel.getId());

        if (updateTraineeModel.getBirthday() == null) {
            trainee.setBirthday(updateTraineeModel.getBirthday());
        }

        if (updateTraineeModel.getAddress() != null) {
            trainee.setAddress(updateTraineeModel.getAddress());
        }

        if (updateTraineeModel.getTrainerIds() != null) {
            List<TrainerModel> trainerModels = trainerService.getAllByIds(updateTraineeModel.getTrainerIds(), null);
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(trainerModels)));
        }

        if (updateTraineeModel.getFirstName() != null) {
            trainee.setFirstName(updateTraineeModel.getFirstName());
        }

        if (updateTraineeModel.getLastName() != null) {
            trainee.setLastName(updateTraineeModel.getLastName());
        }

        String username = userService.generateUsername(trainee.getFirstName(), trainee.getLastName());
        Long amountOfUsers = traineeRepository.countByUsernameLike(username);

        if (amountOfUsers > 0) {
            trainee.setUsername(username + amountOfUsers);
        }

        TraineeModel traineeModel = traineeMapper.toModel(traineeRepository.save(trainee));
        log.info("Updated Trainee with model {}", updateTraineeModel);
        return traineeModel;
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void deleteById(Long id, SecurityAuthentication authentication) {
        traineeRepository.deleteById(id);
        log.info("Deleted Trainee with id {}", id);
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void deleteByUsername(String username, SecurityAuthentication authentication) {
        traineeRepository.deleteByUsername(username);
        log.info("Deleted Trainee with username {}", username);
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void changePassword(UserCredentialsModel userCredentialsModel, SecurityAuthentication authentication) {
        Trainee trainee = getEntityById(userCredentialsModel.getId());
        trainee.setPassword(userCredentialsModel.getNewPassword());
        traineeRepository.save(trainee);
        log.debug("Changed password to User with username: {}", trainee.getUsername());
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void activateById(Long id, SecurityAuthentication authentication) {
        Trainee trainee = getEntityById(id);
        trainee.setIsActive(true);
        traineeRepository.save(trainee);
        log.debug("Activated User with username: {}", trainee.getUsername());
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void deactivateById(Long id, SecurityAuthentication authentication) {
        Trainee trainee = getEntityById(id);
        trainee.setIsActive(false);
        traineeRepository.save(trainee);
        log.debug("Activated User with username: {}", trainee.getUsername());
    }

    @Override
    public String authentication(String username, String password) {
        Trainee trainee = traineeRepository.findByUsernameAndPassword(username, password);

        if (trainee == null) {
            log.error("Incorrect username or password!");
            throw new BadRequestException("Incorrect username or password!");
        }

        return RandomString.make();
    }

    private Trainee getEntityById(Long id) {
        Optional<Trainee> traineeOptional = traineeRepository.findById(id);

        if (traineeOptional.isPresent()) {
            return traineeOptional.get();
        }

        log.error("There is no Trainee with id {}", id);
        throw new BadRequestException("There is no Trainee with id: " + id);
    }
}
