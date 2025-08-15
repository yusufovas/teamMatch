package com.example.teamMatch.controllers;

import com.example.teamMatch.dto.auth.LoginDto;
import com.example.teamMatch.dto.user.UserResponseDto;
import com.example.teamMatch.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody UserResponseDto userResponseDto) {
        return ResponseEntity.ok(authService.signup(userResponseDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signin(@RequestBody LoginDto loginDto) {
        String token = authService.signin(loginDto);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
