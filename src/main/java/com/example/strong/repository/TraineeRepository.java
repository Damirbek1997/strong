package com.example.strong.repository;

import com.example.strong.entities.Trainee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    @NonNull
    @EntityGraph(value = "trainee_entity-graph")
    List<Trainee> findAll();

    @NonNull
    @EntityGraph(value = "trainee_entity-graph")
    Optional<Trainee> findById(@NonNull Long id);

    @EntityGraph(value = "trainee_entity-graph")
    List<Trainee> findAllByIdIn(List<Long> ids);

    @EntityGraph(value = "trainee_entity-graph")
    Trainee findByUserUsername(String username);

    void deleteByUserUsername(String username);
}
