package com.agi.hack.controller;

import com.agi.hack.dto.ManutencaoDTO.ManutencaoRequest;
import com.agi.hack.dto.ManutencaoDTO.ManutencaoResponse;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Manutencao;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.ManutencaoRepository;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manutencao")
@RequiredArgsConstructor
public class ManutencaoController {

    private final ManutencaoRepository manutencaoRepository;

    //Listar todas as manutenção que não estão com status de cancelada
    @GetMapping
    public List<ManutencaoResponse> listarTodasAtivas() {
        return manutencaoService.listarAtivas().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> buscarPorIDAtivas(@PathVariable Long id) {
        return manutencaoService.buscarPorIdAtiva(id)
                .map(m -> ResponseEntity.ok(toResponseDTO(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    //Busca GET por Status
    @GetMapping("/status/{status")
    public List<ManutencaoResponse> buscarPorStatus(@PathVariable StatusManutencao statusManutencao) {
        return manutencaoService.buscarPorStatusManutencao(statusManutencao).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca GET por intevalos de datas de entrada
    @GetMapping("/entrata")
    public List<ManutencaoResponse> buscarPorData(@RequestParam("inicio")LocalDate inicio, @RequestParam("fim") LocalDate fim) {
        return manutencaoService.buscarPorDataEntradaAtivas(inicio, fim).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    }

    // Criar POST uma nova manutenção
    @PostMapping
    public ResponseEntity<ManutencaoResponse> criar (@Valid @RequestBody ManutencaoRequest request) {
        Manutencao manutencao = manutencaoService.criarManutencao(request);
        return ResponseEntity.status(201).body(toResponseDTO(manutencao));
    }

    //Atualização PUT da manutencao
    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ManutencaoRequest request) {
        Manutencao manutencaoAtualizada = manutencaoService.atualizarManutencao(id, request);
        return ResponseEntity.ok(toResponseDTO(manutencaoAtualizada));
    }

    //Cancelar Manutenção (SOFT DELETE)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ManutencaoResponse> cancelar (@PathVariable Long id) {
        Manutencao manutencaoCancelada = manutencaoService.cancelarManutencao(id);
        return ResponseEntity.ok(toResponseDTO(manutencaoCancelada));
    }

    //Metodo Auxiliar: Entidade -> DTO

    private ManutencaoResponse toResponseDTO(Manutencao m){
        Long usuarioId = m.getUsuario() !=null ? m.getUsuario().getIdUsuario() : null;
        String nomeUsuario = m.getUsuario() !=null ? m.getUsuario().getNome() : null;

        return new ManutencaoResponse(
                m.getIdOrdemServico(),
                m.getEquipamento().getIdEquipamento(),
                m.getTipoEquipamento(),
                m.getStatusManutencao(),
                m.getDataEntrada(),
                m.getDataInicio(),
                m.getDataPrevista(),
                m.getDataEntrega(),
                usuarioId,
                nomeUsuario
        );
    }

}
