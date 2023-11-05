package com.example.strong.controllers.impl;

import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.services.impl.TraineeServiceImpl;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeControllerImplTest {
    @InjectMocks
    private TraineeControllerImpl traineeController;
    @Mock
    TraineeServiceImpl traineeService;

    private String username;

    @BeforeEach
    void beforeAll() {
        RestAssuredMockMvc.standaloneSetup(traineeController);
        username = "Ivan.Ivanov";
    }

    @AfterEach
    void afterEach() {
        username = null;
    }

    @Test
    void getProfile_withUsernameAndPassword_shouldReturnTraineeModel() {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Ivan");
        traineeModel.setLastName("Ivanov");
        traineeModel.setUsername("Ivan.Ivanov");
        traineeModel.setActive(true);

        when(traineeService.getByUsername(username))
                .thenReturn(traineeModel);

        RestAssuredMockMvc.given()
                .header("username", username)
                .when()
                .get("/trainee/profile")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value())
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("Ivan"))
                .body("lastName", Matchers.equalTo("Ivanov"))
                .body("active", Matchers.equalTo(true))
                .body("username", Matchers.equalTo("Ivan.Ivanov"));

        verify(traineeService)
                .getByUsername(username);
    }

    @Test
    void create_withValidData() {
        Date birthDate = new Date();

        CreateTraineeModel createTraineeModel = new CreateTraineeModel();
        createTraineeModel.setFirstName("Petya");
        createTraineeModel.setLastName("Petrov");
        createTraineeModel.setAddress("address");
        createTraineeModel.setBirthday(birthDate);

        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setActive(true);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(createTraineeModel)
                .when()
                .post("/trainee")
                .then()
                .log().all().statusCode(HttpStatus.CREATED.value());

        when(traineeService.getByUsername("Petya.Petrov"))
                .thenReturn(traineeModel);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .header("username", "Petya.Petrov")
                .when()
                .get("/trainee/profile")
                .then()
                .log().all().statusCode(HttpStatus.OK.value())
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("Petya"))
                .body("lastName", Matchers.equalTo("Petrov"))
                .body("active", Matchers.equalTo(true))
                .body("username", Matchers.equalTo("Petya.Petrov"));
    }

    @Test
    void update_withValidData() {
        Date birthDate = new Date();

        UpdateTraineeModel updateTraineeModel = new UpdateTraineeModel();
        updateTraineeModel.setFirstName("Petya");
        updateTraineeModel.setLastName("Petrov");
        updateTraineeModel.setAddress("address");
        updateTraineeModel.setBirthday(birthDate);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(updateTraineeModel)
                .when()
                .put("/trainee/1")
                .then()
                .log().all().statusCode(HttpStatus.OK.value());
    }

    @Test
    void updateTrainerList_withValidData() {
        List<String> trainerUsernames = new ArrayList<>();
        trainerUsernames.add("Petya.Petrov");

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(trainerUsernames)
                .when()
                .put("/trainee/1/trainer-list")
                .then()
                .log().all().statusCode(HttpStatus.OK.value());
    }

    @Test
    void deleteByUsername_withValidData_shouldReturnVoid() {
        TraineeModel traineeModel = new TraineeModel();
        traineeModel.setId(1L);
        traineeModel.setFirstName("Petya");
        traineeModel.setLastName("Petrov");
        traineeModel.setUsername("Petya.Petrov");
        traineeModel.setActive(true);

        when(traineeService.getByUsername("Petya.Petrov"))
                .thenReturn(traineeModel);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .header("username", "Petya.Petrov")
                .when()
                .delete("/trainee")
                .then()
                .log().all().statusCode(HttpStatus.OK.value());
    }
}