package com.deopraglabs.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
    Amarelo,
    Azul,
    Branco,
    Fantasia,
    Laranja,
    Marrom,
    Prata,
    Preto,
    Roxo,
    Verde,
    Vermelho;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
