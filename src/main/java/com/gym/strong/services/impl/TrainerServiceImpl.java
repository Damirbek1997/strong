package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainer;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.models.crud.UpdateTrainerModel;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.TrainerService;
import com.gym.strong.services.UserService;
import com.gym.strong.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final TrainerMapper trainerMapper;
    private final UserService userService;

    @Override
    public List<TrainerModel> getAll() {
        return trainerMapper.toModelList(trainerDao.getAll());
    }

    @Override
    public List<TrainerModel> getAllIn(List<Long> ids) {
        return trainerMapper.toModelList(trainerDao.getAllIn(ids));
    }

    @Override
    public TrainerModel getById(Long id) {
        return trainerMapper.toModel(trainerDao.getById(id));
    }

    @Override
    public TrainerModel create(CreateTrainerModel createTrainerModel) {
        Trainer trainer = trainerMapper.toEntity(createTrainerModel);

        if (userService.isUsernameBusy(trainer.getUsername())) {
            trainer.setUsername(userService.regenerateUsername(trainer.getFirstName(), trainer.getLastName(), 1L));
        }

        trainerDao.save(trainer);
        log.info("Created trainer with model {}", createTrainerModel);
        return trainerMapper.toModel(trainer);
    }

    @Override
    public TrainerModel update(UpdateTrainerModel updateTrainerModel) {
        Trainer trainer = trainerDao.getById(updateTrainerModel.getId());
        String username = userService.regenerateUsername(updateTrainerModel.getFirstName(), updateTrainerModel.getLastName(),
                trainer.getFirstName(), trainer.getLastName());

        if (updateTrainerModel.getFirstName() != null && updateTrainerModel.getLastName() != null) {
            trainer.setFirstName(updateTrainerModel.getFirstName());
            trainer.setLastName(updateTrainerModel.getLastName());
        }

        if (updateTrainerModel.getFirstName() == null) {
            trainer.setLastName(updateTrainerModel.getLastName());
        }

        if (updateTrainerModel.getLastName() == null) {
            trainer.setFirstName(updateTrainerModel.getFirstName());
        }

        trainer.setUsername(UserUtil.generateUsername(trainer.getFirstName(), trainer.getLastName()));

        if (username != null) {
            trainer.setUsername(username);
        }

        if (updateTrainerModel.getIsActive() != null) {
            trainer.setIsActive(updateTrainerModel.getIsActive());
        }

        log.info("Updated trainer with model {}", updateTrainerModel);
        return trainerMapper.toModel(trainer);
    }
}
