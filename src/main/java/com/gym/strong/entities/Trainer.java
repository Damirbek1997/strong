package com.gym.strong.entities;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends User {
    private Long id;
    private Set<Trainee> trainees;
}
