package com.agi.hack.dto;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import jakarta.persistence.ManyToOne;

public class EquipamentoResponseDTO {

    private Long idEquipamento;

    private StatusEquipamento status;

    private CategoriaEquipamento categoriaEquipamento;

    private Setor setor;
}
