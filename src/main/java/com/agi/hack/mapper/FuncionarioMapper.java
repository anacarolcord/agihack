package com.agi.hack.mapper;

import com.agi.hack.dto.FuncionarioDTO.FuncionarioRequestDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.model.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Interface de mapeamento para a Entidade Funcionario usando MapStruct.
 * Mapeia DTOs para Entidade e vice-versa.
 */
@Mapper(componentModel = "spring",
        uses = {SetorMapper.class, CargoMapper.class}, // Depende de SetorMapper e CargoMapper
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FuncionarioMapper {

    // --- Mapeamento de Request DTO para Entidade ---
    // O Service lida com a busca de Setor e Cargo, então ignoramos o mapeamento direto.
    @Mapping(target = "setor", ignore = true)
    @Mapping(target = "cargo", ignore = true)
    @Mapping(target = "idFuncionario", ignore = true)
    Funcionario toEntity(FuncionarioRequestDTO dto);

    // --- Mapeamento de Entidade para Response DTO ---
    // O MapStruct usa SetorMapper e CargoMapper (definidos em 'uses') para mapear Setor/Cargo
    // para SetorResponseDTO/CargoDTO automaticamente.
    FuncionarioResponseDTO toResponseDTO(Funcionario funcionario);
}