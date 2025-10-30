package com.agi.hack.enums;

public enum StatusManutencao {

    PENDENTE,     // aguardando início
    EM_REPARO,    // em manutenção dentro do prazo
    ATRASADA,     // ultrapassou o prazo previsto
    CONCLUIDA,    // reparo finalizado com sucesso
    FALHA,        // perda total / reparo não foi possível
    CANCELADO     // cancelado manualmente
}
