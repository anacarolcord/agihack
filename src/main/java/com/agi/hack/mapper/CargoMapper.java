package com.agi.hack.mapper;

import com.agi.hack.dto.CargoDTO.CargoDTO; // Assumindo que este DTO existe
import com.agi.hack.model.Cargo; // Assumindo que a Entidade existe
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CargoMapper {

    // Mapeia a Entidade Cargo para o DTO de Resposta
    CargoDTO toDto(Cargo cargo);

    // Opcional: Mapeia o DTO para a Entidade, se necessário
    Cargo toEntity(CargoDTO cargoDTO);
}