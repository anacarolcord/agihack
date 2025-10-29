package com.agi.hack.model;

import java.time.LocalDateTime;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// IMPORT CORRIGIDO
import com.agi.hack.model.Equipamento;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private LocalDateTime dataPedido;

    private LocalDateTime dataPrevisao;

    @Enumerated(EnumType.STRING)
    private ListaEquipamento tipo;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    // MAPEAMENTO CORRIGIDO: Deve ser "pedido" para corresponder ao campo em Equipamento.java
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonBackReference
    private Equipamento equipamento;

    public Pedido() {
    }

    public Pedido(Long idPedido, LocalDateTime dataPedido, LocalDateTime dataPrevisao, ListaEquipamento tipo,
                  StatusPedido status, Equipamento equipamento) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.dataPrevisao = dataPrevisao;
        this.tipo = tipo;
        this.status = status;
        this.equipamento = equipamento;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(LocalDateTime dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }

    public ListaEquipamento getTipo() {
        return tipo;
    }

    public void setTipo(ListaEquipamento tipo) {
        this.tipo = tipo;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

}