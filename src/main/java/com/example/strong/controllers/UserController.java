package com.example.strong.controllers;

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

@RequestMapping("/user")
public interface UserController {
    @GetMapping("/login")
    @Operation(summary = "Login")
    @Parameters({
            @Parameter(name = "username", in = ParameterIn.HEADER, schema = @Schema(type = "string")),
            @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<String> login();

    @PutMapping("/change-credentials")
    @Operation(summary = "Update user credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user credentials"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    ResponseEntity<String> changeCredentials(@RequestHeader(value = "username", required = false) String username,
                                             @RequestHeader(value = "password", required = false) String password,
                                             @RequestParam String newPassword);
}
