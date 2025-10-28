package com.agi.hack.dto.EquipamentoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoRequestDTO {
    private String nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private Long numeroSerie;
}
