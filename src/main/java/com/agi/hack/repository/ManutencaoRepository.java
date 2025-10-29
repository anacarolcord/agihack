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
    // CORREÇÃO: findByFuncionario_IdFuncionario
    List<Manutencao> findByFuncionario_IdFuncionario(Long funcionarioId);

    // Por tipo e status
    List<Manutencao> findByTipoEquipamentoAndStatusManutencao(ListaEquipamento tipoEquipamento, StatusManutencao status);

    // Por funcionário e status
    // CORREÇÃO: findByFuncionario_IdFuncionarioAndStatusManutencao
    List<Manutencao> findByFuncionario_IdFuncionarioAndStatusManutencao(Long funcionarioId, StatusManutencao status);

    // Por equipamento específico
    // CORREÇÃO: findByEquipamento_IdEquipamento
    List<Manutencao> findByEquipamento_IdEquipamento(Long equipamentoId);

    // Buscar entre duas datas (equipamentos que entraram em manutenção em um período)
    List<Manutencao> findByDataEntradaBetween(LocalDate inicio, LocalDate fim);

    // Buscar previsão de entrega de um equipamento
    // CORREÇÃO: findByEquipamento_IdEquipamentoAndDataPrevista
    List<Manutencao> findByEquipamento_IdEquipamentoAndDataPrevista(Long equipamentoId, LocalDate dataPrevista);

    // Buscar data de entrega do equipamento após manutenção
    // CORREÇÃO: findByEquipamento_IdEquipamentoAndDataEntrega
    List<Manutencao> findByEquipamento_IdEquipamentoAndDataEntrega(Long equipamentoId, LocalDate dataEntrega);

}