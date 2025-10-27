package com.agi.hack.model;

import java.time.LocalDateTime;

import com.agi.hack.enums.EquipmentList;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private LocalDateTime expectedDate;

    @Enumerated(EnumType.STRING)
    private EquipmentList Type;

    public Order() {
    }

    public Order(Long id, LocalDateTime orderDate, LocalDateTime expectedDate, EquipmentList type) {
        this.id = id;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        Type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDateTime expectedDate) {
        this.expectedDate = expectedDate;
    }

    public EquipmentList getType() {
        return Type;
    }

    public void setType(EquipmentList type) {
        Type = type;
    }

}
