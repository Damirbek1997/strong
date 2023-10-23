package com.gym.strong;

import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.TrainingMapper;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.repository.TrainingDao;
import com.gym.strong.services.impl.FIleServiceImpl;

public class StrongApplication {
    public static void main(String[] args) {
        String filePath = "";

        TraineeDao traineeDao = new TraineeDao();
        TrainerDao trainerDao = new TrainerDao();
        TrainingDao trainingDao = new TrainingDao();

        TrainerMapper trainerMapper = new TrainerMapper();
        TraineeMapper traineeMapper = new TraineeMapper(trainerMapper);
        TrainingMapper trainingMapper = new TrainingMapper(traineeMapper, trainerMapper);

        FIleServiceImpl fileService = new FIleServiceImpl();
        fileService.setTraineeDao(traineeDao);
        fileService.setTrainerDao(trainerDao);
        fileService.setTrainingDao(trainingDao);
        fileService.setTraineeMapper(traineeMapper);
        fileService.setTrainerMapper(trainerMapper);
        fileService.setTrainingMapper(trainingMapper);
        fileService.initializeStorage(filePath);
    }
}
