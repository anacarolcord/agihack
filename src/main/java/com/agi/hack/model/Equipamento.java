package com.agi.hack.model;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.enums.equipmentList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipamento {

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipamento;

    @Column(nullable = false)
    private String nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private Long numeroSerie;

    private StatusEquipamento status;

    private CategoriaEquipamento categoriaEquipamento;

    @ManyToOne(mappedBy= idSetor)
    private Setor setor;

}
