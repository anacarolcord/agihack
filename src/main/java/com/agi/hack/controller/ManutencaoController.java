package com.agi.hack.controller;

import com.agi.hack.dto.ManutencaoDTO.ManutencaoRequest;
import com.agi.hack.dto.ManutencaoDTO.ManutencaoResponse;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Manutencao;
import com.agi.hack.service.ManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manutencao")
@RequiredArgsConstructor
public class ManutencaoController {

    private final ManutencaoService manutencaoService;

    // Listar todas as manutenções ativas
    @GetMapping
    public List<ManutencaoResponse> listarTodasAtivas() {
        return manutencaoService.listarAtivos().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> buscarPorID(@PathVariable Long id) {
        return manutencaoService.buscarPorIdAtivo(id)
                .map(m -> ResponseEntity.ok(toResponseDTO(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca GET por Status
    @GetMapping("/status/{status}")
    public List<ManutencaoResponse> buscarPorStatus(@PathVariable StatusManutencao status) {
        return manutencaoService.buscarPorStatusAtivo(status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca GET por intervalos de datas de entrada
    @GetMapping("/entrada")
    public List<ManutencaoResponse> buscarPorData(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return manutencaoService.buscarPorDataEntrada(inicio, fim).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Criar POST uma nova manutenção
    @PostMapping
    public ResponseEntity<ManutencaoResponse> criar(@Valid @RequestBody ManutencaoRequest request) {
        Manutencao manutencao = manutencaoService.criarManutencao(request);
        return ResponseEntity.status(201).body(toResponseDTO(manutencao));
    }

    // Atualização PUT da manutenção
    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ManutencaoRequest request) {
        Manutencao manutencaoAtualizada = manutencaoService.atualizarManutencao(id, request);
        return ResponseEntity.ok(toResponseDTO(manutencaoAtualizada));
    }

    // Cancelar manutenção (SOFT DELETE)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ManutencaoResponse> cancelar(@PathVariable Long id) {
        Manutencao manutencaoCancelada = manutencaoService.cancelarManutencao(id);
        return ResponseEntity.ok(toResponseDTO(manutencaoCancelada));
    }

    // Entregar equipamento (marcar como concluída)
    @PutMapping("/{id}/entregar")
    public ResponseEntity<ManutencaoResponse> entregar(@PathVariable Long id) {
        Manutencao manutencaoEntregue = manutencaoService.entregarEquipamento(id);
        return ResponseEntity.ok(toResponseDTO(manutencaoEntregue));
    }

    // Metodo Auxiliar: Entidade -> DTO
    private ManutencaoResponse toResponseDTO(Manutencao m) {
        Long usuarioId = m.getUsuario() != null ? m.getUsuario().getId() : null;
        String nomeUsuario = m.getUsuario() != null ? m.getUsuario().getUsername() : null;

        return new ManutencaoResponse(
                m.getIdOrdemServico(),
                m.getSerialNumber(),
                m.getEquipamento().getIdEquipamento(),
                m.getTipoEquipamento(),
                m.getStatusManutencao(),
                m.getDataEntrada(),
                m.getDataPrevista(),
                m.getDataEntrega(),
                usuarioId,
                nomeUsuario
        );
    }

}