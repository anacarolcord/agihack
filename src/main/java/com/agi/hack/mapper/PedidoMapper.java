package com.agi.hack.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // Import necessário

import com.agi.hack.dto.PedidoDTO.PedidoResponseDTO;
import com.agi.hack.dto.PedidoDTO.PedidoRequestDTO;
import com.agi.hack.model.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    // RequestDTO → Entidade
    // CORREÇÃO: Adicionando @Mapping para ignorar o ID (que é gerado no banco)
    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "itemSolicitado", ignore = true)
    Pedido toEntity(PedidoRequestDTO dto);

    // Entidade → ResponseDTO
    PedidoResponseDTO toResponseDTO(Pedido order);

    // Lista de entidades → Lista de ResponseDTO
    List<PedidoResponseDTO> toResponseDTOList(List<Pedido> order);
}