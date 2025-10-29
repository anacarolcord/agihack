package com.agi.hack.dto.EquipamentoDTO;

import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.dto.PedidoDTO.PedidoResponseDTO;
import com.agi.hack.dto.SetorDTO.SetorResponseDTO;
import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.model.Manutencao;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class EquipamentoResponseDTO {

    private Long idEquipamento;

    private String nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private String numeroSerie;

    private StatusEquipamento status;

    private CategoriaEquipamento categoriaEquipamento;

    private Manutencao manutencao;

    private SetorResponseDTO setor;

    private PedidoResponseDTO pedido;

    private FuncionarioResponseDTO funcionario;

    public void setNome(ListaEquipamento listaEquipamento, ListaEquipamento listaEquipamento1, ListaEquipamento listaEquipamento2, ListaEquipamento listaEquipamento3) {
    }

    public void setNome(ListaEquipamento listaEquipamento, ListaEquipamento listaEquipamento1, ListaEquipamento listaEquipamento2, ListaEquipamento listaEquipamento3, ListaEquipamento listaEquipamento4, ListaEquipamento listaEquipamento5) {
    }
}
