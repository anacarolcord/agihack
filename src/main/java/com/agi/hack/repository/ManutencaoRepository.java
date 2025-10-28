package com.agi.hack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agi.hack.model.Manutencao;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
}