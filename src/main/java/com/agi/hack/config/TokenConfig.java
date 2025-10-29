package com.agi.hack.config;

import com.agi.hack.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

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

    public Optional<JWTUserData> validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(segredo);

            DecodedJWT decode = JWT.require(algorithm)
                    .build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .idUsuario(decode.getClaim("idUsuario").asLong())
                    .email(decode.getSubject())
                    .build());
        }
        catch(JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
