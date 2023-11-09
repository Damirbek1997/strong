package com.example.strong.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "transactionLoggingFilter", urlPatterns = "/*")
public class TransactionLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String transactionId = generateTransactionId();
        MDC.put("transactionId", transactionId);

        try {
            log.info("Request came: endpoint {} called", request.getRequestURI());
            response.setHeader("transactionId", transactionId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
