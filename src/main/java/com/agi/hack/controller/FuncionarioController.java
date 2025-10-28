package com.agi.hack.Controller;

import com.agi.hack.dto.FuncionarioDTO.FuncionarioRequestDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criarFuncionario(@Valid @RequestBody FuncionarioRequestDTO dto) {
        FuncionarioResponseDTO novoFuncionario = funcionarioService.criarFuncionario(dto);
        return new ResponseEntity<>(novoFuncionario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarFuncionarios() {
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> getFuncionarioById(@PathVariable long id){
        FuncionarioResponseDTO dto = funcionarioService.buscarPorId(id);
        return new ResponseEntity<FuncionarioResponseDTO>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioRequestDTO dto){
        FuncionarioResponseDTO funcionarioResponseDTO = funcionarioService.atualizarFuncionario(id,dto);
        return new ResponseEntity<FuncionarioResponseDTO>(funcionarioResponseDTO, HttpStatus.OK);
    }
    @PatchMapping("desativar/{id}")
    public ResponseEntity<FuncionarioResponseDTO> desativarFuncionario(@PathVariable long id){
        FuncionarioResponseDTO funcionarioResponseDTO = funcionarioService.desativarFuncionario(id);
        return new ResponseEntity<>(funcionarioResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> mudarOcupacao(@RequestBody long idCargo, @RequestBody long idSetor, @PathVariable long employeeId){

        FuncionarioResponseDTO funcionarioResponseDTO = funcionarioService.mudarOcupacao(employeeId,idCargo, idSetor);
        return new ResponseEntity<>(funcionarioResponseDTO, HttpStatus.OK);

    }
}