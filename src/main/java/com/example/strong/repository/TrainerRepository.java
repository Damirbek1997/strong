package com.example.strong.repository;

import com.example.strong.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findAllByIdIn(List<Long> ids);

    Trainer findByUserUsername(String username);

    @Query("select tr from Trainer tr \n" +
            "join User u on u.id = tr.user.id \n" +
            "where u.isActive = true and tr.id not in (" +
            "select t.trainer.id from Training t)")
    List<Trainer> getAllNotBusyTrainers();
}
