package com.agi.hack.enums;

public enum TipoMovimentacao {
    COMPRA,             // Veio da RequisicaoEquipamento (Joao)
    ALOCACAO,           // Foi para o Funcionario (Luan)
    DEVOLUCAO,          // Voltou do Funcionario (Luan)
    ENVIO_MANUTENCAO,   // Foi para a Manutencao (Carol)
    RETORNO_MANUTENCAO, // Voltou da Manutencao (Carol)
    DESCARTE            // Fim do ciclo de vida
}
