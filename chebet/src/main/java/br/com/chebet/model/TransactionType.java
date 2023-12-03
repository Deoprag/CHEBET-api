package br.com.chebet.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    Deposito,
    Saque,
    Aposta,
    Pagamento;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
