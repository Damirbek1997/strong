package com.example.strong.services.impl;

import com.example.strong.entities.TrainingType;
import com.example.strong.mappers.impl.TrainingTypeMapper;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.repository.TrainingTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {
    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @Test
    void getByAll_shouldReturnTrainingTypeList() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTypeName("type");

        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        List<TrainingType> trainingTypeList = new ArrayList<>();
        trainingTypeList.add(trainingType);

        when(trainingTypeRepository.findAll())
                .thenReturn(Collections.singletonList(trainingType));
        when(trainingTypeMapper.toModelList(trainingTypeList))
                .thenReturn(Collections.singletonList(trainingTypeModel));

        List<TrainingTypeModel> response = trainingTypeService.getAll();
        assertEquals(1, response.size());
        verify(trainingTypeRepository).findAll();
    }

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