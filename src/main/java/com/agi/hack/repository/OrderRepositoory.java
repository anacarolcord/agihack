package com.agi.hack.repository;

import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoory extends JpaRepository<Order, Long> {
}
