package com.supermarket.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Factura {

    private int id;
    private Cliente cliente;
    private Usuario usuario;
    private LocalDateTime fecha;
    private double total;
    private String metodoPago;
    private boolean membresiaAplicada;

    public Factura() {}

    public Factura(int id, Cliente cliente, Usuario usuario, 
                   LocalDateTime fecha, double total, String metodoPago, 
                   boolean membresiaAplicada) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
        this.membresiaAplicada = membresiaAplicada;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public boolean isMembresiaAplicada() { return membresiaAplicada; }
    public void setMembresiaAplicada(boolean membresiaAplicada) { this.membresiaAplicada = membresiaAplicada; }

    @Override
    public String toString() {
        return "Factura #" + id + " - Total: " + total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factura)) return false;
        Factura f = (Factura) o;
        return id == f.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
