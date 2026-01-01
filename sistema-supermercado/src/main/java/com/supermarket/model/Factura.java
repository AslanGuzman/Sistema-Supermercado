package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;
import java.time.LocalDateTime;
import java.util.List;

public class Factura extends AbstractEntity {

    private final Cliente cliente;
    private final Usuario usuario;
    private final LocalDateTime fecha;
    private final double total;
    private final String metodoPago;
    private final boolean membresiaAplicada;
    private final List<FacturaDetalle> detalles;

    private Factura(Builder b) {
        this.id = b.id;
        this.cliente = b.cliente;
        this.usuario = b.usuario;
        this.fecha = b.fecha;
        this.total = b.total;
        this.metodoPago = b.metodoPago;
        this.membresiaAplicada = b.membresiaAplicada;
        this.detalles = b.detalles;
    }

    public Cliente getCliente() { return cliente; }
    public Usuario getUsuario() { return usuario; }
    public LocalDateTime getFecha() { return fecha; }
    public double getTotal() { return total; }
    public String getMetodoPago() { return metodoPago; }
    public boolean isMembresiaAplicada() { return membresiaAplicada; }
    public List<FacturaDetalle> getDetalles() { return detalles; }

    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private int id;
        private Cliente cliente;
        private Usuario usuario;
        private LocalDateTime fecha;
        private double total;
        private String metodoPago;
        private boolean membresiaAplicada;
        private List<FacturaDetalle> detalles;

        public Builder id(int id) { this.id = id; return this; }
        public Builder cliente(Cliente c) { this.cliente = c; return this; }
        public Builder usuario(Usuario u) { this.usuario = u; return this; }
        public Builder fecha(LocalDateTime f) { this.fecha = f; return this; }
        public Builder total(double t) { this.total = t; return this; }
        public Builder metodoPago(String m) { this.metodoPago = m; return this; }
        public Builder membresiaAplicada(boolean ma) { this.membresiaAplicada = ma; return this; }
        public Builder detalles(List<FacturaDetalle> d) { this.detalles = d; return this; }

        public Factura build() { return new Factura(this); }
    }
}
