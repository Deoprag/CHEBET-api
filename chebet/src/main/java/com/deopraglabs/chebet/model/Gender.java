package com.deopraglabs.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    Masculino,
    Feminino,
    Outro;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
