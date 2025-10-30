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

    // --------------------------
    // LISTAGENS E BUSCAS
    // --------------------------

    // Lista todas as manutenções ativas (não canceladas)
    @GetMapping
    public List<ManutencaoResponse> listarTodasAtivas() {
        return manutencaoService.listarAtivos().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca manutenção por ID
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> buscarPorID(@PathVariable Long id) {
        return manutencaoService.buscarPorIdAtivo(id)
                .map(m -> ResponseEntity.ok(toResponseDTO(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca por status (PENDENTE, EM_REPARO, CONCLUIDA, ATRASADA, FALHA etc)
    @GetMapping("/status/{status}")
    public List<ManutencaoResponse> buscarPorStatus(@PathVariable StatusManutencao status) {
        return manutencaoService.buscarPorStatusAtivo(status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca manutenções por intervalo de datas de entrada
    @GetMapping("/entrada")
    public List<ManutencaoResponse> buscarPorData(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return manutencaoService.buscarPorDataEntrada(inicio, fim).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --------------------------
    // CRUD PRINCIPAL
    // --------------------------

    // Cria nova manutenção
    @PostMapping
    public ResponseEntity<ManutencaoResponse> criar(@Valid @RequestBody ManutencaoRequest request) {
        Manutencao manutencao = manutencaoService.criarManutencao(request);
        return ResponseEntity.status(201).body(toResponseDTO(manutencao));
    }

    // Atualiza manutenção existente
    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ManutencaoRequest request) {
        Manutencao manutencaoAtualizada = manutencaoService.atualizarManutencao(id, request);
        return ResponseEntity.ok(toResponseDTO(manutencaoAtualizada));
    }

    // Cancela uma manutenção (soft delete)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ManutencaoResponse> cancelar(@PathVariable Long id) {
        Manutencao manutencaoCancelada = manutencaoService.cancelarManutencao(id);
        return ResponseEntity.ok(toResponseDTO(manutencaoCancelada));
    }

    // Marca o equipamento como entregue (e atualiza status automaticamente)
    @PutMapping("/{id}/entregar")
    public ResponseEntity<ManutencaoResponse> entregar(@PathVariable Long id) {
        Manutencao manutencaoEntregue = manutencaoService.entregarEquipamento(id);
        return ResponseEntity.ok(toResponseDTO(manutencaoEntregue));
    }

    // --------------------------
    // NOVO: EXTENDER PRAZO DE ENTREGA
    // --------------------------

    /**
     * Permite que o técnico estenda o prazo de entrega ANTES da data prevista.
     * Exemplo de chamada:
     * PUT /manutencao/5/estender?dias=2
     */
    @PutMapping("/{id}/estender")
    public ResponseEntity<ManutencaoResponse> estenderPrazo(
            @PathVariable Long id,
            @RequestParam("dias") int dias) {
        Manutencao manutencaoAtualizada = manutencaoService.estenderPrazoEntrega(id, dias);
        return ResponseEntity.ok(toResponseDTO(manutencaoAtualizada));
    }

    // --------------------------
    // METODO AUXILIAR: CONVERSÃO PARA DTO
    // --------------------------

    private ManutencaoResponse toResponseDTO(Manutencao m) {
        Long usuarioId = m.getUsuario() != null ? m.getUsuario().getId() : null;
        String nomeUsuario = m.getUsuario() != null ? m.getUsuario().getUsername() : null;

        return new ManutencaoResponse(
                m.getIdOrdemServico(),
                m.getSerialNumber(),
                m.getEquipamento() != null ? m.getEquipamento().getIdEquipamento() : null,
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
