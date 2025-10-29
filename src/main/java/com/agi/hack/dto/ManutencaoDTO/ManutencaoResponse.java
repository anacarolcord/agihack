package com.agi.hack.dto.ManutencaoDTO;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoResponse {

    private Long idOrdemServico;
    private String serialNumber;
    private Long idEquipamento;
    private ListaEquipamento listaEquipamento;
    private StatusManutencao statusManutencao;
    private LocalDate dataEntrada;
    private LocalDate dataInicio;
    private LocalDate dataPrevista;
    private LocalDate dataEntrega;
    private Long usuarioId;
    private String nomeUsuario;

}
