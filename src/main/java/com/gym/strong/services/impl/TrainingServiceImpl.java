package com.gym.strong.services.impl;

import com.gym.strong.entities.Training;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.TrainingMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.TrainingModel;
import com.gym.strong.models.crud.CreateTrainingModel;
import com.gym.strong.repository.TrainingDao;
import com.gym.strong.services.TraineeService;
import com.gym.strong.services.TrainerService;
import com.gym.strong.services.TrainingService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao trainingDao;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingMapper trainingMapper;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;

    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDao, TraineeService traineeService, TrainerService trainerService, TrainingMapper trainingMapper,
                               TraineeMapper traineeMapper, TrainerMapper trainerMapper) {
        this.trainingDao = trainingDao;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingMapper = trainingMapper;
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
    }

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
        Training training = new Training();
        TraineeModel traineeModel = traineeService.getById(createTrainingModel.getTraineeId());
        TrainerModel trainerModel = trainerService.getById(createTrainingModel.getTrainerId());
        training.setTrainee(traineeMapper.toEntity(traineeModel));
        training.setTrainer(trainerMapper.toEntity(trainerModel));
        training.setTrainingName(createTrainingModel.getTrainingName());
        training.setTrainingDate(createTrainingModel.getTrainingDate());
        training.setTrainingDuration(createTrainingModel.getTrainingDuration());
        trainingDao.save(training);
        log.info("Created training with model " + createTrainingModel);
        return trainingMapper.toModel(training);
    }
}
