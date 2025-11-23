package com.supermarket.model;

import java.util.Objects;

public class FacturaDetalle {

    private int id;
    private Factura factura;
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public FacturaDetalle() {}

    public FacturaDetalle(int id, Factura factura, Producto producto, 
                          int cantidad, double precioUnitario, double subtotal) {

        this.id = id;
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return cantidad + " x " + producto.getNombre() + " = " + subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacturaDetalle)) return false;
        FacturaDetalle fd = (FacturaDetalle) o;
        return id == fd.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
