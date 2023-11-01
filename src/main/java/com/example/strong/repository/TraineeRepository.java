package com.example.strong.repository;

import com.example.strong.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    Trainee findByUsername(String username);
    Long countByUsernameLike(String username);
}
