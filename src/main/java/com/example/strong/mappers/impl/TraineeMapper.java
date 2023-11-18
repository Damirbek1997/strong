package com.example.strong.mappers.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.ResponseTraineeModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.TrainerModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TraineeMapper implements AbstractMapper<Trainee, TraineeModel> {
    @Override
    public TraineeModel toModel(Trainee entity) {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(entity.getId());
        traineeModel.setFirstName(entity.getFirstName());
        traineeModel.setLastName(entity.getLastName());
        traineeModel.setUsername(entity.getUsername());
        traineeModel.setActive(entity.getActive());
        traineeModel.setBirthday(entity.getBirthday());
        traineeModel.setAddress(entity.getAddress());

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(entity.getId());
        trainerModel.setFirstName(entity.getFirstName());
        trainerModel.setLastName(entity.getLastName());
        trainerModel.setUsername(entity.getUsername());
        trainerModel.setActive(entity.getActive());

        if (entity.getTrainers() != null) {
            List<ResponseTrainerModel> responseTrainerModels = new ArrayList<>();

            for (Trainer trainer : entity.getTrainers()) {
                responseTrainerModels.add(ResponseTrainerModel.builder()
                        .username(trainer.getUsername())
                        .firstName(trainer.getFirstName())
                        .lastName(trainer.getLastName())
                        .specializationId(trainer.getTrainingType() != null ? trainer.getTrainingType().getId() : null)
                        .build());
            }

            traineeModel.setTrainerModels(responseTrainerModels);
        }

        return traineeModel;
    }

    @Override
    public Trainee toEntity(TraineeModel model) {
        Trainee trainee = new Trainee();
        trainee.setId(model.getId());
        trainee.setFirstName(model.getFirstName());
        trainee.setLastName(model.getLastName());
        trainee.setUsername(model.getUsername());
        trainee.setActive(model.getActive());
        trainee.setBirthday(model.getBirthday());
        trainee.setAddress(model.getAddress());

        return trainee;
    }

    public ResponseTraineeModel toResponseModel(Trainee trainee) {
        return ResponseTraineeModel.builder()
                .username(trainee.getUsername())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .build();
    }
}
