package com.example.strong.controllers.impl;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.models.response.ResponseAuthorizationModel;
import com.example.strong.services.AuthService;
import com.example.strong.services.impl.TrainingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainingControllerImpl.class)
class TrainingControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TrainingServiceImpl trainingService;
    @MockBean
    private AuthService authService;

    private String username;
    private String contentType;

    @BeforeEach
    void beforeAll() {
        username = "Ivan.Ivanov";
        contentType = "application/json";
    }

    @AfterEach
    void afterEach() {
        username = null;
        contentType = null;
    }

    @Test
    @WithMockUser
    void getAllByTraineeUsername_withValidData_shouldReturnTrainingModelList() throws Exception {
        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName("Ivan");

        List<TrainingModel> trainingModels = new ArrayList<>();
        trainingModels.add(trainingModel);

        mockAuthorization();
        when(trainingService.getAllByTraineeUsername(username, null, null, null, null))
                .thenReturn(trainingModels);

        mockMvc.perform(get("/training/trainee")
                        .header("Authorization", "Bearer token")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].trainerName").value("Ivan"));

        verify(trainingService)
                .getAllByTraineeUsername(username, null, null, null, null);
    }

    @Test
    @WithMockUser
    void getAllByTrainerUsername_withValidData_shouldReturnTrainingModelList() throws Exception {
        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName("Ivan");

        List<TrainingModel> trainingModels = new ArrayList<>();
        trainingModels.add(trainingModel);

        mockAuthorization();
        when(trainingService.getAllByTrainerUsername(username, null, null, null))
                .thenReturn(trainingModels);

        mockMvc.perform(get("/training/trainer")
                        .header("Authorization", "Bearer token")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].trainerName").value("Ivan"));

        verify(trainingService)
                .getAllByTrainerUsername(username, null, null, null);
    }

    @Test
    @WithMockUser
    void create_withValidData_shouldReturnVoid() throws Exception {
        Date trainingDate = new Date();

        CreateTrainingModel createTrainingModel = new CreateTrainingModel();
        createTrainingModel.setTrainingName("training");
        createTrainingModel.setTrainingDate(trainingDate);
        createTrainingModel.setTrainingDuration(100L);
        createTrainingModel.setTraineeUsername("Petya.Petrov");
        createTrainingModel.setTrainerUsername("Ivan.Ivanov");

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName("Ivan");

        mockAuthorization();
        when(trainingService.create(any(CreateTrainingModel.class)))
                .thenReturn(trainingModel);

        mockMvc.perform(post("/training")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(createTrainingModel))
                        .contentType(contentType))
                .andExpect(status().isCreated());

        verify(trainingService)
                .create(any(CreateTrainingModel.class));
    }

    private void mockAuthorization() {
        ResponseAuthorizationModel responseAuthorizationModel = new ResponseAuthorizationModel();
        responseAuthorizationModel.setUsername(username);
        responseAuthorizationModel.setAuthorities("[USER]");

        when(authService.getAuthorities("token"))
                .thenReturn(responseAuthorizationModel);
    }
}