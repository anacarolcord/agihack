package com.agi.hack.dto.CatalogoDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CatalogoRequestDTO(
        @NotBlank @Size(min = 3, max = 50)
        String sku,

        @NotBlank @Size(min = 5, max = 255)
        String descrição,

        @NotBlank
        String categoria,

        String fabricante,

        @NotNull @Positive
        Integer estoqueMinimo
) {
}
