package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BetType {
    Vitoria_Simples,
    Carro_Quebra,
    Posicao_Simples,
    Posicao_Dupla,
    Menos_Tempo,
    Passar_Rodada,
    Media_Tempo_Corridas;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
