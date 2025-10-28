package com.agi.hack.config;

import com.agi.hack.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenConfig {

    private String segredo = "segredo";

    public String gerarToken(Usuario usuario){

        Algorithm algorithm = Algorithm.HMAC256(segredo);

        return JWT.create()
                .withClaim("idUsuario", usuario.getId())
                .withSubject(usuario.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(90000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
}
