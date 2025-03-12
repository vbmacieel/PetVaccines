package com.project.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.auth_service.dto.AuthLoginDTO;
import com.project.auth_service.dto.AuthRegisterDTO;
import com.project.auth_service.dto.AuthTokenDTO;
import com.project.auth_service.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AuthRegisterDTO registerDTO) {
        service.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDTO> login(@Valid @RequestBody AuthLoginDTO loginDTO) {
        AuthTokenDTO authToken = service.login(loginDTO);
        return ResponseEntity.ok(authToken);
    }
}
