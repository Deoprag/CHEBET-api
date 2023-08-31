package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
    Black,
    Blue,
    Brown,
    Fantasy,
    Green,
    Orange,
    Purple,
    Red,
    Silver,
    White,
    Yellow;
    
    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
