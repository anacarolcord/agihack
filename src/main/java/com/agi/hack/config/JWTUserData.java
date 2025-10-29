package com.agi.hack.config;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long idUsuario,
        String email
) {
}
