package com.gym.strong.models;

import com.gym.strong.enums.TraineeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSummaryModel {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private TraineeStatus traineeStatus;
    private List<Year> years;
}
