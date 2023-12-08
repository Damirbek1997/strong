package com.example.strong.clients.auth;

import com.example.strong.exceptions.UnexpectedException;
import com.example.strong.models.response.ResponseAuthorizationModel;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    private static final String ERROR_MSG = "Auth service is unavailable";

    @Override
    public ResponseAuthorizationModel getAuthorities(String token) {
        throw new UnexpectedException(ERROR_MSG);
    }
}
