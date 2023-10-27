package com.gym.strong.repository;

import com.gym.strong.entities.Training;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

@Log4j
@Repository
public class TrainingDao extends AbstractDao<Training> {
    @Override
    protected void setId(Training entity, long id) {
        entity.setId(id);
    }
}
