package com.example.strong.repository;

import com.example.strong.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query(value = "select p from Training p\n" +
            "            join Trainer t on p.trainer.id = t.id\n" +
            "            where t.username = :username")
    List<Training> getAllTrainersByUsername(String username);

    @Query(value = "select p from Training p\n" +
            "            join Trainee t on p.trainee.id = t.id\n" +
            "            where t.username = :username")
    List<Training> getAllTraineesByUsername(String username);
}
