package com.agi.hack.enums;

public enum ListaEquipamento {

    NOTEBOOK(7),
    DESKTOP(5),
    IMPRESSORA(4),
    ROTEADOR(3),
    MONITOR(3),
    TABLET(3),
    MOUSE(2),
    TECLADO(2),
    HEADSET(2),
    WEBCAM(2);

    private final int diasManutencao;

    ListaEquipamento(int dias) {
        this.diasManutencao = dias;
    }

    public int getDiasManutencao() {
        return diasManutencao;
    }
}

