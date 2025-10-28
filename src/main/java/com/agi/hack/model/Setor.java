package com.agi.hack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // Necessário para a validação
import jakarta.validation.constraints.Size;   // Necessário para a validação
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Import de Funcionario foi removido, assumindo que está no mesmo pacote (com.agi.hack.model)
// Caso o erro persista, você precisará garantir que a classe Funcionario.java exista nesse pacote.

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "setores") // A tabela foi renomeada para 'setores' na sua Entidade anterior. Mantendo a correção.
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSetor;

    // --- Validações Adicionadas conforme os requisitos (não pode ser vazio, max 50) ---
    @NotBlank(message = "O nome do setor não pode estar vazio.")
    @Size(max = 50, message = "O nome do setor deve ter no máximo 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String nomeSetor;

    @OneToMany(mappedBy = "setor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funcionario> funcionarios; // A classe Funcionario deve estar no mesmo pacote!
}