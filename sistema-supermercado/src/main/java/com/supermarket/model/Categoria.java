package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Categoria extends AbstractEntity {

    private final String nombre;

    private Categoria(Builder b) {
        this.id = b.id;
        this.nombre = b.nombre;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getNombre() { return nombre; }

    public static class Builder {
        private int id;
        private String nombre;

        public Builder id(int i) { this.id = i; return this; }
        public Builder nombre(String n) { this.nombre = n; return this; }

        public Categoria build() { return new Categoria(this); }
    }
}
