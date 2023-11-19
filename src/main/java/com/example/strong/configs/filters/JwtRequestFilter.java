package com.example.strong.configs.filters;

import com.example.strong.services.JwtService;
import com.example.strong.services.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authz = getAuthorizationHeader(request);

            if (authz == null) {
                return;
            }

            UserDetails userDetails = retrieveUserDetails(request);
            String token = extractToken(request);

            if (isTokenValid(token, userDetails)) {
                setAuthenticationToContext(userDetails, request);
            } else {
                log.error("There is no Authentication in Headers");
            }
        } catch (Exception ex) {
            log.error("Something went wrong", ex);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtService.validateToken(token, userDetails);
    }

    private void setAuthenticationToContext(UserDetails userDetails, HttpServletRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private String extractToken(HttpServletRequest request) {
        return getAuthorizationHeader(request)
                .substring(7);
    }

    private String extractUsername(HttpServletRequest request) {
        return jwtService.extractUsername(extractToken(request));
    }

    private UserDetails retrieveUserDetails(HttpServletRequest request) {
        return customUserDetailsService.loadUserByUsername(extractUsername(request));
    }
}
