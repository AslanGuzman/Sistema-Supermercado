package com.supermarket.model;

import java.util.Objects;

public class Rol {

    private int id;
    private String nombre;

    public Rol() {}

    public Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        Rol rol = (Rol) o;
        return id == rol.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
