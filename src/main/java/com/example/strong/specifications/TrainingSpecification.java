package com.example.strong.specifications;

import com.example.strong.entities.Trainee;
import com.example.strong.entities.Trainer;
import com.example.strong.entities.Training;
import com.example.strong.entities.TrainingType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TrainingSpecification {
    public Specification<Training> getTraineeSpecificationBy(String traineeUsername, Date periodFrom, Date periodTo, String trainerName, Long trainingTypeId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Training, Trainee> traineeJoin = root.join("trainee", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(traineeJoin.get("username"), traineeUsername));

            if (periodFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingDate"), periodFrom));
            }

            if (periodTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingDate"), periodTo));
            }

            if (trainerName != null) {
                Join<Training, Trainer> trainerJoin = root.join("trainer", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(trainerJoin.get("firstName"), trainerName));
            }

            if (trainingTypeId != null) {
                Join<Training, TrainingType> trainingTypeJoin = root.join("trainingType", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(trainingTypeJoin.get("id"), trainingTypeId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Training> getTrainerSpecificationBy(String trainerUsername, Date periodFrom, Date periodTo, String traineeName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Training, Trainer> trainerJoin = root.join("trainer", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(trainerJoin.get("username"), trainerUsername));

            if (periodFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingDate"), periodFrom));
            }

            if (periodTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingDate"), periodTo));
            }

            if (traineeName != null) {
                Join<Training, Trainee> traineeJoin = root.join("trainee", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(traineeJoin.get("firstName"), traineeName));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
