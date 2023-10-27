package com.example.strong.services.impl;

import com.example.strong.entities.TrainingType;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.repository.TrainingTypeRepository;
import com.example.strong.services.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    @Override
    public TrainingType getById(Long id) {
        Optional<TrainingType> trainingTypeOptional = trainingTypeRepository.findById(id);

        if (trainingTypeOptional.isPresent()) {
            log.debug("Getting TrainingType with id: {}", id);
            return trainingTypeOptional.get();
        }

        log.error("There is no TrainingType with id {}", id);
        throw new BadRequestException("There is no TrainingType with id: " + id);
    }
}
