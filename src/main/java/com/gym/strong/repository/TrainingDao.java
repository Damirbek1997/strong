package com.gym.strong.repository;

import com.gym.strong.entities.Training;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.impl.TraineeMapper;
import com.gym.strong.mappers.impl.TrainerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class TrainingDao extends AbstractDao<Training> {
    @Value("${storage.file.path.training}")
    private String filePath;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;

    public TrainingDao(TraineeMapper traineeMapper, TrainerMapper trainerMapper) {
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
    }

    @Override
    protected void setId(Training entity, long id) {
        entity.setId(id);
    }

    @PostConstruct
    public void initializeStorage() {
        if (filePath.isEmpty()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    Training training = new Training();
                    training.setTrainee(traineeMapper.fromString(parts[0]));
                    training.setTrainer(trainerMapper.fromString(parts[1]));
                    training.setTrainingName(parts[2]);
                    training.setTrainingDate(new SimpleDateFormat("yyyy-MM-dd").parse(parts[4]));
                    training.setTrainingDuration(Long.valueOf(parts[5]));

                    save(training);
                }
            }
        } catch (IOException | InsertStorageFromFileException | ParseException e) {
            log.error("Error while trying to read from the file location: {}, error: {}, message: {}", filePath, e, e.getMessage());
            e.printStackTrace();
        }
    }
}
