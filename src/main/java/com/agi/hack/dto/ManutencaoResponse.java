package com.agi.hack.dto;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoResponse {

    private Long idOrdemServico;
    private Long idEquipamento;
    private String serialNumber;
    private ListaEquipamento listaEquipamento;
    private StatusManutencao statusManutencao;
    private LocalDate dataEntrada;
    private LocalDate dataInicio;
    private LocalDate dataPrevista;
    private LocalDate dataEntrega;

}
