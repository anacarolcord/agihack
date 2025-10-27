package com.agi.hack.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.agi.hack.dto.OrderResponseDTO;
import com.agi.hack.dto.OrderRequestDTO;
import com.agi.hack.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // RequestDTO → Entidade
    Order toEntity(OrderRequestDTO dto);

    // Entidade → ResponseDTO
    OrderResponseDTO toResponseDTO(Order order);

    // Lista de entidades → Lista de ResponseDTO
    List<OrderResponseDTO> toResponseDTOList(List<Order> order);
}
