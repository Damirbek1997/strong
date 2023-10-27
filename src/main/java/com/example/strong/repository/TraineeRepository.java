package com.example.strong.repository;

import com.example.strong.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    List<Trainee> findAllByIdIn(List<Long> ids);

    Trainee findByUsername(String username);

    void deleteByUsername(String username);

    Long countByUsernameLike(String username);
}
