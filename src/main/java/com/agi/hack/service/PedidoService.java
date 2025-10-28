package com.agi.hack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agi.hack.dto.PedidoRequestDTO;
import com.agi.hack.dto.PedidoResponseDTO;
import com.agi.hack.enums.StatusPedido;
import com.agi.hack.exception.BadRequestException;
import com.agi.hack.mapper.PedidoMapper;
import com.agi.hack.model.Pedido;
import com.agi.hack.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    public PedidoResponseDTO gerarPedido(PedidoRequestDTO dto) {
        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setStatus(StatusPedido.PENDENTE);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }

    public PedidoResponseDTO buscarPedidoPorID(Long id) {
        Pedido pedido = buscarPorId(id);
        return pedidoMapper.toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toResponseDTOList(pedidos);
    }

    public PedidoResponseDTO atualizarPedidoAprovado(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(StatusPedido.APROVADO);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    public PedidoResponseDTO atualizarPedidoCancelado(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(StatusPedido.CANCELADO);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    public PedidoResponseDTO atualizarPedidoConcluido(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(StatusPedido.CONCLUIDO);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    private Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Pedido não encontrado com o ID: " + id));
    }   
}
