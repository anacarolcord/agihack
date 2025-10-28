package com.agi.hack.dto;
import com.agi.hack.enums.TipoMovimentacao;
import com.agi.hack.model.Movimentacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoResponseDTO {
    private Long id;
    private TipoMovimentacao tipo;
    private LocalDateTime dataHoraMovimentacao;
    private String observacoes;


    private Long equipamentoId;
    private String nomeEquipamento;


    private Long funcionarioId;
    private String nomeFuncionario;


    private Long requisicaoId;


    private Long manutencaoId;


    public MovimentacaoResponseDTO(Movimentacao movimentacao){
        this.id = movimentacao.getId();
        this.tipo = movimentacao.getTipo();
        this.dataHoraMovimentacao = movimentacao.getDataHoraMovimentacao();
        this.observacoes = movimentacao.getObservacoes();


        if (movimentacao.getEquipamento() != null) {
            this.equipamentoId = movimentacao.getEquipamento().getId();
            this.nomeEquipamento = movimentacao.getEquipamento().getNome();
        }


        if (movimentacao.getFuncionario() != null) {
            this.funcionarioId = movimentacao.getFuncionario().getId();
            this.nomeFuncionario = movimentacao.getFuncionario().getNome();
        }


        if (movimentacao.getRequisicao() != null) {
            this.requisicaoId = movimentacao.getRequisicao().getId();
        }


        if (movimentacao.getManutencao() != null) {
            this.manutencaoId = movimentacao.getManutencao().getId();
        }
    }
}