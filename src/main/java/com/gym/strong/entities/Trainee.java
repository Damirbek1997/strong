package com.gym.strong.entities;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    private Long id;
    private User user;
    private Date birthday;
    private String address;
    private Set<Trainer> trainers;
}
