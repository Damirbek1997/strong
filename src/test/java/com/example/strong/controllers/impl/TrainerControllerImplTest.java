package com.example.strong.controllers.impl;

import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.services.JwtService;
import com.example.strong.services.impl.CustomUserDetailsService;
import com.example.strong.services.impl.TrainerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerControllerImpl.class)
class TrainerControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TrainerServiceImpl trainerService;
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
    void getProfile_withUsername_shouldReturnTrainerModel() throws Exception {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setActive(true);

        mockAuthorization();
        when(trainerService.getByUsername(username))
                .thenReturn(trainerModel);

        mockMvc.perform(get("/trainer/profile")
                        .header("Authorization", "Bearer token")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(trainerModel.getId()))
                .andExpect(jsonPath("$.firstName").value(trainerModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(trainerModel.getLastName()))
                .andExpect(jsonPath("$.username").value(trainerModel.getUsername()))
                .andExpect(jsonPath("$.active").value(trainerModel.getActive()));

        verify(trainerService)
                .getByUsername(username);
    }

    @Test
    @WithMockUser
    void create_withValidData_shouldReturnResponseCredentialsModel() throws Exception {
        CreateTrainerModel createTrainerModel = new CreateTrainerModel();
        createTrainerModel.setFirstName("Petya");
        createTrainerModel.setLastName("Petrov");

        ResponseCredentialsModel responseCredentialsModel = new ResponseCredentialsModel();
        responseCredentialsModel.setUsername("Petya.Petrov");

        when(trainerService.create(any(CreateTrainerModel.class)))
                .thenReturn(responseCredentialsModel);

        mockMvc.perform(post("/trainer")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(createTrainerModel))
                        .contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username").value(responseCredentialsModel.getUsername()));

        verify(trainerService)
                .create(any(CreateTrainerModel.class));
    }

    @Test
    @WithMockUser
    void update_withValidData_shouldReturnTrainerModel() throws Exception {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setActive(true);

        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setFirstName("Petya");
        updateTrainerModel.setLastName("Petrov");

        mockAuthorization();
        when(trainerService.update(eq(1L), any(UpdateTrainerModel.class)))
                .thenReturn(trainerModel);

        mockMvc.perform(put("/trainer/{id}", 1L)
                        .header("Authorization", "Bearer token")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(updateTrainerModel))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(trainerModel.getId()))
                .andExpect(jsonPath("$.firstName").value(trainerModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(trainerModel.getLastName()))
                .andExpect(jsonPath("$.username").value(trainerModel.getUsername()))
                .andExpect(jsonPath("$.active").value(trainerModel.getActive()));

        verify(trainerService)
                .update(eq(1L), any(UpdateTrainerModel.class));
    }

    @Test
    @WithMockUser
    void getAllNotBusyTrainers_withValidData_shouldReturnResponseTrainerModelList() throws Exception {
        ResponseTrainerModel responseTrainerModel = new ResponseTrainerModel();
        responseTrainerModel.setUsername("Ivan.Ivanov");
        responseTrainerModel.setFirstName("Ivan");
        responseTrainerModel.setLastName("Ivanov");

        List<ResponseTrainerModel> responseTrainerModels = new ArrayList<>();
        responseTrainerModels.add(responseTrainerModel);

        mockAuthorization();
        when(trainerService.getAllNotBusyTrainers())
                .thenReturn(responseTrainerModels);

        mockMvc.perform(get("/trainer/not-busy")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].firstName").value("Ivan"))
                .andExpect(jsonPath("$[0].lastName").value("Ivanov"))
                .andExpect(jsonPath("$[0].username").value("Ivan.Ivanov"));

        verify(trainerService)
                .getAllNotBusyTrainers();
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