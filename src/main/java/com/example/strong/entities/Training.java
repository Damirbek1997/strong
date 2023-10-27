package com.example.strong.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainings")
@NamedEntityGraph(name = "training_entity-graph", attributeNodes = {
        @NamedAttributeNode(value = "trainee"),
        @NamedAttributeNode(value = "trainer"),
        @NamedAttributeNode(value = "trainingType")}
)
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Trainee trainee;

    @ManyToOne
    private Trainer trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @ManyToOne
    private TrainingType trainingType;

    @Column(name = "training_date", nullable = false)
    private Date trainingDate;

    @Column(name = "training_duration", nullable = false)
    private Long trainingDuration;
}
