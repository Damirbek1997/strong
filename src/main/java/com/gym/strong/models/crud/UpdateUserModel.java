package com.gym.strong.models.crud;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
}
