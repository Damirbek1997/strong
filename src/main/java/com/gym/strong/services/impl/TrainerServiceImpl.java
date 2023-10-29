package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainer;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.models.crud.UpdateTrainerModel;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.TrainerService;
import com.gym.strong.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final TrainerMapper trainerMapper;
    private final UserService userService;

    @Autowired
    public TrainerServiceImpl(TrainerDao trainerDao, TrainerMapper trainerMapper, UserService userService) {
        this.trainerDao = trainerDao;
        this.trainerMapper = trainerMapper;
        this.userService = userService;
    }

    @Override
    public List<TrainerModel> getAll() {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerDao.getAll());
        log.debug("Getting all Trainers: " + trainerModels);
        return trainerModels;
    }

    @Override
    public List<TrainerModel> getAllIn(List<Long> ids) {
        List<TrainerModel> trainerModels = trainerMapper.toModelList(trainerDao.getAllByIds(ids));
        log.debug("Getting all Trainers: " + trainerModels + " by their ids: " + ids);
        return trainerModels;
    }

    @Override
    public TrainerModel getById(Long id) {
        TrainerModel trainerModel = trainerMapper.toModel(trainerDao.getById(id));
        log.debug("Getting Trainer: " + trainerModel + " by his id: " + id);
        return trainerModel;
    }

    @Override
    public TrainerModel create(CreateTrainerModel createTrainerModel) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(createTrainerModel.getFirstName());
        trainer.setLastName(createTrainerModel.getLastName());
        trainer.setUsername(userService.generateUsername(createTrainerModel.getFirstName(), createTrainerModel.getLastName()));
        trainer.setPassword(userService.generatePassword());
        trainer.setIsActive(true);

        trainerDao.save(trainer);
        log.info("Created Trainer with model " + createTrainerModel);
        log.debug("Generated username - " + trainer.getUsername() + " for Trainer: " + trainer.getId());
        return trainerMapper.toModel(trainer);
    }

    @Override
    public TrainerModel update(UpdateTrainerModel updateTrainerModel) {
        Trainer trainer = trainerDao.getById(updateTrainerModel.getId());
        String username = userService.generateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName(),
                trainer.getFirstName(), trainer.getLastName());

        trainer.setUsername(username);

        if (updateTrainerModel.getIsActive() != null) {
            trainer.setIsActive(updateTrainerModel.getIsActive());
        }

        log.info("Updated Trainer with model " + updateTrainerModel);
        log.debug("Generated username - " + trainer.getUsername() + " for Trainer: " + trainer.getId());
        return trainerMapper.toModel(trainer);
    }
}
