package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Inventario extends AbstractEntity {

    private final Producto producto;
    private final int cantidad;

    private Inventario(Builder b) {
        this.id = b.id;
        this.producto = b.producto;
        this.cantidad = b.cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public static class Builder {
        private int id;
        private Producto producto;
        private int cantidad;

        public Builder id(int id) { this.id = id; return this; }
        public Builder producto(Producto p) { this.producto = p; return this; }
        public Builder cantidad(int c) { this.cantidad = c; return this; }

        public Inventario build() { return new Inventario(this); }
    }
}
