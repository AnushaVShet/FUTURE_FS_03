package com.anvera.anvera_backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/protected")
public class AdminProtectedController {

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        return Map.of(
                "success", true,
                "message", "Admin authentication successful",
                "admin", true
        );
    }
}