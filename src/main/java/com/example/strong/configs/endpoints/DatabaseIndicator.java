package com.example.strong.configs.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class DatabaseIndicator implements HealthIndicator {
    private final EntityManager entityManager;

    @Override
    public Health health() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("Error", e.getMessage()).build();
        }
    }
}
