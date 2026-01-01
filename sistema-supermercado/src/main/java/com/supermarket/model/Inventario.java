package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Inventario extends AbstractEntity {

    private final Producto producto;
    private final int cantidad;
    private final int minimo;
    private final int maximo;

    private Inventario(Builder b) {
        this.id = b.id;
        this.producto = b.producto;
        this.cantidad = b.cantidad;
        this.minimo = b.minimo;
        this.maximo = b.maximo;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public int getMinimo() { return minimo; }
    public int getMaximo() { return maximo; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Producto producto;
        private int cantidad;
        private int minimo;
        private int maximo;

        public Builder id(int id) { this.id = id; return this; }
        public Builder producto(Producto p) { this.producto = p; return this; }
        public Builder cantidad(int c) { this.cantidad = c; return this; }
        public Builder minimo(int m) { this.minimo = m; return this; }
        public Builder maximo(int m) { this.maximo = m; return this; }

        public Inventario build() { return new Inventario(this); }
    }
}