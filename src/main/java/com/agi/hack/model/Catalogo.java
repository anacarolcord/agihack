package com.agi.hack.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalogo_itens")
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku; // Código único do item.

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String categoria;

    private String fabricante;

    @Column(name = "estoque_minimo", nullable = false, columnDefinition = "INT DEFAULT 5")
    private Integer estoqueMinimo;

    @OneToMany(mappedBy = "catalogo", fetch = FetchType.LAZY)
    private List<Equipamento> equipamento;

    @OneToMany(mappedBy = "itemSolicitado", fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
}
