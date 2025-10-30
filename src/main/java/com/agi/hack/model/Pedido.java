package com.agi.hack.model;

import java.time.LocalDateTime;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusPedido;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

// IMPORT CORRIGIDO


@Entity
@Table(name = "pedidos")
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private LocalDateTime dataPedido;

    private LocalDateTime dataPrevisao;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;


    public Pedido(Catalogo itemSolicitado) {
        this.itemSolicitado = itemSolicitado;
    }

    public Pedido(Long idPedido, LocalDateTime dataPedido, LocalDateTime dataPrevisao,
                  StatusPedido status, Catalogo itemSolicitado, Integer quantidade) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.dataPrevisao = dataPrevisao;
        this.status = status;
        this.itemSolicitado = itemSolicitado;
        this.quantidade = quantidade;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCatalogo")
    private Catalogo itemSolicitado;

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

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }


    public Catalogo getItemSolicitado() {
        return itemSolicitado;
    }

    public void setItemSolicitado(Catalogo itemSolicitado) {
        this.itemSolicitado = itemSolicitado;
    }

    public Integer getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(Integer quantidade){
        this.quantidade = quantidade;
    }
}