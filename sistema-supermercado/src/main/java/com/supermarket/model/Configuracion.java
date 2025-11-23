package com.supermarket.model;

import java.util.Objects;

public class Configuracion {

    private int id;
    private String clave;
    private String valor;

    public Configuracion() {}

    public Configuracion(int id, String clave, String valor) {
        this.id = id;
        this.clave = clave;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    @Override
    public String toString() { return clave + "=" + valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuracion)) return false;
        Configuracion conf = (Configuracion) o;
        return id == conf.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
