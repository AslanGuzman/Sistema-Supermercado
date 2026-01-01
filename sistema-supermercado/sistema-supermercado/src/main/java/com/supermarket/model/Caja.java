package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;
import java.time.LocalDateTime;

public class Caja extends AbstractEntity {

    private final Usuario usuario;
    private final LocalDateTime fechaApertura;
    private final LocalDateTime fechaCierre;
    private final double montoInicial;
    private final Double montoFinal;
    private final String estado;

    private Caja(Builder b) {
        this.id = b.id;
        this.usuario = b.usuario;
        this.fechaApertura = b.fechaApertura;
        this.fechaCierre = b.fechaCierre;
        this.montoInicial = b.montoInicial;
        this.montoFinal = b.montoFinal;
        this.estado = b.estado;
    }

    public Usuario getUsuario() { return usuario; }
    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public double getMontoInicial() { return montoInicial; }
    public Double getMontoFinal() { return montoFinal; }
    public String getEstado() { return estado; }
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Usuario usuario;
        private LocalDateTime fechaApertura;
        private LocalDateTime fechaCierre;
        private double montoInicial;
        private Double montoFinal;
        private String estado;

        public Builder id(int id) { this.id = id; return this; }
        public Builder usuario(Usuario u) { this.usuario = u; return this; }
        public Builder fechaApertura(LocalDateTime f) { this.fechaApertura = f; return this; }
        public Builder fechaCierre(LocalDateTime f) { this.fechaCierre = f; return this; }
        public Builder montoInicial(double m) { this.montoInicial = m; return this; }
        public Builder montoFinal(Double m) { this.montoFinal = m; return this; }
        public Builder estado(String e) { this.estado = e; return this; }

        public Caja build() { return new Caja(this); }
    }
}
