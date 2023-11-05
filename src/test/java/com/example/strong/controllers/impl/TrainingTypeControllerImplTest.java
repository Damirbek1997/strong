package com.example.strong.controllers.impl;

import com.example.strong.models.TrainingTypeModel;
import com.example.strong.services.impl.TrainingTypeServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeControllerImplTest {
    @InjectMocks
    private TrainingTypeControllerImpl trainingTypeController;
    @Mock
    TrainingTypeServiceImpl trainingTypeService;


    @BeforeEach
    void beforeAll() {
        RestAssuredMockMvc.standaloneSetup(trainingTypeController);
    }

    @Test
    void getAll() {
        TrainingTypeModel trainingTypeModel = new TrainingTypeModel();
        trainingTypeModel.setId(1L);
        trainingTypeModel.setTypeName("type");

        when(trainingTypeService.getAll())
                .thenReturn(Collections.singletonList(trainingTypeModel));

        RestAssuredMockMvc.given()
                .when()
                .get("/training-type")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value())
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.notNullValue())
                .body("[0].typeName", Matchers.equalTo("type"));

        verify(trainingTypeService)
                .getAll();
    }
}