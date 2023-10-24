package com.example.strong.mappers.impl;

import com.example.strong.entities.Trainee;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.TraineeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineeMapper implements AbstractMapper<Trainee, TraineeModel> {
    private final UserMapper userMapper;

    @Override
    public TraineeModel toModel(Trainee entity) {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(entity.getId());
        traineeModel.setUserModel(userMapper.toModel(entity.getUser()));
        traineeModel.setBirthday(entity.getBirthday());
        traineeModel.setAddress(entity.getAddress());

        return traineeModel;
    }

    @Override
    public Trainee toEntity(TraineeModel model) {
        Trainee trainee = new Trainee();
        trainee.setId(model.getId());
        trainee.setUser(userMapper.toEntity(model.getUserModel()));
        trainee.setBirthday(model.getBirthday());
        trainee.setAddress(model.getAddress());

        return trainee;
    }
}
