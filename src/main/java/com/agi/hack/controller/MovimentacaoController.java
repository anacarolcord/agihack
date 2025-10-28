package com.agi.hack.controller;

import com.agi.hack.dto.MovimentacaoDTO.MovimentacaoRequestDTO;
import com.agi.hack.dto.MovimentacaoDTO.MovimentacaoResponseDTO;
import com.agi.hack.model.Movimentacao;
import com.agi.hack.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/movimentacoes")

public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @PostMapping

    public ResponseEntity<MovimentacaoResponseDTO> criarMovimentacao(@RequestBody @Valid MovimentacaoRequestDTO dto){
        Movimentacao movimentacaoSalva = service.criarMovimentacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MovimentacaoResponseDTO(movimentacaoSalva));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> buscarPorId(@PathVariable Long id){
        Movimentacao movimentacao = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada com ID: " + id)); // Deixando o RuntimeException por enquanto
        return ResponseEntity.ok().body(new MovimentacaoResponseDTO(movimentacao));
    }

    @GetMapping
    // CORREÇÃO 7: Deve retornar List<MovimentacaoResponseDTO>
    public ResponseEntity<List<MovimentacaoResponseDTO>> buscarTodas(){
        List<Movimentacao> movimentacoes = service.buscarTodas();
        // CORREÇÃO 8: Mapear a lista de entidades para lista de DTOs
        List<MovimentacaoResponseDTO> responseList = movimentacoes.stream()
                .map(MovimentacaoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping(value = "/equipamento/{equipamentoId}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> buscarPorEquipamento(@PathVariable Long equipamentoId){
        List<Movimentacao> movimentacoes = service.buscarPorEquipamento(equipamentoId);
        List<MovimentacaoResponseDTO> responseList = movimentacoes.stream()
                .map(MovimentacaoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping(value = "/funcionario/{funcionarioId}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> buscarPorFuncionario(@PathVariable Long funcionarioId){ // nome da variável correto
        List<Movimentacao> movimentacoes = service.buscarPorFuncionario(funcionarioId); // Passa funcionarioId
        List<MovimentacaoResponseDTO> responseList = movimentacoes.stream()
                .map(MovimentacaoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }
}