package com.supermarket.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Caja {

    private int id;
    private Usuario usuario;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private double montoInicial;
    private Double montoFinal;
    private String estado;

    public Caja() {}

    public Caja(int id, Usuario usuario, LocalDateTime fechaApertura,
                LocalDateTime fechaCierre, double montoInicial,
                Double montoFinal, String estado) {

        this.id = id;
        this.usuario = usuario;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.montoInicial = montoInicial;
        this.montoFinal = montoFinal;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDateTime fechaApertura) { this.fechaApertura = fechaApertura; }

    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }

    public double getMontoInicial() { return montoInicial; }
    public void setMontoInicial(double montoInicial) { this.montoInicial = montoInicial; }

    public Double getMontoFinal() { return montoFinal; }
    public void setMontoFinal(Double montoFinal) { this.montoFinal = montoFinal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Caja #" + id + " - " + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caja)) return false;
        Caja c = (Caja) o;
        return id == c.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
