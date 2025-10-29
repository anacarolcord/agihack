package com.agi.hack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agi.hack.dto.PedidoDTO.PedidoRequestDTO;
import com.agi.hack.dto.PedidoDTO.PedidoResponseDTO;
import com.agi.hack.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> gerarPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.gerarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.status(HttpStatus.OK).body(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @PutMapping("/aprovar/{id}")
    public ResponseEntity<PedidoResponseDTO> aprovarPedido(@PathVariable Long id) {
        PedidoResponseDTO pedidoAtualizado = pedidoService.aprovarPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoAtualizado);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable Long id) {
        PedidoResponseDTO pedidoAtualizado = pedidoService.cancelarPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoAtualizado);
    }

    @PutMapping("/concluir/{id}")
    public ResponseEntity<PedidoResponseDTO> concluirPedido(@PathVariable Long id) {
        PedidoResponseDTO pedidoAtualizado = pedidoService.concluirPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoAtualizado);
    }

}
