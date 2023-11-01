package com.example.strong.controllers;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/training")
public interface TrainingController {
    @GetMapping("/trainee")
    @Operation(summary = "Get all trainings list by trainee username and filter data")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<List<TrainingModel>> getAllByTraineeUsername(@RequestHeader(name = "username", required = false) String username,
                                                                @RequestParam(required = false) Date periodFrom,
                                                                @RequestParam(required = false) Date periodTo,
                                                                @RequestParam(required = false) String trainerName,
                                                                @RequestParam(required = false) Long trainingTypeId);

    @GetMapping("/trainer")
    @Operation(summary = "Get all trainings list by trainer username and filter data")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<List<TrainingModel>> getAllByTrainerUsername(@RequestHeader(name = "username", required = false) String username,
                                                                @RequestParam(required = false) Date periodFrom,
                                                                @RequestParam(required = false) Date periodTo,
                                                                @RequestParam(required = false) String traineeName);

    @PostMapping
    @Operation(summary = "Create a new training")
    @Parameters({
            @Parameter(name = "username", in = ParameterIn.HEADER, schema = @Schema(type = "string")),
            @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a training"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<String> create(@RequestBody CreateTrainingModel createTrainingModel);
}
