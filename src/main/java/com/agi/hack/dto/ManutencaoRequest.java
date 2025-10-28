package com.agi.hack.dto;

import com.agi.hack.enums.EquipmentList;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManutencaoRequest {

    private String serialNumber;
    private Long equipamentoId;
    private EquipmentList equipmentList;

}
