package com.agi.hack.mapper;

import com.agi.hack.dto.ManutencaoDTO.ManutencaoRequest;
import com.agi.hack.dto.ManutencaoDTO.ManutencaoResponse;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Manutencao;

import java.time.LocalDate;

public class ManutencaoMapper {

    // Converter DTO de requisição -> Entity
    public static Manutencao toEntity(ManutencaoRequest dto, Equipamento equipamento, Long idUsuario) {
        Manutencao manutencao = new Manutencao();
        manutencao.setEquipamento(equipamento);
        manutencao.setSerialNumber(dto.getSerialNumber() != null ? dto.getSerialNumber() : equipamento.getNumeroSerie());
        manutencao.setTipoEquipamento(dto.getListaEquipamento() != null ? dto.getListaEquipamento() : equipamento.getTipoEquipamento());
        manutencao.setStatusManutencao(dto.getStatusManutencao() != null ? dto.getStatusManutencao() : null);
        manutencao.setDataEntrada(LocalDate.now());

        // Definir data prevista de forma padrão (pode substituir por lógica de dias conforme tipo)
        manutencao.setDataPrevista(LocalDate.now().plusDays(7));

        // Usuário será setado no Service
        return manutencao;
    }

    // Converter Entity -> DTO de resposta
    public static ManutencaoResponse toResponseDTO(Manutencao manutencao) {
        Long usuarioId = manutencao.getUsuario() != null ? manutencao.getUsuario().getId() : null;
        String nomeUsuario = manutencao.getUsuario() != null ? manutencao.getUsuario().getUsername() : null;

        return new ManutencaoResponse(
                manutencao.getIdOrdemServico(),
                manutencao.getSerialNumber(),
                manutencao.getEquipamento() != null ? manutencao.getEquipamento().getIdEquipamento() : null,
                manutencao.getTipoEquipamento(),
                manutencao.getStatusManutencao(),
                manutencao.getDataEntrada(),
                manutencao.getDataPrevista(),
                manutencao.getDataEntrega(),
                usuarioId,
                nomeUsuario
        );
    }
}
