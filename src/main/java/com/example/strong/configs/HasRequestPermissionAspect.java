package com.example.strong.configs;

import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.exceptions.NotAuthorizedRequestException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class HasRequestPermissionAspect {
    @Before("@annotation(com.example.strong.configs.annotations.PreAuthenticated)")
    public void hasPermission(JoinPoint joinPoint) {
        Object[] objects = joinPoint.getArgs();

        for (Object object : objects) {
            if (object instanceof SecurityAuthentication) {
                return;
            }
        }

        log.error("The request is forbidden");
        throw new NotAuthorizedRequestException("Please, provide authentication");
    }
}