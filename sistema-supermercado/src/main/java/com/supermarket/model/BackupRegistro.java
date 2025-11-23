package com.supermarket.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class BackupRegistro {

    private int id;
    private Usuario usuario;
    private String ruta;
    private LocalDateTime fecha;

    public BackupRegistro() {}

    public BackupRegistro(int id, Usuario usuario, String ruta, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.ruta = ruta;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Backup en " + ruta + " (" + fecha + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BackupRegistro)) return false;
        BackupRegistro b = (BackupRegistro) o;
        return id == b.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
