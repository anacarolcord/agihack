package com.agi.hack.dto.EquipamentoDTO;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.model.Funcionario;
import com.agi.hack.model.Manutencao;
import com.agi.hack.model.Pedido;
import com.agi.hack.model.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoRequestDTO {

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private String numeroSerie;

    private StatusEquipamento status;

    private CategoriaEquipamento categoriaEquipamento;

    private Manutencao manutencao;

    private Setor setor;

    private Funcionario funcionario;

    private Long catalogoId;
}
