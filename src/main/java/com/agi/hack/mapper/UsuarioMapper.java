package com.agi.hack.mapper;

import com.agi.hack.dto.RegisterResponseDTO;
import com.agi.hack.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UsuarioMapper {

    RegisterResponseDTO toRegisterResponseDto(Usuario usuario);
}
