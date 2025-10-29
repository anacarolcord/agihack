package com.agi.hack.service;

import com.agi.hack.config.TokenConfig;
import com.agi.hack.dto.LoginDTO.LoginResponseDTO;
import com.agi.hack.exception.CredenciaisInvalidasException;
import com.agi.hack.model.Usuario;
import com.agi.hack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final TokenConfig tokenConfig;

    @Bean
    public PasswordEncoder passwordEnconder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return usuarioRepository.findUsuariosByUsername(username).orElseThrow(()-> new CredenciaisInvalidasException("Credenciais inválidas, verifique o username e senha insereridos!"));
    }

    public ResponseEntity<LoginResponseDTO> login(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
