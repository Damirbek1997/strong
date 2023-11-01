package com.example.strong.repository;

import com.example.strong.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findAllByUsernameIn(List<String> usernames);
    Trainer findByUsername(String username);
    @Query("select tr from Trainer tr \n" +
            "where tr.isActive = true and tr.id not in (" +
            "select t.trainer.id from Training t where " +
            "year(t.trainingDate) = year(current_date) and month(t.trainingDate) = month(t.trainingDate))")
    List<Trainer> getAllNotBusyTrainers();
    Long countByUsernameLike(String username);
}
