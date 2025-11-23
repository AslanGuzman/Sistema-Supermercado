package com.supermarket.model;

import java.util.Objects;

public class Inventario {

    private int id;
    private Producto producto;
    private int cantidad;
    private int minimo;
    private int maximo;

    public Inventario() {}

    public Inventario(int id, Producto producto, int cantidad, int minimo, int maximo) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getMinimo() { return minimo; }
    public void setMinimo(int minimo) { this.minimo = minimo; }

    public int getMaximo() { return maximo; }
    public void setMaximo(int maximo) { this.maximo = maximo; }

    @Override
    public String toString() { return producto.getNombre() + " - Cant: " + cantidad; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventario)) return false;
        Inventario inv = (Inventario) o;
        return id == inv.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
