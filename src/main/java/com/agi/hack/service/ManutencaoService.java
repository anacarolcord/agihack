package com.agi.hack.service;

import com.agi.hack.dto.ManutencaoDTO.ManutencaoRequest;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Manutencao;
import com.agi.hack.model.Usuario;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.ManutencaoRepository;
import com.agi.hack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;


    // ----------------------
    // MÉTODOS DE CONSULTA
    // ----------------------

    public List<Manutencao> listarAtivos() {
        return filtrarCanceladas(manutencaoRepository.findAll());
    }

    public Optional<Manutencao> buscarPorIdAtivo(Long id) {
        return manutencaoRepository.findById(id)
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO);
    }

    public List<Manutencao> buscarPorStatusAtivo(StatusManutencao status) {
        if (status == StatusManutencao.CANCELADO) return List.of();
        return filtrarCanceladas(manutencaoRepository.findByStatusManutencao(status));
    }

    public List<Manutencao> buscarPorTipoAtivo(ListaEquipamento tipo) {
        return filtrarCanceladas(manutencaoRepository.findByTipoEquipamento(tipo));
    }

    public List<Manutencao> buscarPorFuncionarioAtivo(Long funcionarioId) {
        // CORRIGIDO: Usa o método correto da Repository (agora com underscore)
        return filtrarCanceladas(manutencaoRepository.findByFuncionario_IdFuncionario(funcionarioId));
    }

    public List<Manutencao> buscarPorTipoEStatusAtivo(ListaEquipamento tipo, StatusManutencao status) {
        return filtrarCanceladas(manutencaoRepository.findByTipoEquipamentoAndStatusManutencao(tipo, status));
    }

    public List<Manutencao> buscarPorFuncionarioEStatusAtivo(Long funcionarioId, StatusManutencao status) {
        // CORRIGIDO: Usa o método correto da Repository (agora com underscore)
        return filtrarCanceladas(manutencaoRepository.findByFuncionario_IdFuncionarioAndStatusManutencao(funcionarioId, status));
    }

    public List<Manutencao> buscarPorEquipamento(Long equipamentoId) {
        // CORRIGIDO: Usa o método correto da Repository (agora com underscore)
        return filtrarCanceladas(manutencaoRepository.findByEquipamento_IdEquipamento(equipamentoId));
    }

    public List<Manutencao> buscarPorDataEntrada(LocalDate inicio, LocalDate fim) {
        return filtrarCanceladas(manutencaoRepository.findByDataEntradaBetween(inicio, fim));
    }

    public List<Manutencao> buscarPorEquipamentoEDataPrevista(Long equipamentoId, LocalDate dataPrevista) {
        // CORRIGIDO: Usa o método correto da Repository (agora com underscore)
        return filtrarCanceladas(manutencaoRepository.findByEquipamento_IdEquipamentoAndDataPrevista(equipamentoId, dataPrevista));
    }

    public List<Manutencao> buscarPorEquipamentoEDataEntrega(Long equipamentoId, LocalDate dataEntrega) {
        // CORRIGIDO: Usa o método correto da Repository (agora com underscore)
        return filtrarCanceladas(manutencaoRepository.findByEquipamento_IdEquipamentoAndDataEntrega(equipamentoId, dataEntrega));
    }


    // ----------------------
    // MÉTODOS DE CRIAÇÃO/ATUALIZAÇÃO
    // ----------------------


    @Transactional
    public Manutencao criarManutencao(ManutencaoRequest request) {
        Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado!"));

        if (equipamento.getFuncionario() == null) {
            throw new RuntimeException("Equipamento sem funcionário atribuído!");
        }

        Manutencao manutencao = new Manutencao();
        manutencao.setEquipamento(equipamento);
        manutencao.setFuncionario(equipamento.getFuncionario());
        manutencao.setSerialNumber(equipamento.getNumeroSerie());
        manutencao.setTipoEquipamento(ListaEquipamento.valueOf(equipamento.getCatalogo().getCategoria().toUpperCase()));
        manutencao.setStatusManutencao(request.getStatusManutencao() != null ? request.getStatusManutencao() : StatusManutencao.PENDENTE);
        manutencao.setDataEntrada(LocalDate.now());

        // calcular dataPrevista
        try {
            ListaEquipamento tipoEnum = ListaEquipamento.valueOf(String.valueOf(equipamento.getCatalogo().getCategoria().toUpperCase()));
            manutencao.setDataPrevista(LocalDate.now().plusDays(tipoEnum.getDiasManutencao()));
        } catch (Exception e) {
            manutencao.setDataPrevista(LocalDate.now().plusDays(7));
        }

        // vincular usuário, se houver
        if (request.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(request.getIdUsuario()).orElse(null);
            manutencao.setUsuario(usuario);
        }

        return manutencaoRepository.save(manutencao);
    }

    @Transactional
    public Manutencao atualizarManutencao(Long id, ManutencaoRequest request) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));

        if (request.getEquipamentoId() != null) {
            Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                    .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
            if (equipamento.getFuncionario() == null) {
                throw new RuntimeException("Equipamento sem funcionário atribuído!");
            }
            manutencao.setEquipamento(equipamento);
            manutencao.setSerialNumber(equipamento.getNumeroSerie());
            manutencao.setTipoEquipamento(ListaEquipamento.valueOf(equipamento.getCatalogo().getCategoria().toUpperCase()));
            manutencao.setFuncionario(equipamento.getFuncionario());

            try {
                ListaEquipamento tipoEnum = ListaEquipamento.valueOf(String.valueOf(equipamento.getCatalogo().getCategoria().toUpperCase()));
                manutencao.setDataPrevista(LocalDate.now().plusDays(tipoEnum.getDiasManutencao()));
            } catch (Exception e) {
                manutencao.setDataPrevista(LocalDate.now().plusDays(7));
            }
        }

        if (request.getStatusManutencao() != null) {
            manutencao.setStatusManutencao(request.getStatusManutencao());
        }

        if (request.getIdUsuario() != null) {
            usuarioRepository.findById(request.getIdUsuario()).ifPresent(manutencao::setUsuario);
        }

        return manutencaoRepository.save(manutencao);
    }


    // ----------------------
    // MÉTODOS DE STATUS
    // ----------------------


    @Transactional
    public Manutencao cancelarManutencao(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));
        manutencao.setStatusManutencao(StatusManutencao.CANCELADO);
        return manutencaoRepository.save(manutencao);
    }

    @Transactional
    public Manutencao entregarEquipamento(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada"));
        manutencao.setStatusManutencao(StatusManutencao.CONCLUIDA);
        manutencao.setDataEntrega(LocalDate.now());
        return manutencaoRepository.save(manutencao);
    }


    // ----------------------
    // MÉTODO AUXILIAR
    // ----------------------


    private List<Manutencao> filtrarCanceladas(List<Manutencao> manutencoes) {
        return manutencoes.stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }
}