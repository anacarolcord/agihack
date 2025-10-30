package com.agi.hack.service;

import com.agi.hack.dto.ManutencaoDTO.ManutencaoRequest;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.exception.ManutencaoException;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Manutencao;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.ManutencaoRepository;
import com.agi.hack.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Adicionado para logging (Sugestão 2)
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j // Logger
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
        return filtrarCanceladas(manutencaoRepository.findByFuncionario_IdFuncionario(funcionarioId));
    }

    public List<Manutencao> buscarPorTipoEStatusAtivo(ListaEquipamento tipo, StatusManutencao status) {
        return filtrarCanceladas(manutencaoRepository.findByTipoEquipamentoAndStatusManutencao(tipo, status));
    }

    public List<Manutencao> buscarPorFuncionarioEStatusAtivo(Long funcionarioId, StatusManutencao status) {
        return filtrarCanceladas(manutencaoRepository.findByFuncionario_IdFuncionarioAndStatusManutencao(funcionarioId, status));
    }

    public List<Manutencao> buscarPorEquipamento(Long equipamentoId) {
        return filtrarCanceladas(manutencaoRepository.findByEquipamento_IdEquipamento(equipamentoId));
    }

    public List<Manutencao> buscarPorDataEntrada(LocalDate inicio, LocalDate fim) {
        return filtrarCanceladas(manutencaoRepository.findByDataEntradaBetween(inicio, fim));
    }

    public List<Manutencao> buscarPorEquipamentoEDataPrevista(Long equipamentoId, LocalDate dataPrevista) {
        return filtrarCanceladas(manutencaoRepository.findByEquipamento_IdEquipamentoAndDataPrevista(equipamentoId, dataPrevista));
    }

    public List<Manutencao> buscarPorEquipamentoEDataEntrega(Long equipamentoId, LocalDate dataEntrega) {
        return filtrarCanceladas(manutencaoRepository.findByEquipamento_IdEquipamentoAndDataEntrega(equipamentoId, dataEntrega));
    }

    // ----------------------
    // MÉTODOS DE CRIAÇÃO/ATUALIZAÇÃO
    // ----------------------

    @Transactional
    public Manutencao criarManutencao(ManutencaoRequest request) {
        log.info("CRIAR: Iniciando criação de nova manutenção para Equipamento ID: {}", request.getEquipamentoId());

        Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado!"));

        if (equipamento.getFuncionario() == null) {
            log.error("CRIAR: Falha ao criar manutenção. Equipamento ID {} sem funcionário atribuído.", request.getEquipamentoId());
            throw new IllegalStateException("Equipamento sem funcionário atribuído!");
        }

        Manutencao manutencao = new Manutencao();
        manutencao.setEquipamento(equipamento);
        manutencao.setFuncionario(equipamento.getFuncionario());
        manutencao.setSerialNumber(equipamento.getNumeroSerie());

        // OBSERVAÇÃO (Sugestão 1): Uso direto do Enum. Se houver erro de nome, o try-catch em calcularDataPrevista ajuda.
        manutencao.setTipoEquipamento(ListaEquipamento.valueOf(equipamento.getNome()));

        manutencao.setStatusManutencao(request.getStatusManutencao() != null
                ? request.getStatusManutencao()
                : StatusManutencao.PENDENTE);
        manutencao.setDataEntrada(LocalDate.now());

        manutencao.setDataPrevista(calcularDataPrevista(equipamento));

        if (request.getIdUsuario() != null) {
            usuarioRepository.findById(request.getIdUsuario()).ifPresent(manutencao::setUsuario);
        }

        Manutencao savedManutencao = manutencaoRepository.save(manutencao);
        log.info("CRIAR: Manutenção ID {} criada com sucesso.", savedManutencao.getIdOrdemServico());
        return savedManutencao;
    }

    @Transactional
    public Manutencao atualizarManutencao(Long id, ManutencaoRequest request) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada"));

        log.info("ATUALIZAR: Iniciando atualização da manutenção ID {}", id);

        if (request.getEquipamentoId() != null) {
            Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                    .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
            if (equipamento.getFuncionario() == null) {
                log.error("ATUALIZAR: Falha ao atualizar. Equipamento ID {} sem funcionário atribuído.", equipamento.getIdEquipamento());
                throw new IllegalStateException("Equipamento sem funcionário atribuído!");
            }
            manutencao.setEquipamento(equipamento);
            manutencao.setSerialNumber(equipamento.getNumeroSerie());

            // OBSERVAÇÃO (Sugestão 1): Uso direto do Enum.
            manutencao.setTipoEquipamento(ListaEquipamento.valueOf(equipamento.getNome()));

            manutencao.setFuncionario(equipamento.getFuncionario());
            manutencao.setDataPrevista(calcularDataPrevista(equipamento));
        }

        if (request.getStatusManutencao() != null) {
            manutencao.setStatusManutencao(request.getStatusManutencao());
        }

        if (request.getIdUsuario() != null) {
            usuarioRepository.findById(request.getIdUsuario()).ifPresent(manutencao::setUsuario);
        }

        Manutencao updatedManutencao = manutencaoRepository.save(manutencao);
        log.info("ATUALIZAR: Manutenção ID {} atualizada com sucesso.", id);
        return updatedManutencao;
    }

    // ----------------------
    // MÉTODOS DE STATUS
    // ----------------------

    @Transactional
    public Manutencao cancelarManutencao(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada"));

        // A checagem por ENTREGUE foi removida daqui, pois ENTREGUE não existe mais no Enum.
        // Se a lógica é: se CONCLUIDA, não pode cancelar, a checagem abaixo deve ser adicionada:
        if (manutencao.getStatusManutencao() == StatusManutencao.CONCLUIDA) {
            log.warn("CANCELAR: Tentativa de cancelar manutenção ID {} já concluída.", id);
            throw new ManutencaoException("Não é possível cancelar uma manutenção já concluída.");
        }

        manutencao.setStatusManutencao(StatusManutencao.CANCELADO);
        Manutencao canceledManutencao = manutencaoRepository.save(manutencao);
        log.warn("CANCELAR: Manutenção ID {} cancelada com sucesso.", id);
        return canceledManutencao;
    }

    @Transactional
    public Manutencao entregarEquipamento(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada"));

        if (manutencao.getStatusManutencao() != StatusManutencao.CONCLUIDA) {
            log.warn("ENTREGAR: Tentativa de entregar manutenção ID {} com status {}. O status deve ser CONCLUIDA para a entrega.", id, manutencao.getStatusManutencao());
            throw new ManutencaoException("O equipamento só pode ser entregue se o status for CONCLUIDA.");
        }

        // O status permanece CONCLUIDA, a data de entrega é o marcador.
        manutencao.setDataEntrega(LocalDate.now());
        atualizarStatusAutomatico(manutencao); // Mantém a coerência do status

        Manutencao deliveredManutencao = manutencaoRepository.save(manutencao);
        log.info("ENTREGAR: Equipamento ID {} entregue com sucesso.", id);
        return deliveredManutencao;
    }

    // ✅ NOVO MÉTODO — ESTENDER PRAZO DE ENTREGA (Com Regras de Negócio Reforçadas)
    @Transactional
    public Manutencao estenderPrazoEntrega(Long id, int dias) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada"));

        if (dias <= 0) {
            log.warn("ESTENDER: Tentativa de estender prazo com dias inválidos ({}).", dias);
            throw new ManutencaoException("A quantidade de dias deve ser positiva.");
        }

        // Regra de Negócio Reforçada (Sugestão 3) - ENTREGUE removido da checagem
        StatusManutencao status = manutencao.getStatusManutencao();
        if (status == StatusManutencao.CANCELADO ||
                status == StatusManutencao.CONCLUIDA || // Se está CONCLUIDA, não deve estender o prazo
                status == StatusManutencao.FALHA) {
            log.warn("ESTENDER: Tentativa de estender prazo de manutenção ID {} com status {}.", id, status);
            throw new ManutencaoException("Não é possível estender o prazo de uma manutenção já cancelada, concluída ou com falha.");
        }

        if (manutencao.getDataPrevista() == null) {
            throw new IllegalStateException("A data prevista não está definida para esta manutenção.");
        }

        LocalDate novoPrazo = manutencao.getDataPrevista().plusDays(dias);
        manutencao.setDataPrevista(novoPrazo);
        log.info("ESTENDER: Prazo da Manutenção ID {} estendido em {} dias. Novo prazo: {}", id, dias, novoPrazo);

        // Atualiza o status automaticamente para refletir o novo prazo
        atualizarStatusAutomatico(manutencao);

        return manutencaoRepository.save(manutencao);
    }

    // ----------------------
    // MÉTODOS AUXILIARES (Regras de Negócio Pura - Sugestão 3)
    // ----------------------

    private List<Manutencao> filtrarCanceladas(List<Manutencao> manutencoes) {
        return manutencoes.stream()
                .filter(m -> m.getStatusManutencao() != StatusManutencao.CANCELADO)
                .toList();
    }

    private LocalDate calcularDataPrevista(Equipamento equipamento) {
        try {
            // OBSERVAÇÃO (Sugestão 1): Em um cenário ideal, o campo 'tipo' (Enum)
            // deveria estar diretamente na Entidade Equipamento para evitar o uso do 'nome'
            // para conversão e mitigar riscos de IllegalArgumentException.
            ListaEquipamento tipoEnum = ListaEquipamento.valueOf(equipamento.getNome());
            return LocalDate.now().plusDays(tipoEnum.getDiasManutencao());
        } catch (Exception e) {
            log.error("CALCULAR_PRAZO: Erro ao converter nome de equipamento '{}' para ListaEquipamento. Usando prazo padrão de 7 dias.",
                    equipamento.getNome(), e);
            return LocalDate.now().plusDays(7);
        }
    }

    /**
     * Aplica regras de negócio de status. Movida aqui (Sugestão 3) para centralizar a lógica de status.
     * Deve ser chamada sempre que houver mudança de data ou status manual.
     */
    private void atualizarStatusAutomatico(Manutencao m) {
        LocalDate hoje = LocalDate.now();

        // Status FINALIZADO (Concluída, Falha, Cancelada) não muda automaticamente
        if (m.getStatusManutencao() == StatusManutencao.CANCELADO ||
                m.getStatusManutencao() == StatusManutencao.CONCLUIDA ||
                m.getStatusManutencao() == StatusManutencao.FALHA) {
            // Se o equipamento foi entregue (dataEntrega != null), o status permanece CONCLUIDA
            if (m.getDataEntrega() != null && m.getStatusManutencao() != StatusManutencao.CONCLUIDA) {
                // Se a entrega foi registrada, mas o status não é CONCLUIDA, força para CONCLUIDA (Regra de Negócio)
                m.setStatusManutencao(StatusManutencao.CONCLUIDA);
                log.debug("STATUS_AUTO: Forçando Manutenção ID {} para CONCLUIDA devido à data de entrega.", m.getIdOrdemServico());
            }
            return;
        }

        // Se ainda não foi entregue, verifica o prazo
        if (hoje.isAfter(m.getDataPrevista())) {
            if (m.getStatusManutencao() != StatusManutencao.ATRASADA) {
                m.setStatusManutencao(StatusManutencao.ATRASADA);
                log.warn("STATUS_AUTO: Manutenção ID {} alterada para ATRASADA.", m.getIdOrdemServico());
            }
        }
        // Se está no prazo e não foi entregue, muda de PENDENTE para EM_REPARO
        else {
            if (m.getStatusManutencao() == StatusManutencao.PENDENTE) {
                m.setStatusManutencao(StatusManutencao.EM_REPARO);
                log.debug("STATUS_AUTO: Manutenção ID {} alterada para EM_REPARO (Iniciada).", m.getIdOrdemServico());
            }
        }
    }

    // ----------------------
    // TAREFA AGENDADA (Scheduler)
    // ----------------------

    /**
     * Tarefa agendada para atualizar status diariamente, garantindo que
     * manutenções atrasadas sejam marcadas corretamente.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Executa todo dia à meia-noite
    @Transactional
    public void atualizarStatusDiario() {
        log.info("SCHEDULER: Iniciando verificação diária de status de manutenções...");
        List<Manutencao> manutencoes = manutencaoRepository.findAll();
        int atualizacoes = 0;

        for (Manutencao m : manutencoes) {
            StatusManutencao statusAnterior = m.getStatusManutencao();
            atualizarStatusAutomatico(m);

            // Só salva se houve mudança de status para evitar IO desnecessário (Sugestão 2)
            if (m.getStatusManutencao() != statusAnterior) {
                manutencaoRepository.save(m);
                atualizacoes++;
                log.debug("SCHEDULER: Manutenção ID {} atualizada de {} para {}.", m.getIdOrdemServico(), statusAnterior, m.getStatusManutencao());
            }
        }
        log.info("SCHEDULER: Verificação diária concluída. Total de {} manutenções com status atualizado.", atualizacoes);
    }
}