package com.example.strong.repository;

import com.example.strong.entities.Trainer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @NonNull
    @EntityGraph(value = "trainer_entity-graph")
    List<Trainer> findAll();

    @NonNull
    @EntityGraph(value = "trainer_entity-graph")
    Optional<Trainer> findById(@NonNull Long id);

    @EntityGraph(value = "trainer_entity-graph")
    List<Trainer> findAllByIdIn(List<Long> ids);

    @EntityGraph(value = "trainer_entity-graph")
    List<Trainer> findAllByUserIsActiveIsTrue();

    @EntityGraph(value = "trainer_entity-graph")
    Trainer findByUserUsername(String username);
}
