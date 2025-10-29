package com.agi.hack.dto.MovimentacaoDTO;

import com.agi.hack.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovimentacaoRequestDTO {

    @NotNull(message = "O ID do Equipamento é obrigatório!")
    private Long idEquipamento;

    private Long idFuncionario;

    @NotNull(message = "O Tipo de Movimentação é obrigatório!")
    private TipoMovimentacao tipo;

    private String observacoes;

    private Long idPedido;

    private Long idManutencao;


    public MovimentacaoRequestDTO(Long idEquipamento, Long idFuncionario, TipoMovimentacao tipo, String observacoes, Long idRequisicao, Long idManutencao) {
        this.idEquipamento = idEquipamento;
        this.idFuncionario = idFuncionario;
        this.tipo = tipo;
        this.observacoes = observacoes;
        this.idPedido = idRequisicao;
        this.idManutencao = idManutencao;
    }

}