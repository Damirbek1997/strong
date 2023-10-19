package com.gym.strong.repository;

import com.gym.strong.entities.Trainer;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.impl.TrainerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class TrainerDao extends AbstractDao<Trainer> {
    @Value("${storage.file.path.trainer}")
    private String filePath;
    private final TrainerMapper trainerMapper;

    public TrainerDao(TrainerMapper trainerMapper) {
        this.trainerMapper = trainerMapper;
    }

    @Override
    protected void setId(Trainer entity, long id) {
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
                Trainer trainer = trainerMapper.fromString(line);
                save(trainer);
            }
        } catch (IOException | InsertStorageFromFileException e) {
            log.error("Error while trying to read from the file location: {}, error: {}, message: {}", filePath, e, e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isTrainerExistWith(String username) {
        List<Trainer> trainerList = getAll();

        for (Trainer trainer : trainerList) {
            if (trainer.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
}
