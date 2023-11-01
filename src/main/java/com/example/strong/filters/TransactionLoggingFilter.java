package com.example.strong.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> parameters = getParameters(request);
        Map<String, String> reqHeaders = getHeaders(request);

        try {
            log.info("Transaction ({}) started: endpoint {} called; parameters {}; headers {}", transactionId, request.getRequestURI(), parameters, reqHeaders);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(request, responseWrapper);
            logResponse(transactionId, request.getRequestURI(), responseWrapper);
        } finally {
            MDC.clear();
        }
    }

    private void logResponse(String transactionId, String requestURI, ContentCachingResponseWrapper responseWrapper) throws IOException {
        String responseBody = new String(responseWrapper.getContentAsByteArray());
        Map<String, String> headers = new HashMap<>();

        for (String headerName : responseWrapper.getHeaderNames()) {
            headers.put(headerName, responseWrapper.getHeader(headerName));
        }

        log.info("Transaction ({}) ended: endpoint {} called; response headers {} and body {}", transactionId, requestURI, headers, responseBody);
        responseWrapper.copyBodyToResponse(); // Restore the response body for further processing
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> params = request.getHeaderNames();

        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            headers.put(paramName, request.getHeader(paramName));
        }

        return headers;
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        return parameters;
    }
}
