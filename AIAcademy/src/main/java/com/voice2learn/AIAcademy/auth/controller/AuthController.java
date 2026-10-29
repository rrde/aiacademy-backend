package com.voice2learn.AIAcademy.auth.controller;

import com.voice2learn.AIAcademy.auth.dto.LoginResponse;
import com.voice2learn.AIAcademy.auth.dto.RegisterRequest;
import com.voice2learn.AIAcademy.auth.dto.UserProfileResponse;
import com.voice2learn.AIAcademy.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.voice2learn.AIAcademy.auth.dto.LoginRequest;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication) {
        return new UserProfileResponse(authentication.getName());
    }

    @GetMapping("/whoami")
    public Map<String, Object> whoAmI(
            Authentication authentication) {

        Map<String, Object> response = new HashMap<>();

        response.put(
                "email",
                authentication.getName());

        response.put(
                "authorities",
                authentication
                        .getAuthorities());

        return response;
    }


}