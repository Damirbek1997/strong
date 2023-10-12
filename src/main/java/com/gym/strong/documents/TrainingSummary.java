package com.gym.strong.documents;

import com.gym.strong.enums.TraineeStatus;
import com.gym.strong.models.Year;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("training_summary_duration")
public class TrainingSummary {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private TraineeStatus traineeStatus;
    private List<Year> years;
}
