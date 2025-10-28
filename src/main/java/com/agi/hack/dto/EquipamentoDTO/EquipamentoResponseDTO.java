package com.agi.hack.dto.EquipamentoDTO;

import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.dto.ManutencaoResponse;
import com.agi.hack.dto.PedidoDTO.PedidoResponseDTO;
import com.agi.hack.dto.SetorDTO.SetorResponseDTO;
import com.agi.hack.enums.CategoriaEquipamento;
import com.agi.hack.enums.ClassificacaoEquipamento;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.model.Manutencao;
import com.agi.hack.model.Pedido;
import com.agi.hack.model.Setor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class EquipamentoResponseDTO {

    private Long idEquipamento;

    private ListaEquipamento nome;

    private Double custoAquisicao;

    private LocalDateTime dataAquisicao;

    private Long numeroSerie;

    private StatusEquipamento status;

    private ClassificacaoEquipamento classificacaoEquipamento;

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
