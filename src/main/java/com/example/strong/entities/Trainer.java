package com.example.strong.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainers")
public class Trainer extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingType trainingType;

    @ManyToMany(mappedBy = "trainers")
    private Set<Trainee> trainees;
}
