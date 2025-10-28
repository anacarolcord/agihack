package com.agi.hack.dto.FuncionarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FuncionarioRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    // Recebe apenas o ID do Setor, não o objeto completo.
    @NotNull(message = "O ID do setor é obrigatório.")
    private Long idSetor;
}