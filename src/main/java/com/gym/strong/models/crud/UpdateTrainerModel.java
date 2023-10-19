package com.gym.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerModel extends UpdateUserModel {
    @NotNull
    private Long id;
}
