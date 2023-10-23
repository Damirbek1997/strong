package com.example.strong.services.impl;

import com.example.strong.entities.TrainingType;
import com.example.strong.repository.TrainingTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {
    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Test
    void getById_withValidId_shouldReturnTrainingType() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        when(trainingTypeRepository.findById(1L))
                .thenReturn(Optional.of(trainingType));

        TrainingType response = trainingTypeService.getById(1L);

        assertEquals(trainingType, response);
    }
}