package com.agi.hack.dto;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManutencaoRequest {

    private String serialNumber;
    private Long equipamentoId;
    private ListaEquipamento listaEquipamento;
    private StatusManutencao statusManutencao;
    private Long idFuncionario;

}
