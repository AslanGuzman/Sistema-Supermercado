package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Producto extends AbstractEntity {

    private final String nombre;
    private final double precio;
    private final Categoria categoria;

    private Producto(Builder b) {
        this.id = b.id;
        this.nombre = b.nombre;
        this.precio = b.precio;
        this.categoria = b.categoria;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public Categoria getCategoria() { return categoria; }

    public static class Builder {
        private int id;
        private String nombre;
        private double precio;
        private Categoria categoria;

        public Builder id(int id) { this.id = id; return this; }
        public Builder nombre(String n) { this.nombre = n; return this; }
        public Builder precio(double p) { this.precio = p; return this; }
        public Builder categoria(Categoria c) { this.categoria = c; return this; }

        public Producto build() { return new Producto(this); }
    }

    @Override public String toString() { return nombre; }
}
