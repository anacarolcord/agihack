package com.agi.hack.controller;

import com.agi.hack.dto.EquipamentoDTO.EquipamentoRequestDTO;
import com.agi.hack.dto.EquipamentoDTO.EquipamentoResponseDTO;
import com.agi.hack.service.EquipamentoService;
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

}
