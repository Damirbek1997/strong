package com.example.strong.services.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TrainerMapper;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    @Override
    public List<TrainerModel> getAll() {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerRepository.findAll());
        log.debug("Getting all Trainers: {}", trainerModels);
        return trainerModels;
    }

    @Override
    public List<TrainerModel> getAllByIds(List<Long> ids) {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerRepository.findAllByIdIn(ids));
        log.debug("Getting all Trainers: {}, by ids", trainerModels);
        return trainerModels;
    }

    @Override
    public List<TrainerModel> getAllNotBusyTrainers() {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerRepository.getAllNotBusyTrainers());
        log.debug("Getting all not busy Trainers: {}", trainerModels);
        return trainerModels;
    }

    @Override
    public TrainerModel getById(Long id) {
        TrainerModel trainerModel = trainerMapper.toModel(getEntityById(id));
        log.debug("Getting Trainer: {} by id", trainerModel);
        return trainerModel;
    }

    @Override
    public TrainerModel getByUsername(String username) {
        TrainerModel trainerModel = trainerMapper.toModel(trainerRepository.findByUserUsername(username));
        log.debug("Getting Trainer: {} by username {}", trainerModel, username);
        return trainerModel;
    }

    @Override
    @Transactional
    public TrainerModel create(CreateTrainerModel createTrainerModel) {
        Trainer trainer = new Trainer();
        trainer.setUser(userService.create(createTrainerModel.getCreateUserModel()));
        trainer.setTrainingType(trainingTypeService.getById(createTrainerModel.getTrainingTypeId()));
        Trainer savedTrainer = trainerRepository.save(trainer);

        log.info("Created Trainer with model {}", createTrainerModel);
        return trainerMapper.toModel(savedTrainer);
    }

    @Override
    @Transactional
    public TrainerModel update(UpdateTrainerModel updateTrainerModel) {
        Trainer trainer = getEntityById(updateTrainerModel.getId());

        if (updateTrainerModel.getUpdateUserModel() != null) {
            trainer.setUser(userService.update(updateTrainerModel.getUpdateUserModel()));
        }

        if (updateTrainerModel.getTrainingTypeId() != null) {
            trainer.setTrainingType(trainingTypeService.getById(updateTrainerModel.getTrainingTypeId()));
        }

        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Updated Trainer with model {}", updateTrainerModel);
        return trainerMapper.toModel(savedTrainer);
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
