package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Producto extends AbstractEntity {

    private final String sku;
    private final String nombre;
    private final String descripcion;
    private final double precio;
    private final Categoria categoria;
    private final int categoriaId; 

    private Producto(Builder b) {
        
        this.id = b.id; 
        this.sku = b.sku;
        this.nombre = b.nombre;
        this.descripcion = b.descripcion;
        this.precio = b.precio;
        this.categoria = b.categoria;
        this.categoriaId = b.categoriaId;
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public String getSku() { return sku; }
    public String getDescripcion() { return descripcion; }
    public int getCategoriaId() { return categoriaId; }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public Categoria getCategoria() { return categoria; }


    public static class Builder {
        private int id;
        private String sku;
        private String descripcion;
        private int categoriaId;
        private String nombre;
        private double precio;
        private Categoria categoria;
        
        public Builder sku(String s) { this.sku = s; return this; }
        public Builder descripcion(String d) { this.descripcion = d; return this; }
        public Builder categoriaId(int cid) { this.categoriaId = cid; return this; }
        public Builder id(int id) { this.id = id; return this; }
        public Builder nombre(String n) { this.nombre = n; return this; }
        public Builder precio(double p) { this.precio = p; return this; }
        public Builder categoria(Categoria c) { this.categoria = c; return this; }

        public Producto build() { return new Producto(this); }
    }

    @Override public String toString() { return nombre; }
}