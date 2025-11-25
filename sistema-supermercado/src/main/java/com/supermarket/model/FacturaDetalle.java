package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class FacturaDetalle extends AbstractEntity {

    private final Producto producto;
    private final int cantidad;
    private final double precio;

    private FacturaDetalle(Builder b) {
        this.id = b.id;
        this.producto = b.producto;
        this.cantidad = b.cantidad;
        this.precio = b.precio;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    public static class Builder {
        private int id;
        private Producto producto;
        private int cantidad;
        private double precio;

        public Builder id(int id) { this.id = id; return this; }
        public Builder producto(Producto p) { this.producto = p; return this; }
        public Builder cantidad(int c) { this.cantidad = c; return this; }
        public Builder precio(double p) { this.precio = p; return this; }

        public FacturaDetalle build() { return new FacturaDetalle(this); }
    }
}
