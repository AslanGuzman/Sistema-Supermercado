package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Configuracion extends AbstractEntity {

    private final String clave;
    private final String valor;

    private Configuracion(Builder b) {
        this.id = b.id;
        this.clave = b.clave;
        this.valor = b.valor;
    }

    public String getClave() { return clave; }
    public String getValor() { return valor; }

    public static class Builder {
        private int id;
        private String clave;
        private String valor;

        public Builder id(int id) { this.id = id; return this; }
        public Builder clave(String c) { this.clave = c; return this; }
        public Builder valor(String v) { this.valor = v; return this; }

        public Configuracion build() { return new Configuracion(this); }
    }
}
