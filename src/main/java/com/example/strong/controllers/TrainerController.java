package com.example.strong.controllers;

import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
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

import java.util.List;

@RequestMapping("/trainer")
public interface TrainerController {
    @GetMapping("/profile")
    @Operation(summary = "Get profile of current trainer")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer profile"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<TrainerModel> getProfile(@RequestHeader(value = "username", required = false) String username);

    @PostMapping
    @Operation(summary = "Create a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a trainer"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<ResponseCredentialsModel> create(@RequestBody CreateTrainerModel createTrainerModel);

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Update a trainer")
    @Parameters({
            @Parameter(name = "username", in = ParameterIn.HEADER, schema = @Schema(type = "string")),
            @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainer"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<TrainerModel> update(@PathVariable Long id, @RequestBody UpdateTrainerModel updateTrainerModel);

    @GetMapping("/not-busy")
    @Operation(summary = "Get all not busy trainers")
    @Parameters({
            @Parameter(name = "username", in = ParameterIn.HEADER, schema = @Schema(type = "string")),
            @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved not busy trainers list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<List<ResponseTrainerModel>> getAllNotBusyTrainers();

    @PatchMapping("/status")
    @Operation(summary = "Update trainer status")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated status of a trainer"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<String> updateStatus(@RequestHeader(value = "username", required = false) String username,
                                        @RequestParam Boolean isActive);
}