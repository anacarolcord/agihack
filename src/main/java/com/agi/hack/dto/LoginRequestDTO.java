package com.agi.hack.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message= "O Email é obrigatório") String email,
        @NotBlank(message= "A Senha é obrigatória")  String senha) {
}
