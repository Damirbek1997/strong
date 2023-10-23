package com.example.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainerModel {
    @NotNull
    private Long id;
    private UpdateUserModel updateUserModel;
    private Long trainingTypeId;
}
