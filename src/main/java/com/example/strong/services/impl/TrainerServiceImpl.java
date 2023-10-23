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

import java.util.ArrayList;
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
        return trainerMapper.toModelList(trainerRepository.findAll());
    }

    @Override
    public List<TrainerModel> getAllIn(List<Long> ids) {
        return trainerMapper.toModelList(trainerRepository.findAllByIdIn(ids));
    }

    @Override
    public List<TrainerModel> getAllNotBusyTrainers() {
        List<Trainer> response = new ArrayList<>();
        List<Trainer> trainers = trainerRepository.findAllByUserIsActiveIsTrue();

        for (Trainer trainer : trainers) {
            if (trainer.getTrainings() == null || trainer.getTrainings().isEmpty()) {
                response.add(trainer);
            }
        }

        return trainerMapper.toModelList(response);
    }

    @Override
    public TrainerModel getById(Long id) {
        return trainerMapper.toModel(getEntityById(id));
    }

    @Override
    public TrainerModel getByUsername(String username) {
        return trainerMapper.toModel(trainerRepository.findByUserUsername(username));
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
