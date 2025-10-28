package com.agi.hack.model;


import com.agi.hack.enums.StatusManutencao;
import jakarta.persistence.*;
import lombok.*;

import com.agi.hack.enums.EquipmentList;

import java.time.LocalDate;


@Entity
@EqualsAndHashCode(of = {"idOrdemServico"})
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {

    //id, serialNumber, nome do equipamento, status, data de entrada, data prevista de saida, data saida

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordem_servico")
    private Long idOrdemServico;

    @Column(name = "numero_serial", nullable = false, unique = true, length = 50)
    private String serialNumber;

   @Enumerated(EnumType.STRING)
    @Column(name = "tipo_equipamento", nullable = false)
    private EquipmentList tipoEquipamento;

   @Enumerated(EnumType.STRING)
    @Column(name = "status_manutencao", nullable = false)
    private StatusManutencao statusManutencao;

   @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

   @Column(name = "data_prevista", nullable = false)
    private LocalDate dataPrevista;

   @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;

   @ManyToOne
    @JoinColumn(name = "id_equipamento", nullable = false)
    private Equipamento equipamento;

}
