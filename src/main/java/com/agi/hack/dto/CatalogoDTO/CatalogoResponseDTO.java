package com.agi.hack.dto.CatalogoDTO;

import com.agi.hack.model.Catalogo;

public record CatalogoResponseDTO(
        Long id,
        String sku,
        String descricao,
        String categoria,
        String fabricante,
        Integer estoqueMinimo
) {
    public CatalogoResponseDTO(Catalogo catalogo){
        this(
                catalogo.getId(),
                catalogo.getSku(),
                catalogo.getDescricao(),
                catalogo.getCategoria(),
                catalogo.getFabricante(),
                catalogo.getEstoqueMinimo()
        );
    }
}
