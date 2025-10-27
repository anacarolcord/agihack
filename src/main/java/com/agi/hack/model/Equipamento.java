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
    @JsonIgnore
    private Setor setor;

    @OneToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "idPedido")
    @JsonManagedReference
    private Pedido pedido;

    public Equipamento() {
    }

    public Equipamento(Long idEquipamento, String nome, Double custoAquisicao, LocalDateTime dataAquisicao,
            Long numeroSerie, StatusEquipamento status, CategoriaEquipamento categoriaEquipamento, Setor setor,
            Pedido pedido) {
        this.idEquipamento = idEquipamento;
        this.nome = nome;
        this.custoAquisicao = custoAquisicao;
        this.dataAquisicao = dataAquisicao;
        this.numeroSerie = numeroSerie;
        this.status = status;
        this.categoriaEquipamento = categoriaEquipamento;
        this.setor = setor;
        this.pedido = pedido;
    }

    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCustoAquisicao() {
        return custoAquisicao;
    }

    public void setCustoAquisicao(Double custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    public LocalDateTime getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDateTime dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Long getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(Long numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public StatusEquipamento getStatus() {
        return status;
    }

    public void setStatus(StatusEquipamento status) {
        this.status = status;
    }

    public CategoriaEquipamento getCategoriaEquipamento() {
        return categoriaEquipamento;
    }

    public void setCategoriaEquipamento(CategoriaEquipamento categoriaEquipamento) {
        this.categoriaEquipamento = categoriaEquipamento;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
