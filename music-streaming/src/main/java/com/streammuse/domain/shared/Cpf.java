package com.streammuse.domain.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Cpf {

    @Column(name = "cpf", nullable = false, length = 11)
    private String valor;

    protected Cpf() {
    }

    public Cpf(String valor) {
        this.valor = Objects.requireNonNull(valor, "CPF é obrigatório");
        if (!valor.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos");
        }
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cpf cpf)) {
            return false;
        }
        return valor.equals(cpf.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }
}
