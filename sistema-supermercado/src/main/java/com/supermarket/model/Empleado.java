package com.supermarket.model;

import com.supermarket.model.base.AbstractPersona;

public class Empleado extends AbstractPersona {

    private final Rol rol;
    private final double salario;

    private Empleado(Builder builder) {
        super(builder);
        this.rol = builder.rol;
        this.salario = builder.salario;
    }

    public Rol getRol() { return rol; }
    public double getSalario() { return salario; }

    public static class Builder extends AbstractPersona.Builder<Builder> {
        private Rol rol;
        private double salario;

        public Builder rol(Rol r) { this.rol = r; return this; }
        public Builder salario(double s) { this.salario = s; return this; }

        @Override protected Builder self() { return this; }
        public Empleado build() { return new Empleado(this); }
    }

    @Override public String toString() {
        return nombre + " " + apellido + " (" + rol.getNombre() + ")";
    }
}
