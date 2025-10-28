package com.agi.hack.model;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.ClassificacaoEquipamento;
import com.agi.hack.enums.StatusEquipamento;

import com.agi.hack.repository.ManutencaoRepository;
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

    @Column(nullable = false)
    private String nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private Long numeroSerie;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status;

    @Enumerated(EnumType.STRING)
    private ClassificacaoEquipamento classificacaoEquipamento;

    @Enumerated(EnumType.STRING)
    private CategoriaEquipamento categoriaEquipamento;

    @OneToMany(mappedBy = "idManutencao")
    private List<Manutencao> manutencao;

    @ManyToOne
    @JoinColumn(name = "idSetor")
    private Setor setor;

    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;



}
