package com.example.strong.controllers;

import com.example.strong.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/change-credentials")
    @Operation(summary = "Update user credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user credentials"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public void changeCredentials(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @PatchMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update user status")
    @Parameter(name = "password", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated status of a user"),
            @ApiResponse(responseCode = "400", description = "The request is incorrect", content = @Content),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    public void updateStatus(@RequestParam String username, @RequestParam Boolean active) {
        if (active) {
            userService.activateByUsername(username);
        } else {
            userService.deactivateByUsername(username);
        }
    }
}
