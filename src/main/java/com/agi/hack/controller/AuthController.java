package com.agi.hack.controller;

import com.agi.hack.dto.LoginDTO.LoginRequestDTO;
import com.agi.hack.dto.LoginDTO.LoginResponseDTO;
import com.agi.hack.dto.RegisterDTO.RegisterRequestDTO;
import com.agi.hack.dto.RegisterDTO.RegisterResponseDTO;
import com.agi.hack.service.AuthService;
import com.agi.hack.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        UsernamePasswordAuthenticationToken usernameESenha = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.senha());
        Authentication authentication = authenticationManager.authenticate(usernameESenha);

        return authService.login(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest){
        return usuarioService.novoUsuario(registerRequest);
    }




}
