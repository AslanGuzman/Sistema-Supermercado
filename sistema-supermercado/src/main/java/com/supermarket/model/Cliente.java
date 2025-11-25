package com.supermarket.model;

import com.supermarket.model.base.AbstractPersona;

public class Cliente extends AbstractPersona {

    private Cliente(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractPersona.Builder<Builder> {
        public Cliente build() { return new Cliente(this); }
        @Override protected Builder self() { return this; }
    }

    @Override public String toString() {
        return nombre + " " + apellido;
    }
}
