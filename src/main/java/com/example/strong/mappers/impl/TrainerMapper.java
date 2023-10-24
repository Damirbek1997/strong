package com.example.strong.mappers.impl;

import com.example.strong.entities.Trainer;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.TrainerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerMapper implements AbstractMapper<Trainer, TrainerModel> {
    private final UserMapper userMapper;

    @Override
    public TrainerModel toModel(Trainer entity) {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(entity.getId());
        trainerModel.setUserModel(userMapper.toModel(entity.getUser()));
        return trainerModel;
    }

    @Override
    public Trainer toEntity(TrainerModel model) {
        Trainer trainer = new Trainer();
        trainer.setId(model.getId());
        trainer.setUser(userMapper.toEntity(model.getUserModel()));
        return trainer;
    }
}
