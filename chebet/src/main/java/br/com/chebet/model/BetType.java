package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BetType {
    Simple_Victory,
    Broken_Car,
    Simple_Position,
    Double_Position,
    Less_Time,
    Pass_Round,
    Avg_Race_Time;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
