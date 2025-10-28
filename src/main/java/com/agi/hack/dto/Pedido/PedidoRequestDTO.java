package com.agi.hack.dto.Pedido;

import java.time.LocalDateTime;

import com.agi.hack.model.Equipamento;

public class PedidoRequestDTO {

    // private Long idPedido;
    private LocalDateTime dataPedido;
    private LocalDateTime dataPrevisao;
    private String tipo;
    private String status;
    private Equipamento equipamento;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(LocalDateTime dataPedido, LocalDateTime dataPrevisao, String tipo, String status,
            Equipamento equipamento) {
        this.dataPedido = dataPedido;
        this.dataPrevisao = dataPrevisao;
        this.tipo = tipo;
        this.status = status;
        this.equipamento = equipamento;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

}
