package com.agi.hack.model;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.StatusEquipamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Equipamento {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipamento;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private String numeroSerie;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status;

    @Enumerated(EnumType.STRING)
    private CategoriaEquipamento categoriaEquipamento;

    @OneToMany(mappedBy = "idOrdemServico")
    private List<Manutencao> manutencao;

    @ManyToOne
    @JoinColumn(name = "idSetor")
    private Setor setor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCatalogo")
    private Catalogo catalogo;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;

}
