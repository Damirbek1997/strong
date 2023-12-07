package com.example.strong.clients.workload;

import com.example.strong.models.response.ResponseAuthorizationModel;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFallback implements AuthServiceClient {
    @Override
    public ResponseAuthorizationModel getAuthorities(String token) {
        return null;
    }
}
