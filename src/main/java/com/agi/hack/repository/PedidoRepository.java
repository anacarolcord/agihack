package com.agi.hack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agi.hack.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
