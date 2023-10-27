package com.example.strong.mappers.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.TraineeModel;
import org.springframework.stereotype.Component;

@Component
public class TraineeMapper implements AbstractMapper<Trainee, TraineeModel> {
    @Override
    public TraineeModel toModel(Trainee entity) {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(entity.getId());
        traineeModel.setFirstName(entity.getFirstName());
        traineeModel.setLastName(entity.getLastName());
        traineeModel.setUsername(entity.getUsername());
        traineeModel.setIsActive(entity.getIsActive());
        traineeModel.setBirthday(entity.getBirthday());
        traineeModel.setAddress(entity.getAddress());

        return traineeModel;
    }

    @Override
    public Trainee toEntity(TraineeModel model) {
        Trainee trainee = new Trainee();
        trainee.setId(model.getId());
        trainee.setFirstName(model.getFirstName());
        trainee.setLastName(model.getLastName());
        trainee.setUsername(model.getUsername());
        trainee.setIsActive(model.getIsActive());
        trainee.setBirthday(model.getBirthday());
        trainee.setAddress(model.getAddress());

        return trainee;
    }
}
