package com.agi.hack.controller;

import com.agi.hack.dto.EquipamentoDTO.EquipamentoRequestDTO;
import com.agi.hack.dto.EquipamentoDTO.EquipamentoResponseDTO;
import com.agi.hack.service.EquipamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/equipamentos")

public class EquipamentoController {
    private final EquipamentoService service;

    public EquipamentoController (EquipamentoService service){
        this.service=service;
    }

    @GetMapping
    public List<EquipamentoResponseDTO> listarEquipamentos(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public EquipamentoResponseDTO buscarEquipamentoPorId(@PathVariable Long id){
        return service.buscarId(id);
    }

    @PostMapping
    public EquipamentoResponseDTO salvar(@RequestBody EquipamentoRequestDTO dto){
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public EquipamentoResponseDTO atualizarEquipamento(@PathVariable Long id, @RequestBody EquipamentoRequestDTO dados){
        return service.atualizar(id,dados);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        service.deletar(id);
    }

        @PostMapping("/{equipamentoId}/alocar")
        public ResponseEntity<EquipamentoResponseDTO> alocarEquipamento(
                @PathVariable Long equipamentoId,
                @RequestParam Long funcionarioId) {

            EquipamentoResponseDTO dto = service.alocarEquipamento(equipamentoId, funcionarioId);
            return ResponseEntity.ok(dto);
        }

        @PostMapping("/{equipamentoId}/devolver")
        public ResponseEntity<EquipamentoResponseDTO> devolverEquipamento(
                @PathVariable Long equipamentoId) {

            EquipamentoResponseDTO dto = service.devolverEquipamento(equipamentoId);
            return ResponseEntity.ok(dto);
        }


        @PostMapping("/{equipamentoId}/manutencao")
        public ResponseEntity<EquipamentoResponseDTO> enviarParaManutencao(
                @PathVariable Long equipamentoId) {

            EquipamentoResponseDTO dto = service.enviarParaManutencao(equipamentoId);
            return ResponseEntity.ok(dto);
        }


        @PostMapping("/alocar-kit")
        public ResponseEntity<Void> alocarKitPadrao(
                @RequestParam Long funcionarioId) {

            service.atribuirKitParaFuncionario(funcionarioId);
            return ResponseEntity.noContent().build();
        }
    }


