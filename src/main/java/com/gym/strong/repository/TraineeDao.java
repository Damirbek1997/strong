package com.gym.strong.repository;

import com.gym.strong.entities.Trainee;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.impl.TraineeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

@Slf4j
@Component
public class TraineeDao extends AbstractDao<Trainee> {
    @Value("${storage.file.path.trainee}")
    private String filePath;
    private final TraineeMapper traineeMapper;

    public TraineeDao(TraineeMapper traineeMapper) {
        this.traineeMapper = traineeMapper;
    }

    @Override
    protected void setId(Trainee entity, long id) {
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
                Trainee trainee = traineeMapper.fromString(line);
                save(trainee);
            }
        } catch (IOException | ParseException | InsertStorageFromFileException e) {
            log.error("Error while trying to read from the file location: {}, error: {}, message: {}", filePath, e, e.getMessage());
            e.printStackTrace();
        }
    }
}
