package com.agi.hack.dto.FuncionarioDTO;

import com.agi.hack.enums.StatusFuncionario; // Import está correto!
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos (somente números).")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    // ID de relacionamento para Setor
    @NotNull(message = "O ID do setor é obrigatório.")
    private Long idSetor;

    // ID de relacionamento para Cargo
    @NotNull(message = "O ID do cargo é obrigatório.")
    private Long idCargo;

    // Status: Opcional na requisição, usa o nome do Enum CORRETO
    private StatusFuncionario status; // CORRIGIDO: Era FuncionarioStatus, agora é StatusFuncionario

    private LocalDate dataAdmissao;

    private LocalDate dataDesligamento;
}