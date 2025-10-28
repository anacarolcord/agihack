package com.agi.hack.enums;

import lombok.Getter;

@Getter
public enum StatusFuncionario {

    // --- 1. CONSTANTES DO ENUM (Separadas por vírgula) ---
    ATIVO("Ativo", true),
    AFASTADO("Afastado", true),
    FERIAS("Férias", true),
    INATIVO("Inativo", false), // Alterado de DEMITIDO/DESATIVADO, conforme seu código

    // --- 2. PONTO E VÍRGULA OBRIGATÓRIO (Após a última constante) ---
    ;

    // --- 3. CAMPOS (Variáveis de instância) ---
    private final String descricao;
    private final boolean ativo; // Flag para facilitar consultas de 'Funcionários Ativos'

    // --- 4. CONSTRUTOR ---
    // ATENÇÃO: O nome do construtor DEVE ser o nome do Enum (StatusFuncionario), não FuncionarioStatus
    StatusFuncionario(String descricao, boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }
}