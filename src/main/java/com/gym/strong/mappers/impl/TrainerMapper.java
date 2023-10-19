package com.gym.strong.mappers.impl;

import com.gym.strong.entities.Trainer;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.util.UserUtil;
import org.springframework.stereotype.Component;

import java.util.HashSet;

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

    public Trainer toEntity(CreateTrainerModel model) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(model.getFirstName());
        trainer.setLastName(model.getLastName());
        trainer.setUsername(UserUtil.generateUsername(model.getFirstName(), model.getLastName()));
        trainer.setPassword(UserUtil.generatePassword());
        trainer.setIsActive(true);
        return trainer;
    }

    public Trainer fromString(String line) {
        String[] parts = line.split(",");

        if (parts.length >= 3) {
            Trainer trainer = new Trainer();
            trainer.setFirstName(parts[1]);
            trainer.setLastName(parts[2]);
            trainer.setUsername(parts[3]);
            trainer.setIsActive(Boolean.valueOf(parts[4]));
            trainer.setTrainees(new HashSet<>());

            return trainer;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of User must be filled!");
    }
}
