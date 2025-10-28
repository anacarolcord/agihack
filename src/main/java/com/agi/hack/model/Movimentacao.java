package com.agi.hack.model;
import com.agi.hack.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_movimentacao")
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacao tipo;

    @Column(name = "data_hora_movimentacao", nullable = false)
    private LocalDateTime dataHoraMovimentacao;

    @Column(length = 1000)
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;


    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "requisicao_equipamento_id")
    private RequisicaoEquipamento requisicaoEquipamento;

    @ManyToOne
    @JoinColumn(name = "manutencao_id")
    private Manutencao manutencao;
}