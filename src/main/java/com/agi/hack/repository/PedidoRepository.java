package com.agi.hack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agi.hack.model.Pedido;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("""
        SELECT COALESCE(SUM(p.quantidade), 0)
        FROM Pedido p
        WHERE p.itemSolicitado.id = :catalogoId
        AND p.status IN ('ABERTO_AUTOMATICO', 'APROVADO')
    """)
    long countPedidosAbertosPorCatalogoId(Long catalogoId);
    
}
