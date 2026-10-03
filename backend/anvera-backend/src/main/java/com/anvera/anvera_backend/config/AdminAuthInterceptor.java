
package com.anvera.anvera_backend.config;

import java.time.Instant;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final Map<String, Instant> activeTokens;

    public AdminAuthInterceptor() {
        this.activeTokens = AdminTokenStore.getTokens();
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        // Allow OPTIONS requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("X-Admin-Token");

        if (token == null || token.isBlank()) {
            sendUnauthorized(response);
            return false;
        }

        Instant expiry = activeTokens.get(token);

        if (expiry == null) {
            sendUnauthorized(response);
            return false;
        }

        if (Instant.now().isAfter(expiry)) {
            activeTokens.remove(token);
            sendUnauthorized(response);
            return false;
        }

        return true;
    }

    private void sendUnauthorized(HttpServletResponse response)
            throws Exception {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write(
                "{\"success\":false,\"message\":\"Admin authentication required\"}"
        );
    }
}