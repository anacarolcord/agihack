package com.agi.hack.dto.EquipamentoDTO;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.ClassificacaoEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.model.Manutencao;
import com.agi.hack.model.Pedido;
import com.agi.hack.model.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoRequestDTO {
    private String nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private Long numeroSerie;

    private StatusEquipamento status;

    private ClassificacaoEquipamento classificacaoEquipamento;

    private CategoriaEquipamento categoriaEquipamento;

    private List<Manutencao> manutencao;

    private Setor setor;

    private Pedido pedido;
}
