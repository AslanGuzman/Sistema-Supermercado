package com.supermarket.model;

import java.time.LocalDate;
import java.util.Objects;

public class Producto {

    private int id;
    private String sku;
    private String nombre;
    private String descripcion;
    private Categoria categoria;
    private double precio;
    private Double precioMembresia;
    private String imagenPath;
    private LocalDate fechaCreado;

    public Producto() {}

    public Producto(int id, String sku, String nombre, String descripcion, 
                    Categoria categoria, double precio, Double precioMembresia,
                    String imagenPath, LocalDate fechaCreado) {
        this.id = id;
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.precioMembresia = precioMembresia;
        this.imagenPath = imagenPath;
        this.fechaCreado = fechaCreado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Double getPrecioMembresia() { return precioMembresia; }
    public void setPrecioMembresia(Double precioMembresia) { this.precioMembresia = precioMembresia; }

    public String getImagenPath() { return imagenPath; }
    public void setImagenPath(String imagenPath) { this.imagenPath = imagenPath; }

    public LocalDate getFechaCreado() { return fechaCreado; }
    public void setFechaCreado(LocalDate fechaCreado) { this.fechaCreado = fechaCreado; }

    @Override
    public String toString() { return nombre + " (" + sku + ")"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto p = (Producto) o;
        return id == p.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
