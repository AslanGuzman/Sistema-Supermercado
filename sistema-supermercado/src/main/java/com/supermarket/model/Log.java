package com.supermarket.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Log {

    private int id;
    private Usuario usuario;
    private String accion;
    private LocalDateTime fecha;

    public Log() {}

    public Log(int id, Usuario usuario, String accion, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.accion = accion;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return fecha + " - " + accion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log)) return false;
        Log log = (Log) o;
        return id == log.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
