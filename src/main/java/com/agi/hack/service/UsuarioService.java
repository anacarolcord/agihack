package com.agi.hack.service;

import com.agi.hack.dto.RegisterDTO.RegisterRequestDTO;
import com.agi.hack.dto.RegisterDTO.RegisterResponseDTO;
import com.agi.hack.mapper.UsuarioMapper;
import com.agi.hack.model.Usuario;
import com.agi.hack.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;


    public ResponseEntity<RegisterResponseDTO> novoUsuario(@Valid RegisterRequestDTO registerRequest) {
        Usuario usuario = new Usuario();

        usuario.setUsername(registerRequest.username());
        usuario.setEmail(registerRequest.email());
        usuario.setSenha(passwordEncoder.encode(registerRequest.senha()));
        usuario.setTipoUsuario(registerRequest.tipoUsuario());

        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toRegisterResponseDto(usuario));
    }
}
