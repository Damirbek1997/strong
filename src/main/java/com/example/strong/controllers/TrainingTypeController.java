package com.example.strong.controllers;

import com.example.strong.models.TrainingTypeModel;
import com.example.strong.services.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training-type")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all training type list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training type list"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public List<TrainingTypeModel> getAll() {
        return trainingTypeService.getAll();
    }
}
