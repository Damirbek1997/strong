package com.example.strong.mappers.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.ResponseTraineeModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainerMapper implements AbstractMapper<Trainer, TrainerModel> {
    @Override
    public TrainerModel toModel(Trainer entity) {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(entity.getId());
        trainerModel.setFirstName(entity.getFirstName());
        trainerModel.setLastName(entity.getLastName());
        trainerModel.setUsername(entity.getUsername());
        trainerModel.setActive(entity.getIsActive());

        if (entity.getTrainees() != null) {
            List<ResponseTraineeModel> responseTraineeModels = new ArrayList<>();

            for (Trainee trainee : entity.getTrainees()) {
                responseTraineeModels.add(ResponseTraineeModel.builder()
                        .username(trainee.getUsername())
                        .firstName(trainee.getFirstName())
                        .lastName(trainee.getLastName())
                        .build());
            }

            trainerModel.setTraineeModels(responseTraineeModels);
        }

        return trainerModel;
    }

    @Override
    public Trainer toEntity(TrainerModel model) {
        Trainer trainer = new Trainer();
        trainer.setId(model.getId());
        trainer.setFirstName(model.getFirstName());
        trainer.setLastName(model.getLastName());
        trainer.setUsername(model.getUsername());
        trainer.setIsActive(model.getActive());
        return trainer;
    }

    public ResponseTrainerModel toResponseModel(Trainer trainer) {
        return ResponseTrainerModel.builder()
                .username(trainer.getUsername())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .specializationId(trainer.getTrainingType() != null ? trainer.getTrainingType().getId() : null)
                .build();
    }
}
