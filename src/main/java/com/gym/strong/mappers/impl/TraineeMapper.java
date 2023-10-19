package com.gym.strong.mappers.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class TraineeMapper implements AbstractMapper<Trainee, TraineeModel> {
    private final TrainerMapper trainerMapper;

    @Override
    public TraineeModel toModel(Trainee entity) {
        if (entity == null) {
            return null;
        }

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(entity.getId());
        traineeModel.setFirstName(entity.getFirstName());
        traineeModel.setLastName(entity.getLastName());
        traineeModel.setUsername(entity.getUsername());
        traineeModel.setIsActive(entity.getIsActive());
        traineeModel.setBirthday(entity.getBirthday());
        traineeModel.setAddress(entity.getAddress());

        if (entity.getTrainers() != null) {
            traineeModel.setTrainerModels(trainerMapper.toModelList(new ArrayList<>(entity.getTrainers())));
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
        trainee.setIsActive(model.getIsActive());
        trainee.setBirthday(model.getBirthday());
        trainee.setAddress(model.getAddress());

        if (model.getTrainerModels() != null) {
            trainee.setTrainers(new HashSet<>(trainerMapper.toEntityList(model.getTrainerModels())));
        }

        return trainee;
    }

    public Trainee toEntity(CreateTraineeModel model) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(model.getFirstName());
        trainee.setLastName(model.getLastName());
        trainee.setUsername(UserUtil.generateUsername(model.getFirstName(), model.getLastName()));
        trainee.setPassword(UserUtil.generatePassword());
        trainee.setIsActive(true);
        trainee.setBirthday(model.getBirthday());
        trainee.setAddress(model.getAddress());
        return trainee;
    }

    public Trainee fromString(String line) throws ParseException {
        String[] parts = line.split(",");

        if (parts.length >= 5) {
            Trainee trainee = new Trainee();
            trainee.setFirstName(parts[1]);
            trainee.setLastName(parts[2]);
            trainee.setUsername(parts[3]);
            trainee.setIsActive(Boolean.valueOf(parts[4]));
            trainee.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(parts[5]));
            trainee.setAddress(parts[6]);
            trainee.setTrainers(new HashSet<>());

            return trainee;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of User must be filled!");
    }
}
