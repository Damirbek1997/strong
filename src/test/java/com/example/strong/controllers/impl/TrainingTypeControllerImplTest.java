package com.example.strong.controllers.impl;

import com.example.strong.models.TrainingTypeModel;
import com.example.strong.services.JwtService;
import com.example.strong.services.impl.CustomUserDetailsService;
import com.example.strong.services.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainingTypeControllerImpl.class)
class TrainingTypeControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrainingTypeServiceImpl trainingTypeService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private CustomUserDetailsService userDetailsService;

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
    void getAll_withValidData_shouldReturnTrainingTypeModelList() throws Exception {
        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        List<TrainingTypeModel> trainingTypeModels = new ArrayList<>();
        trainingTypeModels.add(trainingTypeModel);

        mockAuthorization();
        when(trainingTypeService.getAll())
                .thenReturn(trainingTypeModels);

        mockMvc.perform(get("/training-type")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id").value(trainingTypeModel.getId()))
                .andExpect(jsonPath("$[0].typeName").value(trainingTypeModel.getTypeName()));

        verify(trainingTypeService)
                .getAll();
    }

    private void mockAuthorization() {
        UserDetails userDetails = new User("username", "password", new ArrayList<>());

        when(jwtService.validateToken("token"))
                .thenReturn(true);
        when(jwtService.extractUsername("token"))
                .thenReturn(username);
        when(userDetailsService.loadUserByUsername(username))
                .thenReturn(userDetails);
    }
}