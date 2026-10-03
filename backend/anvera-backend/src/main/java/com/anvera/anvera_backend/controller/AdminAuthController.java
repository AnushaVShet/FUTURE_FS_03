package com.anvera.anvera_backend.controller;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.anvera.anvera_backend.config.AdminTokenStore;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    // Temporary development credentials.
    // These will be moved out of the source code later.
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "anvera123";

    // Token validity: 2 hours
    private static final long TOKEN_VALIDITY_SECONDS = 2 * 60 * 60;

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody Map<String, String> loginData) {

        String username = loginData.get("username");
        String password = loginData.get("password");

        if (ADMIN_USERNAME.equals(username)
                && ADMIN_PASSWORD.equals(password)) {

            String token = UUID.randomUUID().toString();

            AdminTokenStore.getTokens().put(
                    token,
                    Instant.now().plusSeconds(TOKEN_VALIDITY_SECONDS)
            );

            return Map.of(
                    "success", true,
                    "message", "Admin login successful",
                    "token", token
            );
        }

        return Map.of(
                "success", false,
                "message", "Invalid username or password"
        );
    }

    @GetMapping("/verify")
    public Map<String, Object> verifyToken(
            @RequestHeader(
                    value = "X-Admin-Token",
                    required = false
            ) String token) {

        if (isValidToken(token)) {
            return Map.of(
                    "authenticated", true
            );
        }

        return Map.of(
                "authenticated", false
        );
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(
            @RequestHeader(
                    value = "X-Admin-Token",
                    required = false
            ) String token) {

        if (token != null) {
            AdminTokenStore.getTokens().remove(token);
        }

        return Map.of(
                "success", true,
                "message", "Logged out successfully"
        );
    }

    private boolean isValidToken(String token) {

        if (token == null || token.isBlank()) {
            return false;
        }

        Instant expiry =
                AdminTokenStore.getTokens().get(token);

        if (expiry == null) {
            return false;
        }

        if (Instant.now().isAfter(expiry)) {
            AdminTokenStore.getTokens().remove(token);
            return false;
        }

        return true;
    }
}