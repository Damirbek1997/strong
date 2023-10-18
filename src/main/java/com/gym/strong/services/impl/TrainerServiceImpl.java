package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainer;
import com.gym.strong.mappers.impl.TrainerMapper;
import com.gym.strong.mappers.impl.TrainingTypeMapper;
import com.gym.strong.mappers.impl.UserMapper;
import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.models.crud.UpdateTrainerModel;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.TrainerService;
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
    private final UserMapper userMapper;
    private final TrainingTypeMapper trainingTypeMapper;

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
        log.info("Creating trainer with model {}", createTrainerModel);
        Trainer trainer = new Trainer();
        trainer.setUser(userMapper.toEntity(createTrainerModel.getCreateUserModel()));
        trainer.setTrainingType(trainingTypeMapper.toEntity(createTrainerModel.getTrainingTypeModel()));

        if (trainerDao.isTrainerExistWith(trainer.getUser().getFirstName(), trainer.getUser().getLastName())) {
            trainer.getUser().setUsername(regenerateUsername(trainer.getUser().getFirstName(), trainer.getUser().getLastName(), 1L));
        }

        trainerDao.save(trainer);
        return trainerMapper.toModel(trainer);
    }

    @Override
    public TrainerModel update(Long id, UpdateTrainerModel updateTrainerModel) {
        log.info("Updating trainer with model {}", updateTrainerModel);
        Trainer trainer = trainerDao.getById(id);
        trainerDao.delete(id);

        if (updateTrainerModel.getUpdateUserModel() != null) {
            trainer.setUser(userMapper.updateUserData(trainer.getUser(), updateTrainerModel.getUpdateUserModel()));

            if (trainerDao.isTrainerExistWith(trainer.getUser().getFirstName(), trainer.getUser().getLastName())) {
                trainer.getUser().setUsername(regenerateUsername(trainer.getUser().getFirstName(), trainer.getUser().getLastName(), 1L));
            }
        }

        if (updateTrainerModel.getTrainingTypeModel() != null) {
            trainer.setTrainingType(trainingTypeMapper.toEntity(updateTrainerModel.getTrainingTypeModel()));
        }

        trainerDao.save(trainer);
        return trainerMapper.toModel(trainer);
    }

    private String regenerateUsername(String firstName, String lastName, Long count) {
        String username = firstName + "." + lastName + count;

        if (trainerDao.isTrainerExistWith(firstName, lastName)) {
            count++;
            regenerateUsername(firstName, lastName, count);
        }

        return username;
    }
}
