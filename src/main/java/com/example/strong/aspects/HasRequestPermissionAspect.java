package com.example.strong.aspects;

import com.example.strong.exceptions.BadRequestException;
import com.example.strong.exceptions.UnauthorizedRequestException;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class HasRequestPermissionAspect {
    private final UserService userService;

    @Before("@annotation(com.example.strong.configs.annotations.PreAuthenticated)")
    public void hasPermission(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String username = request.getHeader("username");
        String password = request.getHeader("password");

        if (username == null || password == null) {
            throw new UnauthorizedRequestException("Please, provide username and password");
        }

        if (!userService.isUserExist(username, password)) {
            throw new BadRequestException("Provide correct username and password!");
        }
    }
}