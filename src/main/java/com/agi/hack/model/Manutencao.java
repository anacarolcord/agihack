package com.agi.hack.model;


import com.agi.hack.enums.StatusManutencao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.agi.hack.enums.EquipmentList;

import java.time.LocalDate;


@Entity
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

    @Column(name = "id_equipamento", nullable = false)
    private Long perifericoId;

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


}
