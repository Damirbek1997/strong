package com.example.strong.services.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.entities.Training;
import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TraineeMapper;
import com.example.strong.mappers.impl.TrainerMapper;
import com.example.strong.mappers.impl.TrainingMapper;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.repository.TrainingRepository;
import com.example.strong.services.TraineeService;
import com.example.strong.services.TrainerService;
import com.example.strong.services.TrainingService;
import com.example.strong.services.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingTypeService trainingTypeService;
    private final TrainingMapper trainingMapper;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;

    @Override
    @PreAuthenticated
    public List<TrainingModel> getAll(SecurityAuthentication authentication) {
        List<TrainingModel> trainingModels = trainingMapper.toModelList(trainingRepository.findAll());
        log.debug("Getting all Trainings: {}", trainingModels);
        return trainingModels;
    }

    @Override
    @PreAuthenticated
    public List<TrainingModel> getAllTrainersByUsername(String username, SecurityAuthentication authentication) {
        List<TrainingModel> trainingModels = trainingMapper.toModelList(trainingRepository.getAllTrainersByUsername(username));
        log.debug("Getting all Trainings: {}, by trainer username: {}", trainingModels, username);
        return trainingModels;
    }

    @Override
    @PreAuthenticated
    public List<TrainingModel> getAllTraineesByUsername(String username, SecurityAuthentication authentication) {
        List<TrainingModel> trainingModels = trainingMapper.toModelList(trainingRepository.getAllTraineesByUsername(username));
        log.debug("Getting all Trainings: {}, by trainee username: {}", trainingModels, username);
        return trainingModels;
    }

    @Override
    @PreAuthenticated
    public TrainingModel getById(Long id, SecurityAuthentication authentication) {
        Optional<Training> trainingOptional = trainingRepository.findById(id);

        if (trainingOptional.isPresent()) {
            TrainingModel trainingModel = trainingMapper.toModel(trainingOptional.get());
            log.debug("Getting Training: {}, by id: {}", trainingModel, id);
            return trainingModel;
        }

        log.error("There is no Training with id {}", id);
        throw new BadRequestException("There is no Training with id: " + id);
    }

    @Override
    @Transactional
    @PreAuthenticated
    public TrainingModel create(CreateTrainingModel createTrainingModel, SecurityAuthentication authentication) {
        Training training = new Training();
        TraineeModel traineeModel = traineeService.getById(createTrainingModel.getTraineeId(), null);
        TrainerModel trainerModel = trainerService.getById(createTrainingModel.getTrainerId(), null);
        training.setTrainee(traineeMapper.toEntity(traineeModel));
        training.setTrainer(trainerMapper.toEntity(trainerModel));
        training.setTrainingName(createTrainingModel.getTrainingName());
        training.setTrainingType(trainingTypeService.getById(createTrainingModel.getTrainingTypeId()));
        training.setTrainingDate(createTrainingModel.getTrainingDate());
        training.setTrainingDuration(createTrainingModel.getTrainingDuration());
        trainingRepository.save(training);
        log.info("Created training with model {}", createTrainingModel);
        return trainingMapper.toModel(training);
    }
}
