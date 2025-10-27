package com.agi.hack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agi.hack.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
