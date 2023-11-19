package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BetType {
    Vitoria_Simples,
    Carro_Quebrado,
    Posicao_Simples,
    Posicao_Dupla,
    Menos_Tempo,
    Tempo_Medio;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
