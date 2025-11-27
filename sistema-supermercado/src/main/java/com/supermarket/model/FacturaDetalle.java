package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class FacturaDetalle extends AbstractEntity {

    private final Producto producto;
    private final int cantidad;
    private final double precioUnitario;
    private final double subtotal;
    private final Factura factura;

    private FacturaDetalle(Builder b) {
        this.id = b.id;
        this.producto = b.producto;
        this.cantidad = b.cantidad;
        this.precioUnitario = b.precioUnitario;
        this.subtotal = b.subtotal;
        this.factura = b.factura;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
    public Factura getFactura() { return factura; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Producto producto;
        private int cantidad;
        private double precioUnitario;
        private double subtotal;
        private Factura factura;

        public Builder id(int id) { this.id = id; return this; }
        public Builder producto(Producto p) { this.producto = p; return this; }
        public Builder cantidad(int c) { this.cantidad = c; return this; }
        public Builder precioUnitario(double p) { this.precioUnitario = p; return this; }
        public Builder subtotal(double s) { this.subtotal = s; return this; }
        public Builder factura(Factura f) { this.factura = f; return this; }

        public FacturaDetalle build() { return new FacturaDetalle(this); }
    }
}