package com.example.strong.services;

import com.example.strong.models.response.ResponseAuthorizationModel;

public interface AuthService {
    ResponseAuthorizationModel getAuthorities(String token);
}
