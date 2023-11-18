package com.example.strong.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainees")
public class Trainee extends User {
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(
            name = "trainee_trainers",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;

    @OneToMany(mappedBy = "trainee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Training> trainings;
}
