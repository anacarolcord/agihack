package com.agi.hack.dto.FuncionarioDTO;

import com.agi.hack.enums.StatusFuncionario;
import com.agi.hack.dto.SetorDTO.SetorDTO;  // Import adicionado
import com.agi.hack.dto.CargoDTO.CargoDTO;  // Import adicionado
import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioResponseDTO {

    private Long idFuncionario;
    private String nome;
    private String cpf;
    private String email;

    // Status (enum)
    private StatusFuncionario status;

    private LocalDate dataAdmissao;

    private LocalDate dataDesligamento;

    // DTO simplificado do Setor (CORREÇÃO: Campo renomeado de 'idSetor' para 'setor')
    private SetorDTO setor;

    // Novo DTO simplificado do Cargo (CORREÇÃO: Campo renomeado de 'idCargo' para 'cargo')
    private CargoDTO cargo;
}