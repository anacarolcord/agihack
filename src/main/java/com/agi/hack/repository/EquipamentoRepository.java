package com.agi.hack.repository;

import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.model.Catalogo;
import com.agi.hack.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    @Query("""
        SELECT COUNT(e)
        FROM Equipamento e
        WHERE e.catalogo.id = :catalogoId
        AND e.status = 'DISPONIVEL'
    """)
    long countDisponiveisPorCatalogoId(Long catalogoId);

    Optional<Equipamento> findFirstByCatalogoAndStatus(Catalogo catalogo, StatusEquipamento status);
}
