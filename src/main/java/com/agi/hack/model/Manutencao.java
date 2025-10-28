package com.agi.hack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manutencoes") // Nome da tabela no banco
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Adicione campos reais de Manutenção aqui. Exemplo:
    private String descricao;
    // private LocalDate dataManutencao;

    // Se a manutenção for relacionada ao funcionário, adicione o relacionamento N:1 aqui:
    // @ManyToOne
    // @JoinColumn(name = "funcionario_id")
    // private Funcionario funcionario;
}