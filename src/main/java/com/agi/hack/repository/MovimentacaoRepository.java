package com.agi.hack.repository;

import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Funcionario;
import com.agi.hack.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository  extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByEquipamento(Equipamento equipamento);
    List<Movimentacao> findByFuncionario(Funcionario funcionario);

}
