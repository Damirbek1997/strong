package com.example.strong.controllers;

import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.services.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get profile of current trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainee profile"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public TraineeModel getProfile(@RequestParam String username) {
        return traineeService.getByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a trainee"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public ResponseCredentialsModel create(@RequestBody @Validated CreateTraineeModel createTraineeModel) {
        return traineeService.create(createTraineeModel);
    }

    @PutMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainee"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public TraineeModel update(@PathVariable Long id, @RequestBody @Valid UpdateTraineeModel updateTraineeModel) {
        return traineeService.update(id, updateTraineeModel);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id:\\d+}/trainer-list")
    @Operation(summary = "Update a trainee's trainer list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainee's trainer list"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public List<ResponseTrainerModel> updateTrainerList(@PathVariable Long id, @RequestBody List<String> usernames) {
        return traineeService.updateTrainerList(id, usernames);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a trainee"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public void deleteByUsername(@RequestParam String username) {
        traineeService.deleteByUsername(username);
    }
}
