package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;
import java.time.LocalDateTime;

public class Log extends AbstractEntity {

    private final Usuario usuario;
    private final String accion;
    private final LocalDateTime fecha;

    private Log(Builder b) {
        this.id = b.id;
        this.usuario = b.usuario;
        this.accion = b.accion;
        this.fecha = b.fecha;
    }

    public Usuario getUsuario() { return usuario; }
    public String getAccion() { return accion; }
    public LocalDateTime getFecha() { return fecha; }
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Usuario usuario;
        private String accion;
        private LocalDateTime fecha;

        public Builder id(int i) { this.id = i; return this; }
        public Builder usuario(Usuario u) { this.usuario = u; return this; }
        public Builder accion(String a) { this.accion = a; return this; }
        public Builder fecha(LocalDateTime f) { this.fecha = f; return this; }

        public Log build() { return new Log(this); }
    }
}
