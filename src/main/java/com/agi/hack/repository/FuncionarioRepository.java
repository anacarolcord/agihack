package com.agi.hack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    // Métodos personalizados de consulta podem ser adicionados aqui, se necessário.
}