package com.deopraglabs.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    Better,
    Admin;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
