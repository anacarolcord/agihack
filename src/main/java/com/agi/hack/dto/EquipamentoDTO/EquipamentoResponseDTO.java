package com.agi.hack.dto.EquipamentoDTO;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.model.Setor;

public class EquipamentoResponseDTO {

    private Long idEquipamento;

    private StatusEquipamento status;

    private CategoriaEquipamento categoriaEquipamento;

    private Setor setor;
}
