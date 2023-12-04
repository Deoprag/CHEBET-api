package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BetType {
    SimpleVictory,
    BrokenCar,
    SimplePosition,
    AverageTime,
    HeadToHead;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
