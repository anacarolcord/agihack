package com.agi.hack.dto;

import com.agi.hack.enums.TipoMovimentacao;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class MovimentacaoRequestDTO {
    @NotNull(message = "O ID do Equipamento é obrigatório!")
    private Long idEquipamento;

    @NotNull(message = "O ID do Funcionário é obrigatório!")
    private  Long idFuncionario;

    @NotNull(message = "O Tipo de Movimentação é obrigatório!")
    private TipoMovimentacao tipo;

    private String observacoes;

}
