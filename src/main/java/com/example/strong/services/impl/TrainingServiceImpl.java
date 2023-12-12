package com.example.strong.services.impl;

import com.example.strong.entities.Training;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TrainingMapper;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.repository.TrainingRepository;
import com.example.strong.services.TraineeService;
import com.example.strong.services.TrainerService;
import com.example.strong.services.TrainingService;
import com.example.strong.services.WorkloadService;
import com.example.strong.specifications.TrainingSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingMapper trainingMapper;
    private final TrainingSpecification trainingSpecification;
    private final WorkloadService workloadService;

    @Override
    public List<TrainingModel> getAllByTraineeUsername(String traineeUsername, Date periodFrom, Date periodTo, String trainerName, Long trainingTypeId) {
        Specification<Training> specification = trainingSpecification.getTraineeSpecificationBy(traineeUsername, periodFrom, periodTo, trainerName, trainingTypeId);
        List<TrainingModel> trainingModels = trainingMapper.toModelList(trainingRepository.findAll(specification));
        log.debug("Getting all Trainings: {}, by trainee username: {}", trainingModels, traineeUsername);
        return trainingModels;
    }

    @Override
    public List<TrainingModel> getAllByTrainerUsername(String trainerUsername, Date periodFrom, Date periodTo, String traineeName) {
        Specification<Training> specification = trainingSpecification.getTrainerSpecificationBy(trainerUsername, periodFrom, periodTo, traineeName);
        List<TrainingModel> trainingModels = trainingMapper.toModelList(trainingRepository.findAll(specification));
        log.debug("Getting all Trainings: {}, by trainer username: {}", trainingModels, trainerUsername);
        return trainingModels;
    }

    @Override
    @Transactional
    public TrainingModel create(CreateTrainingModel createTrainingModel) {
        validateFields(createTrainingModel);
        Training training = trainingMapper.toEntity(createTrainingModel);
        training.setTrainee(traineeService.getEntityByUsername(createTrainingModel.getTraineeUsername()));
        training.setTrainer(trainerService.getEntityByUsername(createTrainingModel.getTrainerUsername()));

        trainingRepository.save(training);
        workloadService.create(training);
        log.debug("Created training with model {}", createTrainingModel);
        return trainingMapper.toModel(training);
    }

    private void validateFields(CreateTrainingModel createTrainingModel) {
        if (createTrainingModel.getTraineeUsername() == null) {
            throw new BadRequestException("trainee username must be filled!");
        }

        if (createTrainingModel.getTrainerUsername() == null) {
            throw new BadRequestException("trainer username must be filled!");
        }

        if (createTrainingModel.getTrainingName() == null) {
            throw new BadRequestException("training name must be filled!");
        }

        if (createTrainingModel.getTrainingDate() == null) {
            throw new BadRequestException("training date must be filled!");
        }

        if (createTrainingModel.getTrainingDuration() == null) {
            throw new BadRequestException("training duration must be filled!");
        }
    }
}
