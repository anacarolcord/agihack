package com.agi.hack.mapper;

import com.agi.hack.dto.SetorDTO.SetorDTO; // Assumindo que este DTO existe
import com.agi.hack.model.Setor; // Assumindo que a Entidade existe
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SetorMapper {

    // Mapeia a Entidade Setor para o DTO de Resposta
    SetorDTO toDto(Setor setor);

    // Opcional: Mapeia o DTO para a Entidade, se necessário para criação/atualização
    Setor toEntity(SetorDTO setorDTO);
}