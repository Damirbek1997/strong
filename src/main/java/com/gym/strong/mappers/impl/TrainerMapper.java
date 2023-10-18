package com.gym.strong.mappers.impl;

import com.gym.strong.entities.Trainer;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TrainerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class TrainerMapper implements AbstractMapper<Trainer, TrainerModel> {
    private final UserMapper userMapper;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public TrainerModel toModel(Trainer entity) {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(entity.getId());
        trainerModel.setUserModel(userMapper.toModel(entity.getUser()));
        trainerModel.setTrainingTypeModel(trainingTypeMapper.toModel(entity.getTrainingType()));
        return trainerModel;
    }

    @Override
    public Trainer toEntity(TrainerModel model) {
        Trainer trainer = new Trainer();
        trainer.setId(model.getId());
        trainer.setUser(userMapper.toEntity(model.getUserModel()));
        trainer.setTrainingType(trainingTypeMapper.toEntity(model.getTrainingTypeModel()));
        return trainer;
    }

    public Trainer fromString(String line) {
        String[] parts = line.split(",");

        if (parts.length >= 2) {
            Trainer trainer = new Trainer();
            trainer.setUser(userMapper.fromString(parts[1]));
            trainer.setTrainingType(trainingTypeMapper.fromString(parts[2]));
            trainer.setTrainees(new HashSet<>());

            return trainer;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of User must be filled!");
    }
}
