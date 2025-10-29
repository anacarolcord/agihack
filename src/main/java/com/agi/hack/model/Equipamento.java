package com.agi.hack.model;

import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.ListaEquipamento;
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

    @Column(nullable = false)
    private String nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private String numeroSerie;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status;

    @Enumerated(EnumType.STRING)
    private ListaEquipamento tipoEquipamento;

    @Enumerated(EnumType.STRING)
    private CategoriaEquipamento categoriaEquipamento;

    @OneToMany(mappedBy = "idOrdemServico")
    private List<Manutencao> manutencao;

    @ManyToOne
    @JoinColumn(name = "idSetor")
    private Setor setor;

    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;


    public void setNome(ListaEquipamento listaEquipamento, ListaEquipamento listaEquipamento1, ListaEquipamento listaEquipamento2) {
    }

    public void setNome(ListaEquipamento listaEquipamento, ListaEquipamento listaEquipamento1, ListaEquipamento listaEquipamento2, ListaEquipamento listaEquipamento3, ListaEquipamento listaEquipamento4, ListaEquipamento listaEquipamento5) {

    }

    public void setNome(ListaEquipamento listaEquipamento, ListaEquipamento listaEquipamento1, ListaEquipamento listaEquipamento2, ListaEquipamento listaEquipamento3) {

    }
}
