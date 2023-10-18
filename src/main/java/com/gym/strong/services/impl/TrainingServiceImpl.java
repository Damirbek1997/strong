package com.gym.strong.services.impl;

import com.gym.strong.entities.Training;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.TrainingMapper;
import com.gym.strong.mappers.impl.TrainingTypeMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.TrainingModel;
import com.gym.strong.models.crud.CreateTrainingModel;
import com.gym.strong.repository.TrainingDao;
import com.gym.strong.services.TraineeService;
import com.gym.strong.services.TrainerService;
import com.gym.strong.services.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao trainingDao;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingMapper trainingMapper;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public List<TrainingModel> getAll() {
        return trainingMapper.toModelList(trainingDao.getAll());
    }

    @Override
    public TrainingModel getById(Long id) {
        return trainingMapper.toModel(trainingDao.getById(id));
    }

    @Override
    public TrainingModel create(CreateTrainingModel createTrainingModel) {
        log.info("Creating training with model {}", createTrainingModel);
        Training training = new Training();
        TraineeModel traineeModel = traineeService.getById(createTrainingModel.getTraineeId());
        TrainerModel trainerModel = trainerService.getById(createTrainingModel.getTrainerId());
        training.setTrainee(traineeMapper.toEntity(traineeModel));
        training.setTrainer(trainerMapper.toEntity(trainerModel));
        training.setTrainingName(createTrainingModel.getTrainingName());
        training.setTrainingType(trainingTypeMapper.toEntity(createTrainingModel.getTrainingTypeModel()));
        training.setTrainingDate(createTrainingModel.getTrainingDate());
        training.setTrainingDuration(createTrainingModel.getTrainingDuration());
        trainingDao.save(training);
        return trainingMapper.toModel(training);
    }
}
