package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Rol extends AbstractEntity {

    private final String nombre;

    private Rol(Builder b) {
        this.id = b.id;
        this.nombre = b.nombre;
    }

    public String getNombre() { return nombre; }

    public static class Builder {
        private int id;
        private String nombre;

        public Builder id(int id) { this.id = id; return this; }
        public Builder nombre(String n) { this.nombre = n; return this; }

        public Rol build() { return new Rol(this); }
    }

    @Override public String toString() { return nombre; }
}
