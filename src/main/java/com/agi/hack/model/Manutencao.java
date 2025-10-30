package com.agi.hack.model;

import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusManutencao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutencao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"uuid"})
@ToString(exclude = {"equipamento", "funcionario", "usuario"})
public class Manutencao {

    // 🔹 Identificação
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdemServico;

    @Column(name = "id_uuid", nullable = false, unique = true, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    // 🔹 Dados do Equipamento
    @NotBlank(message = "O número serial não pode ficar vazio.")
    @Size(max = 50, message = "O número serial deve ter no máximo 50 caracteres.")
    @Column(name = "numero_serial", nullable = false, unique = true, length = 50)
    private String serialNumber;

    @NotNull(message = "O tipo de equipamento é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_equipamento", nullable = false)
    private ListaEquipamento tipoEquipamento;

    // 🔹 Controle de Status
    @Enumerated(EnumType.STRING)
    @Column(name = "status_manutencao", nullable = false)
    private StatusManutencao statusManutencao = StatusManutencao.PENDENTE;

    // 🔹 Datas de controle
    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @Column(name = "data_prevista", nullable = true)
    private LocalDate dataPrevista;

    @Column(name = "data_entrega", nullable = true)
    private LocalDate dataEntrega;

    // 🔹 Indica se houve falha (para sucata)
    @Column(name = "falha_detectada", nullable = false)
    private boolean falhaDetectada = false;

    @Column(name = "descricao_falha", length = 255)
    private String descricaoFalha;

    // 🔹 Relacionamentos
    @NotNull(message = "O equipamento é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipamento", nullable = false)
    private Equipamento equipamento;

    @NotNull(message = "O funcionário é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;

    // 🔹 Métodos auxiliares (não persistem no banco)
    @Transient
    public Long getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

    @Transient
    public Long getEquipamentoId() {
        return equipamento != null ? equipamento.getIdEquipamento() : null;
    }

    @Transient
    public Long getFuncionarioId() {
        return funcionario != null ? funcionario.getIdFuncionario() : null;
    }

    // Métodos de domínio (lógica simples dentro da entidade)
    public boolean isAtrasada() {
        return dataPrevista != null && dataEntrega == null && LocalDate.now().isAfter(dataPrevista);
    }

    public boolean isConcluida() {
        return dataEntrega != null && (dataEntrega.isBefore(dataPrevista) || dataEntrega.isEqual(dataPrevista));
    }

    public boolean isFalha() {
        return falhaDetectada;
    }
}
