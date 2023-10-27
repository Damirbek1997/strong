package com.example.strong.mappers.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.TrainerModel;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper implements AbstractMapper<Trainer, TrainerModel> {
    @Override
    public TrainerModel toModel(Trainer entity) {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(entity.getId());
        trainerModel.setFirstName(entity.getFirstName());
        trainerModel.setLastName(entity.getLastName());
        trainerModel.setUsername(entity.getUsername());
        trainerModel.setIsActive(entity.getIsActive());
        return trainerModel;
    }

    @Override
    public Trainer toEntity(TrainerModel model) {
        Trainer trainer = new Trainer();
        trainer.setId(model.getId());
        trainer.setFirstName(model.getFirstName());
        trainer.setLastName(model.getLastName());
        trainer.setUsername(model.getUsername());
        trainer.setIsActive(model.getIsActive());
        return trainer;
    }
}
