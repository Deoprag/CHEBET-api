package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    User,
    Admin;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
