package com.agi.hack.controller;

import com.agi.hack.dto.CatalogoDTO.CatalogoRequestDTO;
import com.agi.hack.dto.CatalogoDTO.CatalogoResponseDTO;
import com.agi.hack.repository.CatalogoRepository;
import com.agi.hack.service.CatalogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @PostMapping
    public ResponseEntity<CatalogoResponseDTO> criarItem(
            @Valid @RequestBody CatalogoRequestDTO dto){

        CatalogoResponseDTO novoItem = catalogoService.criarItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @GetMapping
    public ResponseEntity<List<CatalogoResponseDTO>> listarTodos(){
        return ResponseEntity.ok(catalogoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(catalogoService.buscarPorId(id));
    }

    @PostMapping("/debug/verificar-reestoque")
    public ResponseEntity<String> debugReestoque() {
        System.out.println("!!! FORÇANDO EXECUÇÃO DA REESTOCAGEM AUTOMÁTICA !!!");
        catalogoService.verificarReestocagemAutomatica();
        return ResponseEntity.ok("Verificação de reestoque executada.");
    }
}
