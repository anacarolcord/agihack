package com.agi.hack.controller;

import com.agi.hack.dto.LoginRequestDTO;
import com.agi.hack.dto.LoginResponseDTO;
import com.agi.hack.dto.RegisterRequestDTO;
import com.agi.hack.dto.RegisterResponseDTO;
import com.agi.hack.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest){
        return authService.novoUsuario(registerRequest);
    }




}
