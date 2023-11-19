package com.example.strong.controllers;

import com.example.strong.models.TrainingTypeModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/training-type")
public interface TrainingTypeController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all training type list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training type list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<TrainingTypeModel> getAll();
}
