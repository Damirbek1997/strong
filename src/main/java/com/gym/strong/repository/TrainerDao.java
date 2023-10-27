package com.gym.strong.repository;

import com.gym.strong.entities.Trainer;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

@Log4j
@Repository
public class TrainerDao extends AbstractDao<Trainer> {
    @Override
    protected void setId(Trainer entity, long id) {
        entity.setId(id);
    }
}
