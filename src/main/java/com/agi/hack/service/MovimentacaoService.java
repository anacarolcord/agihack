package com.agi.hack.service;

import com.agi.hack.dto.MovimentacaoRequestDTO; // <<<< Importe o DTO

import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Funcionario;
import com.agi.hack.model.Manutencao;
import com.agi.hack.model.Movimentacao;
import com.agi.hack.model.RequisicaoEquipamento;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.FuncionarioRepository;
import com.agi.hack.repository.ManutencaoRepository;
import com.agi.hack.repository.MovimentacaoRepository;
import com.agi.hack.repository.RequisicaoEquipamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class MovimentacaoService {
    private final MovimentacaoRepository movimentacaoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final RequisicaoEquipamentoRepository requisicaoRepository;
    private final ManutencaoRepository manutencaoRepository;



    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository,
                               EquipamentoRepository equipamentoRepository,
                               FuncionarioRepository funcionarioRepository,
                               RequisicaoEquipamentoRepository requisicaoRepository,
                               ManutencaoRepository manutencaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.requisicaoRepository = requisicaoRepository;
        this.manutencaoRepository = manutencaoRepository;
    }


    @Transactional
    public Movimentacao criarMovimentacao(MovimentacaoRequestDTO dto) {
        Equipamento equipamento = equipamentoRepository.findById(dto.getIdEquipamento())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado com ID: " + dto.getIdEquipamento()));

        Funcionario funcionario = null;
        if (dto.getIdFuncionario() != null) {
            funcionario = funcionarioRepository.findById(dto.getIdFuncionario())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + dto.getIdFuncionario()));
        }


        Movimentacao novaMovimentacao = new Movimentacao();
        novaMovimentacao.setEquipamento(equipamento);
        novaMovimentacao.setFuncionario(funcionario);
        novaMovimentacao.setTipo(dto.getTipo());
        novaMovimentacao.setDataHoraMovimentacao(LocalDateTime.now());
        novaMovimentacao.setObservacoes(dto.getObservacoes());

        return movimentacaoRepository.save(novaMovimentacao);
    }

    public Optional<Movimentacao> buscarPorId(Long id) {
        return movimentacaoRepository.findById(id);
    }

    public List<Movimentacao> buscarTodas() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> buscarPorEquipamento(Long equipamentoId) {
        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado com ID: " + equipamentoId));
        return movimentacaoRepository.findByEquipamento(equipamento);
    }

    public List<Movimentacao> buscarPorFuncionario(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + funcionarioId));
        return movimentacaoRepository.findByFuncionario(funcionario);
    }
}