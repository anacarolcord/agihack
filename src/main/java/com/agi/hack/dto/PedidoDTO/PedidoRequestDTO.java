package com.agi.hack.dto.PedidoDTO;

import java.time.LocalDateTime;

import com.agi.hack.enums.StatusPedido;
import com.agi.hack.model.Equipamento;

public class PedidoRequestDTO {

    // private Long idPedido;
    private LocalDateTime dataPedido;
    private LocalDateTime dataPrevisao;
    private String status;
    private Long catalogoId;
    private Integer quantidade;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(LocalDateTime dataPedido, LocalDateTime dataPrevisao, String status, Long catalogoId, Integer quantidade) {
        this.dataPedido = dataPedido;
        this.dataPrevisao = dataPrevisao;
        this.status = status;
        this.catalogoId = catalogoId;
        this.quantidade = quantidade;
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


    public Long getCatalogoId() {
        return catalogoId;
    }

    public void setCatalogoId(Long catalogoId) {
        this.catalogoId = catalogoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
