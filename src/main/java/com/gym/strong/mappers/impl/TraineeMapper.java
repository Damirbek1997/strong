package com.gym.strong.mappers.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TraineeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class TraineeMapper implements AbstractMapper<Trainee, TraineeModel> {
    private final UserMapper userMapper;
    private final TrainerMapper mapperFacade;

    @Override
    public TraineeModel toModel(Trainee entity) {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(entity.getId());
        traineeModel.setUserModel(userMapper.toModel(entity.getUser()));
        traineeModel.setBirthday(entity.getBirthday());
        traineeModel.setAddress(entity.getAddress());
        traineeModel.setTrainerModels(mapperFacade.toModelList(new ArrayList<>(entity.getTrainers())));
        return traineeModel;
    }

    @Override
    public Trainee toEntity(TraineeModel model) {
        Trainee trainee = new Trainee();
        trainee.setId(model.getId());
        trainee.setUser(userMapper.toEntity(model.getUserModel()));
        trainee.setBirthday(model.getBirthday());
        trainee.setAddress(model.getAddress());
        trainee.setTrainers(new HashSet<>(mapperFacade.toEntityList(model.getTrainerModels())));
        return trainee;
    }

    public Trainee fromString(String line) throws ParseException {
        String[] parts = line.split(",");

        if (parts.length >= 2) {
            Trainee trainee = new Trainee();
            trainee.setUser(userMapper.fromString(parts[1]));
            trainee.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(parts[2]));
            trainee.setAddress(parts[3]);
            trainee.setTrainers(new HashSet<>());

            return trainee;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of User must be filled!");
    }
}
