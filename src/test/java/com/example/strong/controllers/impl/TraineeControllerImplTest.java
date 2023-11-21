package com.example.strong.controllers.impl;

import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.services.JwtService;
import com.example.strong.services.impl.CustomUserDetailsService;
import com.example.strong.services.impl.TraineeServiceImpl;
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
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TraineeControllerImpl.class)
class TraineeControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TraineeServiceImpl traineeService;
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
    void getProfile_withUsernameAndPassword_shouldReturnTraineeModel() throws Exception {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setActive(true);

        mockAuthorization();
        when(traineeService.getByUsername(username))
                .thenReturn(traineeModel);

        mockMvc.perform(get("/trainee/profile")
                        .header("Authorization", "Bearer token")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(traineeModel.getId()))
                .andExpect(jsonPath("$.firstName").value(traineeModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(traineeModel.getLastName()))
                .andExpect(jsonPath("$.username").value(traineeModel.getUsername()))
                .andExpect(jsonPath("$.active").value(traineeModel.getActive()));

        verify(traineeService)
                .getByUsername(username);
    }

    @Test
    @WithMockUser
    void create_withValidData_shouldReturnResponseCredentialsModel() throws Exception {
        Date birthDate = new Date();

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setFirstName("Petya");
        createTraineeModel.setLastName("Petrov");
        createTraineeModel.setAddress("address");
        createTraineeModel.setBirthday(birthDate);

        ResponseCredentialsModel responseCredentialsModel = new ResponseCredentialsModel();
        responseCredentialsModel.setUsername("Petya.Petrov");

        when(traineeService.create(any(CreateTraineeModel.class)))
                .thenReturn(responseCredentialsModel);

        mockMvc.perform(post("/trainee")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(createTraineeModel))
                        .contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username").value(responseCredentialsModel.getUsername()));

        verify(traineeService)
                .create(any(CreateTraineeModel.class));
    }

    @Test
    @WithMockUser
    void update_withValidData_shouldReturnTraineeModel() throws Exception {
        Date birthDate = new Date();

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setActive(true);

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setFirstName("Petya");
        updateTraineeModel.setLastName("Petrov");
        updateTraineeModel.setAddress("address");
        updateTraineeModel.setBirthday(birthDate);

        mockAuthorization();
        when(traineeService.update(eq(1L), any(UpdateTraineeModel.class)))
                .thenReturn(traineeModel);

        mockMvc.perform(put("/trainee/{id}", 1L)
                        .header("Authorization", "Bearer token")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(updateTraineeModel))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(traineeModel.getId()))
                .andExpect(jsonPath("$.firstName").value(traineeModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(traineeModel.getLastName()))
                .andExpect(jsonPath("$.username").value(traineeModel.getUsername()))
                .andExpect(jsonPath("$.active").value(traineeModel.getActive()));

        verify(traineeService)
                .update(eq(1L), any(UpdateTraineeModel.class));
    }

    @Test
    @WithMockUser
    void updateTrainerList_withValidData_shouldReturnResponseTrainerModelList() throws Exception {
        List<String> trainerUsernames = new ArrayList<>();
        trainerUsernames.add("Petya.Petrov");

        ResponseTrainerModel responseTrainerModel = new ResponseTrainerModel();
        responseTrainerModel.setUsername("Petya.Petrov");

        List<ResponseTrainerModel> responseTrainerModels = new ArrayList<>();
        responseTrainerModels.add(responseTrainerModel);

        mockAuthorization();
        when(traineeService.updateTrainerList(eq(1L), anyList()))
                .thenReturn(responseTrainerModels);

        mockMvc.perform(put("/trainee/{id}/trainer-list", 1L)
                        .header("Authorization", "Bearer token")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(trainerUsernames))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[0].username").value("Petya.Petrov"));

        verify(traineeService)
                .updateTrainerList(eq(1L), anyList());
    }

    @Test
    @WithMockUser
    void deleteByUsername_withValidData_shouldReturnVoid() throws Exception {
        mockAuthorization();
        doNothing()
                .when(traineeService)
                .deleteByUsername(username);

        mockMvc.perform(delete("/trainee")
                        .with(csrf())
                        .header("Authorization", "Bearer token")
                        .param("username", username))
                .andExpect(status().isOk());

        verify(traineeService)
                .deleteByUsername(username);
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