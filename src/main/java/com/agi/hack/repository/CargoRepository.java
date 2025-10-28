package com.agi.hack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agi.hack.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
