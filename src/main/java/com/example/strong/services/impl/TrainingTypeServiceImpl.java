package com.example.strong.services.impl;

import com.example.strong.entities.TrainingType;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.mappers.impl.TrainingTypeMapper;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.repository.TrainingTypeRepository;
import com.example.strong.services.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public List<TrainingTypeModel> getAll() {
        return trainingTypeMapper.toModelList(trainingTypeRepository.findAll());
    }

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
