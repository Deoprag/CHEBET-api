package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    Male,
    Female,
    Other;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
