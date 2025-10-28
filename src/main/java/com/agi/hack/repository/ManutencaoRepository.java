package com.agi.hack.repository;

import com.agi.hack.enums.EquipmentList;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    //Buscar manutenção por seu status
    List<Manutencao> findByStatusManutencao(StatusManutencao status);

    //Buscar equipamento por tipo
    List<Manutencao> findByTipoEquipamento(EquipmentList tipoequipamento);

    //Buscar por funcionario responsavel pelo equipamento
    List<Manutencao> findByEquipamentoFuncionario(Long funcionarioId);

    //Por tipo e status
    List<Manutencao> findbyTipoEquipamentoAndStatusManutencao(EquipmentList equipmentList, StatusManutencao statusManutencao);

    //Por funcionario e status
    List<Manutencao> findbyTipoEquipamentoAnd


}
