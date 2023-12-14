package com.example.strong.controllers;

import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.services.TrainerService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get profile of current trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer profile"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public TrainerModel getProfile(@RequestParam String username) {
        return trainerService.getByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a trainer"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    @Timed(value = "trainer.creating.time", description = "Time taken to create trainer")
    public ResponseCredentialsModel create(@RequestBody @Valid CreateTrainerModel createTrainerModel) {
        return trainerService.create(createTrainerModel);
    }

    @PutMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainer"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public TrainerModel update(@PathVariable Long id, @RequestBody @Valid UpdateTrainerModel updateTrainerModel) {
        return trainerService.update(id, updateTrainerModel);
    }

    @GetMapping("/not-busy")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all not busy trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved not busy trainers list"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public List<ResponseTrainerModel> getAllNotBusyTrainers() {
        return trainerService.getAllNotBusyTrainers();
    }
}
