package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
    Amarelo,
    Azul,
    Branco,
    Fantasia,
    Laranja,
    Marrom,
    Preto,
    Prata,
    Roxo,
    Vermelho;
    
    @Override
    @JsonValue
    public String toString() {
        return name();
    }
}
