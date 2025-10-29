package com.agi.hack.model;

import com.agi.hack.enums.StatusFuncionario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    @NotBlank(message = "O nome do funcionário não pode estar vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos.")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "O setor é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    @NotNull(message = "O cargo é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusFuncionario status;

    @OneToMany(mappedBy = "funcionario")
    @JsonIgnore
    private List<Equipamento> equipamentos;

    @OneToMany(mappedBy = "funcionario")
    @JsonIgnore
    private List<Movimentacao> movimentacoes;

    @OneToMany
    @JsonIgnore
    private List<Manutencao> manutencaos;

    @OneToOne(mappedBy = "funcionario")
    @JsonIgnore
    private Usuario usuario;
}