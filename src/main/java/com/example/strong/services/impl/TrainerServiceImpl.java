package com.example.strong.services.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.entities.Trainer;
import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.repository.TrainerRepository;
import com.example.strong.services.TrainerService;
import com.example.strong.services.TrainingTypeService;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    @Override
    @PreAuthenticated
    public List<TrainerModel> getAll(SecurityAuthentication authentication) {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerRepository.findAll());
        log.debug("Getting all Trainers: {}", trainerModels);
        return trainerModels;
    }

    @Override
    @PreAuthenticated
    public List<TrainerModel> getAllByIds(List<Long> ids, SecurityAuthentication authentication) {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerRepository.findAllByIdIn(ids));
        log.debug("Getting all Trainers: {}, by ids", trainerModels);
        return trainerModels;
    }

    @Override
    @PreAuthenticated
    public List<TrainerModel> getAllNotBusyTrainers(SecurityAuthentication authentication) {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerRepository.getAllNotBusyTrainers());
        log.debug("Getting all not busy Trainers: {}", trainerModels);
        return trainerModels;
    }

    @Override
    @PreAuthenticated
    public TrainerModel getById(Long id, SecurityAuthentication authentication) {
        TrainerModel trainerModel = trainerMapper.toModel(getEntityById(id));
        log.debug("Getting Trainer: {} by id", trainerModel);
        return trainerModel;
    }

    @Override
    @PreAuthenticated
    public TrainerModel getByUsername(String username, SecurityAuthentication authentication) {
        TrainerModel trainerModel = trainerMapper.toModel(trainerRepository.findByUsername(username));
        log.debug("Getting Trainer: {} by username {}", trainerModel, username);
        return trainerModel;
    }

    @Override
    @Transactional
    public TrainerModel create(CreateTrainerModel createTrainerModel) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(createTrainerModel.getFirstName());
        trainer.setLastName(createTrainerModel.getLastName());
        trainer.setUsername(userService.generateUsername(createTrainerModel.getFirstName(), createTrainerModel.getLastName()));
        trainer.setPassword(userService.generatePassword());
        trainer.setIsActive(true);
        trainer.setTrainingType(trainingTypeService.getById(createTrainerModel.getTrainingTypeId()));

        Long amountOfUsers = trainerRepository.countByUsernameLike(trainer.getUsername());

        if (amountOfUsers > 0) {
            trainer.setUsername(trainer.getUsername() + amountOfUsers);
        }

        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Created Trainer with model {}", createTrainerModel);
        return trainerMapper.toModel(savedTrainer);
    }

    @Override
    @Transactional
    @PreAuthenticated
    public TrainerModel update(UpdateTrainerModel updateTrainerModel, SecurityAuthentication authentication) {
        Trainer trainer = getEntityById(updateTrainerModel.getId());

        if (updateTrainerModel.getTrainingTypeId() != null) {
            trainer.setTrainingType(trainingTypeService.getById(updateTrainerModel.getTrainingTypeId()));
        }

        if (updateTrainerModel.getFirstName() != null) {
            trainer.setFirstName(updateTrainerModel.getFirstName());
        }

        if (updateTrainerModel.getLastName() != null) {
            trainer.setLastName(updateTrainerModel.getLastName());
        }

        String username = userService.generateUsername(trainer.getFirstName(), trainer.getLastName());
        Long amountOfUsers = trainerRepository.countByUsernameLike(username);

        if (amountOfUsers > 0) {
            trainer.setUsername(username + amountOfUsers);
        }

        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Updated Trainer with model {}", updateTrainerModel);
        return trainerMapper.toModel(savedTrainer);
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void changePassword(UserCredentialsModel userCredentialsModel, SecurityAuthentication authentication) {
        Trainer trainer = getEntityById(userCredentialsModel.getId());
        trainer.setPassword(userCredentialsModel.getNewPassword());
        trainerRepository.save(trainer);
        log.debug("Changed password to User with username: {}", trainer.getUsername());
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void activateById(Long id, SecurityAuthentication authentication) {
        Trainer trainer = getEntityById(id);
        trainer.setIsActive(true);
        trainerRepository.save(trainer);
        log.debug("Activated User with username: {}", trainer.getUsername());
    }

    @Override
    @Transactional
    @PreAuthenticated
    public void deactivateById(Long id, SecurityAuthentication authentication) {
        Trainer trainer = getEntityById(id);
        trainer.setIsActive(false);
        trainerRepository.save(trainer);
        log.debug("Activated User with username: {}", trainer.getUsername());
    }

    @Override
    public String authentication(String username, String password) {
        Trainer trainer = trainerRepository.findByUsernameAndPassword(username, password);

        if (trainer == null) {
            log.error("Incorrect username or password!");
            throw new BadRequestException("Incorrect username or password!");
        }

        return RandomString.make();
    }

    private Trainer getEntityById(Long id) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(id);

        if (trainerOptional.isPresent()) {
            return trainerOptional.get();
        }

        log.error("There is no Trainer with id {}", id);
        throw new BadRequestException("There is no Trainer with id: " + id);
    }
}
