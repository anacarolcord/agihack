package com.agi.hack.dto;

import com.agi.hack.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
    @NotBlank(message="O username é obrigatório") String username,
    @NotBlank(message= "O Email é obrigatório") String email,
    @NotBlank(message= "A Senha é obrigatória")  String senha,
    TipoUsuario tipoUsuario,
    Long idFuncionario) {
}
