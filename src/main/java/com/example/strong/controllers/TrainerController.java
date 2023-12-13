package com.example.strong.controllers;

import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/trainer")
public interface TrainerController {
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get profile of current trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer profile"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    TrainerModel getProfile(@RequestParam String username);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a trainer"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseCredentialsModel create(@RequestBody @Valid CreateTrainerModel createTrainerModel, BindingResult bindingResult);

    @PutMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainer"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    TrainerModel update(@PathVariable Long id, @Valid @RequestBody UpdateTrainerModel updateTrainerModel, BindingResult bindingResult);

    @GetMapping("/not-busy")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all not busy trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved not busy trainers list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<ResponseTrainerModel> getAllNotBusyTrainers();
}