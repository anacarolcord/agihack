package com.agi.hack.repository;

import com.agi.hack.model.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {

    Optional<Catalogo> findBySku(String sku);
}
