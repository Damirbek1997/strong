package com.gym.strong.repository;

import com.gym.strong.entities.Trainee;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

@Log4j
@Repository
public class TraineeDao extends AbstractDao<Trainee> {
    @Override
    protected void setId(Trainee entity, long id) {
        entity.setId(id);
    }
}
