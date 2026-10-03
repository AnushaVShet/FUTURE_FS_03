package com.anvera.anvera_backend.config;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdminTokenStore {

    private static final Map<String, Instant> TOKENS =
            new ConcurrentHashMap<>();

    private AdminTokenStore() {
        // Prevent creating objects of this utility class
    }

    public static Map<String, Instant> getTokens() {
        return TOKENS;
    }
}