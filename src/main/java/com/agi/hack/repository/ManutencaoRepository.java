package com.agi.hack.repository;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    // Buscar manutenção por status
    List<Manutencao> findByStatusManutencao(StatusManutencao status);

    // Buscar manutenção por tipo de equipamento
    List<Manutencao> findByTipoEquipamento(ListaEquipamento tipoEquipamento);

    // Buscar por funcionário responsável pela manutenção
    List<Manutencao> findByFuncionarioIdFuncionario(Long funcionarioId);

    // Por tipo e status
    List<Manutencao> findByTipoEquipamentoAndStatusManutencao(ListaEquipamento tipoEquipamento, StatusManutencao status);

    // Por funcionário e status
    List<Manutencao> findByFuncionarioIdFuncionarioAndStatusManutencao(Long funcionarioId, StatusManutencao status);

    // Por equipamento específico
    List<Manutencao> findByEquipamentoIdEquipamento(Long equipamentoId);

    // Buscar entre duas datas (equipamentos que entraram em manutenção em um período)
    List<Manutencao> findByDataEntradaBetween(LocalDate inicio, LocalDate fim);

    // Buscar previsão de entrega de um equipamento
    List<Manutencao> findByEquipamentoIdEquipamentoAndDataPrevista(Long equipamentoId, LocalDate dataPrevista);

    // Buscar data de entrega do equipamento após manutenção
    List<Manutencao> findByEquipamentoIdEquipamentoAndDataEntrega(Long equipamentoId, LocalDate dataEntrega);

}
