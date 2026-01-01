package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;
import java.time.LocalDateTime;

public class BackupRegistro extends AbstractEntity {

    private final String ruta;
    private final LocalDateTime fecha;
    private final Usuario usuario;

    private BackupRegistro(Builder b) {
        this.id = b.id;
        this.ruta = b.ruta;
        this.fecha = b.fecha;
        this.usuario = b.usuario;
    }

    public String getRuta() { return ruta; }
    public LocalDateTime getFecha() { return fecha; }
    public Usuario getUsuario() { return usuario; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String ruta;
        private LocalDateTime fecha;
        private Usuario usuario;

        public Builder id(int id) { this.id = id; return this; }
        public Builder ruta(String r) { this.ruta = r; return this; }
        public Builder fecha(LocalDateTime f) { this.fecha = f; return this; }
        public Builder usuario(Usuario u) { this.usuario = u; return this; }

        public BackupRegistro build() { return new BackupRegistro(this); }
    }
}