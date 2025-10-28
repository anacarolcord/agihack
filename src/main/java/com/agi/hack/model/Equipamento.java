package com.agi.hack.model;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipamentos")
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

    @ManyToOne
    @JoinColumn(name = "id_setor", referencedColumnName = "idSetor")
    private Setor setor;

    @OneToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "idPedido")
    private Pedido pedido;



}
