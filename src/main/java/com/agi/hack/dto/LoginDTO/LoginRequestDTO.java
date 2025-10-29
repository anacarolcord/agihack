package com.agi.hack.dto.LoginDTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message="O Username é obrigatórrio") String username,
        @NotBlank(message= "A Senha é obrigatória")  String senha) {
}
