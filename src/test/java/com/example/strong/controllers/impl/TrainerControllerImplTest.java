package com.example.strong.controllers.impl;

import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.services.impl.TrainerServiceImpl;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerControllerImplTest {
    @InjectMocks
    private TrainerControllerImpl trainerController;
    @Mock
    TrainerServiceImpl trainerService;

    private String username;

    @BeforeEach
    void beforeAll() {
        RestAssuredMockMvc.standaloneSetup(trainerController);
        username = "Ivan.Ivanov";
    }

    @AfterEach
    void afterEach() {
        username = null;
    }

    @Test
    void getProfile_withUsernameAndPassword_shouldReturnTraineeModel() {
        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Ivan");
        trainerModel.setLastName("Ivanov");
        trainerModel.setUsername("Ivan.Ivanov");
        trainerModel.setActive(true);

        when(trainerService.getByUsername(username))
                .thenReturn(trainerModel);

        RestAssuredMockMvc.given()
                .header("username", username)
                .when()
                .get("/trainer/profile")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value())
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("Ivan"))
                .body("lastName", Matchers.equalTo("Ivanov"))
                .body("active", Matchers.equalTo(true))
                .body("username", Matchers.equalTo("Ivan.Ivanov"));

        verify(trainerService)
                .getByUsername(username);
    }

    @Test
    void create_withValidData() {
        CreateTrainerModel createTrainerModel = new CreateTrainerModel();
        createTrainerModel.setFirstName("Petya");
        createTrainerModel.setLastName("Petrov");

        TrainerModel trainerModel = new TrainerModel();
        trainerModel.setId(1L);
        trainerModel.setFirstName("Petya");
        trainerModel.setLastName("Petrov");
        trainerModel.setUsername("Petya.Petrov");
        trainerModel.setActive(true);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(createTrainerModel)
                .when()
                .post("/trainer")
                .then()
                .log().all().statusCode(HttpStatus.CREATED.value());

        when(trainerService.getByUsername("Petya.Petrov"))
                .thenReturn(trainerModel);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .header("username", "Petya.Petrov")
                .when()
                .get("/trainer/profile")
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
        UpdateTrainerModel updateTrainerModel = new UpdateTrainerModel();
        updateTrainerModel.setFirstName("Petya");
        updateTrainerModel.setLastName("Petrov");

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(updateTrainerModel)
                .when()
                .put("/trainer/1")
                .then()
                .log().all().statusCode(HttpStatus.OK.value());
    }

    @Test
    void getAllNotBusyTrainers() {
        RestAssuredMockMvc.given()
                .when()
                .get("/trainer/not-busy")
                .then()
                .log().all().statusCode(HttpStatus.OK.value());
    }
}