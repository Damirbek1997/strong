package com.gym.strong.mappers.impl;

import com.gym.strong.entities.TrainingType;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TrainingTypeModel;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeMapper implements AbstractMapper<TrainingType, TrainingTypeModel> {
    @Override
    public TrainingTypeModel toModel(TrainingType entity) {
        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(entity.getId());
        trainingTypeModel.setTypeName(entity.getTypeName());
        return trainingTypeModel;
    }

    @Override
    public TrainingType toEntity(TrainingTypeModel model) {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(model.getId());
        trainingType.setTypeName(model.getTypeName());
        return trainingType;
    }

    public TrainingType fromString(String line) {
        String[] parts = line.split(",");

        if (parts.length >= 5) {
            TrainingType trainingType = new TrainingType();
            trainingType.setId(Long.parseLong(parts[0]));
            trainingType.setTypeName(parts[1]);

            return trainingType;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of TrainingType must be filled!");
    }
}
