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
@NamedEntityGraph(name = "trainee_entity-graph",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode(value = "trainers", subgraph = "trainer-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(name = "trainer-subgraph", attributeNodes = {
                        @NamedAttributeNode("user"),
                        @NamedAttributeNode("trainingType")
                })
        }
)
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToMany
    @JoinTable(
            name = "trainee_trainers",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Training> trainings;
}
