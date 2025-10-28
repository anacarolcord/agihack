package com.agi.hack.repository;

import com.agi.hack.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import com.agi.hack.model.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
}
