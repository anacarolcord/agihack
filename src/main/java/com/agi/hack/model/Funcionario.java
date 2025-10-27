package com.agi.hack.model;

import com.agi.hack.enums.StatusFuncionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    private String email;

    @Column(nullable = false)
    private StatusFuncionario statusFuncionario;

    private LocalDate dataCadastro;

    @Column(nullable = false)
    private LocalDate dataAdmissao;

    private LocalDate dataDesligamento;

    @ManyToOne
    @JoinColumn(name = "id_setor")
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;
}