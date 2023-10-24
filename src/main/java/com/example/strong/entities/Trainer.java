package com.example.strong.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainers")
@NamedEntityGraph(name = "trainer_entity-graph", attributeNodes = {
        @NamedAttributeNode("user")
})
public class Trainer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingType trainingType;

    @ManyToMany(mappedBy = "trainers")
    private Set<Trainee> trainees;

    @OneToMany(mappedBy = "trainer")
    private List<Training> trainings;
}
