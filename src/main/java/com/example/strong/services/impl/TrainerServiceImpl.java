package com.example.strong.services.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.repository.TrainerRepository;
import com.example.strong.services.TrainerService;
import com.example.strong.services.TrainingTypeService;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    @Override
    public List<Trainer> getAllEntitiesByUsernames(List<String> usernames) {
        return trainerRepository.findAllByUsernameIn(usernames);
    }

    @Override
    public List<ResponseTrainerModel> getAllNotBusyTrainers() {
        List<Trainer> trainers = trainerRepository.getAllNotBusyTrainers();
        List<ResponseTrainerModel> trainerModels = trainers.stream()
                .map(trainerMapper::toResponseModel)
                .collect(Collectors.toList());
        log.debug("Getting all not busy Trainers: {}", trainerModels);
        return trainerModels;
    }

    @Override
    public TrainerModel getByUsername(String username) {
        TrainerModel trainerModel = trainerMapper.toModel(getEntityByUsername(username));
        log.debug("Getting Trainer: {} by username {}", trainerModel, username);
        return trainerModel;
    }

    @Override
    public Trainer getEntityByUsername(String username) {
        Trainer trainer = trainerRepository.findByUsername(username);

        if (trainer != null) {
            return trainer;
        }

        log.error("There is no Trainer with username {}", username);
        throw new BadRequestException("There is no Trainer with username: " + username);
    }

    @Override
    @Transactional
    public ResponseCredentialsModel create(CreateTrainerModel createTrainerModel) {
        validateFields(createTrainerModel);
        Trainer trainer = new Trainer();
        trainer.setFirstName(createTrainerModel.getFirstName());
        trainer.setLastName(createTrainerModel.getLastName());
        trainer.setUsername(userService.generateUsername(createTrainerModel.getFirstName(), createTrainerModel.getLastName()));
        trainer.setPassword(userService.generatePassword());
        trainer.setActive(true);
        trainer.setTrainingType(trainingTypeService.getById(createTrainerModel.getTrainingTypeId()));

        Long amountOfUsers = trainerRepository.countByUsernameLike(trainer.getUsername());

        if (amountOfUsers > 0) {
            trainer.setUsername(trainer.getUsername() + amountOfUsers);
        }

        trainerRepository.save(trainer);
        log.info("Created Trainer with model {}", createTrainerModel);
        return ResponseCredentialsModel.builder()
                .username(trainer.getUsername())
                .password(trainer.getPassword())
                .build();
    }

    @Override
    @Transactional
    public TrainerModel update(Long id, UpdateTrainerModel updateTrainerModel) {
        validateFields(updateTrainerModel);

        Trainer trainer = getEntityById(id);
        trainer.setFirstName(updateTrainerModel.getFirstName());
        trainer.setLastName(updateTrainerModel.getLastName());
        trainer.setActive(updateTrainerModel.getActive());

        if (updateTrainerModel.getTrainingTypeId() != null) {
            trainer.setTrainingType(trainingTypeService.getById(updateTrainerModel.getTrainingTypeId()));
        }

        String username = userService.generateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName());
        Long amountOfUsers = trainerRepository.countByUsernameLike(username);

        trainer.setUsername(amountOfUsers > 0 ? username + amountOfUsers : username);

        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Updated Trainer with model {}", updateTrainerModel);
        return trainerMapper.toModel(savedTrainer);
    }

    @Override
    @Transactional
    public void activateByUsername(String username) {
        Trainer trainer = getEntityByUsername(username);
        trainer.setActive(true);
        trainerRepository.save(trainer);
        log.debug("Activated User with username: {}", trainer.getUsername());
    }

    @Override
    @Transactional
    public void deactivateByUsername(String username) {
        Trainer trainer = getEntityByUsername(username);
        trainer.setActive(false);
        trainerRepository.save(trainer);
        log.debug("Activated User with username: {}", trainer.getUsername());
    }

    private Trainer getEntityById(Long id) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(id);

        if (trainerOptional.isPresent()) {
            return trainerOptional.get();
        }

        log.error("There is no Trainer with id {}", id);
        throw new BadRequestException("There is no Trainer with id: " + id);
    }

    private void validateFields(CreateTrainerModel createTrainerModel) {
        if (createTrainerModel.getFirstName() == null) {
            throw new BadRequestException("firstName must be filled!");
        }

        if (createTrainerModel.getLastName() == null) {
            throw new BadRequestException("lastName must be filled!");
        }

        if (createTrainerModel.getTrainingTypeId() == null) {
            throw new BadRequestException("training type must be filled!");
        }
    }

    private void validateFields(UpdateTrainerModel updateTrainerModel) {
        if (updateTrainerModel.getFirstName() == null) {
            throw new BadRequestException("firstName must be filled!");
        }

        if (updateTrainerModel.getLastName() == null) {
            throw new BadRequestException("lastName must be filled!");
        }

        if (updateTrainerModel.getActive() == null) {
            throw new BadRequestException("active must be filled!");
        }
    }
}
