package com.agi.hack.dto.FuncionarioDTO;

import lombok.Data;

@Data
public class FuncionarioResponseDTO {

    private Long idFuncionario;
    private String nome;

    // Retorna um objeto simplificado do Setor
    private SetorDTO setor;
}