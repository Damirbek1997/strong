package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.entities.Training;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.TrainingMapper;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.repository.TrainingDao;
import com.gym.strong.services.FileService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

@Log4j
@Service
public class FIleServiceImpl implements FileService {
    private TraineeMapper traineeMapper;
    private TrainerMapper trainerMapper;
    private TrainingMapper trainingMapper;
    private TraineeDao traineeDao;
    private TrainerDao trainerDao;
    private TrainingDao trainingDao;

    @Override
    public void initializeStorage(String filePath) {
        if (filePath.isEmpty()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String objectType = parts[0];
                switch (objectType) {
                    case "Trainee": {
                        Trainee trainee = traineeMapper.fromString(line);
                        traineeDao.save(trainee);
                        break;
                    }
                    case "Trainer": {
                        Trainer trainer = trainerMapper.fromString(line);
                        trainerDao.save(trainer);
                        break;
                    }
                    case "Training": {
                        Training training = trainingMapper.fromString(line);
                        trainingDao.save(training);
                        break;
                    }
                    default:
                }
            }
        } catch (IOException | InsertStorageFromFileException | ParseException e) {
            log.error("Error while trying to read from the file location: " + filePath + ", error: " + e + ", message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Autowired
    public void setTraineeMapper(TraineeMapper traineeMapper) {
        this.traineeMapper = traineeMapper;
    }

    @Autowired
    public void setTrainerMapper(TrainerMapper trainerMapper) {
        this.trainerMapper = trainerMapper;
    }

    @Autowired
    public void setTrainingMapper(TrainingMapper trainingMapper) {
        this.trainingMapper = trainingMapper;
    }

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
}
