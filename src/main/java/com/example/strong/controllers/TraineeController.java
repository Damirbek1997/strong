package com.example.strong.controllers;

import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
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

@RequestMapping("/trainee")
public interface TraineeController {
    @GetMapping("/profile")
    @Operation(summary = "Get profile of current trainee")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainee profile"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<TraineeModel> getProfile(@RequestHeader(name = "username", required = false) String username);

    @PostMapping
    @Operation(summary = "Create a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a trainee"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<ResponseCredentialsModel> create(@RequestBody CreateTraineeModel createTraineeModel);

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Update a trainee")
    @Parameters({
            @Parameter(name = "username", in = ParameterIn.HEADER, schema = @Schema(type = "string")),
            @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainee"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<TraineeModel> update(@PathVariable Long id, @RequestBody UpdateTraineeModel updateTraineeModel);

    @PutMapping("/{id:\\d+}/trainer-list")
    @Operation(summary = "Update a trainee's trainer list")
    @Parameters({
            @Parameter(name = "username", in = ParameterIn.HEADER, schema = @Schema(type = "string")),
            @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a trainee's trainer list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<List<ResponseTrainerModel>> updateTrainerList(@PathVariable Long id, @RequestBody List<String> usernames);

    @DeleteMapping
    @Operation(summary = "Delete trainee by username")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a trainee"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<String> deleteByUsername(@RequestHeader(name = "username", required = false) String username);
}
