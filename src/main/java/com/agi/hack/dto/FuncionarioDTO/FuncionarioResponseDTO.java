package com.agi.hack.dto.FuncionarioDTO;

import com.agi.hack.enums.StatusFuncionario;
import com.agi.hack.dto.SetorDTO.SetorResponseDTO;  // Import adicionado
import com.agi.hack.dto.CargoDTO.CargoDTO;  // Import adicionado
import lombok.Data;

@Data
public class FuncionarioResponseDTO {

    private Long idFuncionario;
    private String nome;
    private String cpf;
    private String email;

    // Status (enum)
    private StatusFuncionario status;

    // DTO simplificado do Setor (CORREÇÃO: Campo renomeado de 'idSetor' para 'setor')
    private SetorResponseDTO setor;

    // Novo DTO simplificado do Cargo (CORREÇÃO: Campo renomeado de 'idCargo' para 'cargo')
    private CargoDTO cargo;
}