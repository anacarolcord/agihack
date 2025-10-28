package com.agi.hack.model;

import jakarta.persistence.*;
import com.agi.hack.model.Funcionario;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "setores")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSetor;

    @Column(nullable = false, length = 50)
    private String nomeSetor;

    @OneToMany(mappedBy = "setor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funcionario> funcionarios; // Lista de funcionários deste setor
}