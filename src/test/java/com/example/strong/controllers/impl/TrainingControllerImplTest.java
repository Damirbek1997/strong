package com.example.strong.controllers.impl;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.services.impl.TrainingServiceImpl;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingControllerImplTest {
    @InjectMocks
    private TrainingControllerImpl trainingController;
    @Mock
    TrainingServiceImpl trainingService;

    private String username;

    @BeforeEach
    void beforeAll() {
        RestAssuredMockMvc.standaloneSetup(trainingController);
        username = "Ivan.Ivanov";
    }

    @AfterEach
    void afterEach() {
        username = null;
    }

    @Test
    void getAllByTraineeUsername() {
        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName("Ivan");

        when(trainingService.getAllByTraineeUsername(username, null, null, null, null))
                .thenReturn(Collections.singletonList(trainingModel));

        RestAssuredMockMvc.given()
                .header("username", username)
                .when()
                .get("/training/trainee")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value())
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.notNullValue())
                .body("[0].trainerName", Matchers.equalTo("Ivan"));

        verify(trainingService)
                .getAllByTraineeUsername(username, null, null, null, null);
    }

    @Test
    void getAllByTrainerUsername() {
        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(1L);
        trainingModel.setTrainerName("Ivan");

        when(trainingService.getAllByTrainerUsername(username, null, null, null))
                .thenReturn(Collections.singletonList(trainingModel));

        RestAssuredMockMvc.given()
                .header("username", username)
                .when()
                .get("/training/trainer")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value())
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.notNullValue())
                .body("[0].trainerName", Matchers.equalTo("Ivan"));

        verify(trainingService)
                .getAllByTrainerUsername(username, null, null, null);
    }

    @Test
    void create_withValidData_shouldReturnVoid() {
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

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(createTrainingModel)
                .when()
                .post("/training")
                .then()
                .log().all().statusCode(HttpStatus.CREATED.value());

        when(trainingService.getAllByTrainerUsername(username, null, null, null))
                .thenReturn(Collections.singletonList(trainingModel));

        RestAssuredMockMvc.given()
                .header("username", username)
                .when()
                .get("/training/trainer")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value())
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.notNullValue())
                .body("[0].trainerName", Matchers.equalTo("Ivan"));
    }
}