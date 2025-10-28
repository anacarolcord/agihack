package com.agi.hack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    
    
}
