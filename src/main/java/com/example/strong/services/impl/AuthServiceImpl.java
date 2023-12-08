package com.example.strong.services.impl;

import com.example.strong.clients.auth.AuthServiceClient;
import com.example.strong.models.response.ResponseAuthorizationModel;
import com.example.strong.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthServiceClient authServiceClient;

    @Override
    public ResponseAuthorizationModel getAuthorities(String token) {
        return authServiceClient.getAuthorities(token);
    }
}
