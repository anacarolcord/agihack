package com.agi.hack.dto.ManutencaoDTO;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManutencaoRequest {

    private Long equipamentoId;
    private String serialNumber;
    private ListaEquipamento listaEquipamento;
    private StatusManutencao statusManutencao;
    private Long idUsuario;

}
