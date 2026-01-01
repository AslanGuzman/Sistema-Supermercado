package com.supermarket.model;

import com.supermarket.model.base.AbstractPersona;
import java.time.LocalDate;

public class Empleado extends AbstractPersona {

    private final Rol rol;
    private final double salario;
    private final String direccion;
    private final LocalDate fechaIngreso;

    private Empleado(Builder builder) {
        super(builder);
        this.rol = builder.rol;
        this.salario = builder.salario;
        this.direccion = builder.direccion;
        this.fechaIngreso = builder.fechaIngreso;
    }

    public Rol getRol() { return rol; }
    public double getSalario() { return salario; }
    public String getDireccion() { return direccion; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractPersona.Builder<Builder> {
        private Rol rol;
        private double salario;
        private String direccion;
        private LocalDate fechaIngreso;

        public Builder rol(Rol r) { this.rol = r; return this; }
        public Builder salario(double s) { this.salario = s; return this; }
        public Builder direccion(String d) { this.direccion = d; return this; }
        public Builder fechaIngreso(LocalDate fi) { this.fechaIngreso = fi; return this; }


        @Override protected Builder self() { return this; }
        public Empleado build() { return new Empleado(this); }
    }

    @Override public String toString() {
        return nombre + " " + apellido + " (" + rol.getNombre() + ")";
    }
}