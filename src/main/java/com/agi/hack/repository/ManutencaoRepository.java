package com.agi.hack.repository;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    //Buscar manutenção por seu status
    List<Manutencao> findByStatusManutencao(StatusManutencao status);

    //Buscar equipamento por tipo
    List<Manutencao> findByTipoEquipamento(ListaEquipamento tipoequipamento);

    //Buscar por funcionario responsavel pelo equipamento
    List<Manutencao> findByEquipamentoFuncionarioId(Long funcionarioId);

    //Por tipo e status
    List<Manutencao> findByTipoEquipamentoAndStatusManutencao(ListaEquipamento listaEquipamento, StatusManutencao statusManutencao);

    //Por funcionario e status
    List<Manutencao> findByFuncionarioIdAndStatus(Long funcionarioId, StatusManutencao status);

    //Por equipamento especifico
    List<Manutencao> findByEquipamentoId(Long equipamentoId);
}
