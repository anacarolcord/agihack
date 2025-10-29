package com.agi.hack.service;

import com.agi.hack.dto.ManutencaoDTO.ManutencaoRequest;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Manutencao;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.ManutencaoRepository;
import com.agi.hack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Manutencao> listarAtivos(){
        return manutencaoRepository.findAll().stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    public Optional<Manutencao> buscarPorIdAtivo(Long id){
        return manutencaoRepository.findById(id)
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO);
    }

    public List<Manutencao> buscarPorStatusAtivo(StatusManutencao statusManutencao){
        if (statusManutencao == StatusManutencao.CANCELADO) return List.of();
        return manutencaoRepository.findByStatusManutencao(statusManutencao);
    }

    public List<Manutencao> buscarPorTipoAtivo(ListaEquipamento tipo){
        return manutencaoRepository.findByTipoEquipamento(tipo).stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    public List<Manutencao> buscarPorFuncionarioAtivo(Long funcionarioId){
        return manutencaoRepository.findByEquipamentoFuncionarioId(funcionarioId).stream()
                .filter(m-> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    public List<Manutencao> buscarPorDataEntradaAtivo(LocalDate inicio, LocalDate fim) {
        return manutencaoRepository.findByDataEntradaBetween(inicio, fim).stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    public List<Manutencao> buscarPorEquipamentoEDataPrevistaAtivas(Long equipamentoId, LocalDate dataPrevisao){
        return manutencaoRepository.findByEquipamentoIdAndDataPrevisao(equipamentoId, dataPrevisao).stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    public List<Manutencao> buscarPorEquipamentoEDataEntrega(Long equipamentoId, LocalDate dataEntrega){
        return manutencaoRepository.findByEquipamentoIdAndDataEntrega(equipamentoId, dataEntrega).stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    public Manutencao criarManutencao(ManutencaoRequest request){
        Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado!"));

        Manutencao manutencao = new Manutencao();
        manutencao.setEquipamento(equipamento);
        manutencao.setSerialNumber(equipamento.getNumeroSerie());
        manutencao.setTipoEquipamento(equipamento.getNome());
        manutencao.setStatusManutencao(request.getStatusManutencao());
        manutencao.setDataEntrada(LocalDate.now());

        // calcular dataPrevista usando o enum ListaEquipamento, se possível
        try {
            ListaEquipamento tipoEnum = ListaEquipamento.valueOf(String.valueOf(equipamento.getNome()));
            manutencao.setDataPrevista(LocalDate.now().plusDays(tipoEnum.getDiasManutencao()));
        } catch (IllegalArgumentException | NullPointerException ex) {
            // fallback: se não encontrar no enum, define 7 dias padrão
            manutencao.setDataPrevista(LocalDate.now().plusDays(7));
        }

        if (request.getIdUsuario() != null) {
            usuarioRepository.findById(request.getIdUsuario())
                    .ifPresent(manutencao::setUsuario);
        }

        manutencao.setFuncionario(equipamento.getFuncionario());
        return manutencaoRepository.save(manutencao);
    }

    public Manutencao atualizarManutencao(Long id, ManutencaoRequest request) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));

        if (request.getEquipamentoId() != null) {
            Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                    .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
            manutencao.setEquipamento(equipamento);
            manutencao.setSerialNumber(equipamento.getNumeroSerie());
            manutencao.setTipoEquipamento(equipamento.getNome());
            manutencao.setFuncionario(equipamento.getFuncionario());

            try {
                ListaEquipamento tipoEnum = ListaEquipamento.valueOf(String.valueOf(equipamento.getNome()));
                manutencao.setDataPrevista(LocalDate.now().plusDays(tipoEnum.getDiasManutencao()));
            } catch (IllegalArgumentException | NullPointerException ex) {
                manutencao.setDataPrevista(LocalDate.now().plusDays(7));
            }
        }

        if (request.getStatusManutencao() != null) {
            manutencao.setStatusManutencao(request.getStatusManutencao());
        }

        if (request.getIdUsuario() != null) {
            usuarioRepository.findById(request.getIdUsuario())
                    .ifPresent(manutencao::setUsuario);
        }

        return manutencaoRepository.save(manutencao);
    }

    public Manutencao cancelarManutencao(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));
        manutencao.setStatusManutencao(StatusManutencao.CANCELADO);
        return  manutencaoRepository.save(manutencao);
    }

    public Manutencao entregarEquipamento(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));
        manutencao.setStatusManutencao(StatusManutencao.CONCLUIDA);
        manutencao.setDataEntrega(LocalDate.now());
        return manutencaoRepository.save(manutencao);
    }
}
