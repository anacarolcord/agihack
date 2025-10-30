package com.agi.hack.dto.PedidoDTO;

import java.time.LocalDateTime;

import com.agi.hack.enums.StatusPedido;
import com.agi.hack.model.Equipamento;

public class PedidoResponseDTO {

    private Long idPedido;
    private LocalDateTime dataPedido;
    private LocalDateTime dataPrevisao;
    private String status;
    private String catalogoDescricao; // <-- ADICIONADO (Ex: "Notebook Dell")
    private Integer quantidade;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Long idPedido, LocalDateTime dataPedido, LocalDateTime dataPrevisao,
                             String status, String catalogoDescricao, Integer quantidade) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.dataPrevisao = dataPrevisao;
        this.status = status;
        this.catalogoDescricao = catalogoDescricao;
        this.quantidade = quantidade;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
