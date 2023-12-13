package com.example.strong.controllers;

import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
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

@RequestMapping("/trainee")
public interface TraineeController {
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get profile of current trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainee profile"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    TraineeModel getProfile(@RequestParam String username);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a trainee"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseCredentialsModel create(@RequestBody @Valid CreateTraineeModel createTraineeModel, BindingResult bindingResult);

    @PutMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainee"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    TraineeModel update(@PathVariable Long id, @RequestBody @Valid UpdateTraineeModel updateTraineeModel, BindingResult bindingResult);

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id:\\d+}/trainer-list")
    @Operation(summary = "Update a trainee's trainer list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainee's trainer list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<ResponseTrainerModel> updateTrainerList(@PathVariable Long id, @RequestBody List<String> usernames);

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a trainee"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    void deleteByUsername(@RequestParam String username);
}
