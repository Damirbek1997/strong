package com.example.strong.controllers;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/training")
public interface TrainingController {
    @GetMapping("/trainee")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all trainings list by trainee username and filter data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<TrainingModel> getAllByTraineeUsername(@RequestParam String username,
                                                @RequestParam(required = false) Date periodFrom,
                                                @RequestParam(required = false) Date periodTo,
                                                @RequestParam(required = false) String trainerName,
                                                @RequestParam(required = false) Long trainingTypeId);

    @GetMapping("/trainer")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all trainings list by trainer username and filter data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<TrainingModel> getAllByTrainerUsername(@RequestParam String username,
                                                @RequestParam(required = false) Date periodFrom,
                                                @RequestParam(required = false) Date periodTo,
                                                @RequestParam(required = false) String traineeName);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a training"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    void create(@RequestBody CreateTrainingModel createTrainingModel);
}
