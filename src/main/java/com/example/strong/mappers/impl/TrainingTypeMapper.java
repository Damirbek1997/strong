package com.example.strong.mappers.impl;

import com.example.strong.entities.TrainingType;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.TrainingTypeModel;
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
}
