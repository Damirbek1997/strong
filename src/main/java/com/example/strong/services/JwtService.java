package com.example.strong.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    Boolean validateToken(String token);
    String extractUsername(String token);
}