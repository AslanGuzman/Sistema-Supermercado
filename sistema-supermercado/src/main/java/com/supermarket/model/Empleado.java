package com.supermarket.model;

import java.time.LocalDate;
import java.util.Objects;

public class Empleado {

    private int id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String direccion;
    private LocalDate fechaIngreso;

    public Empleado() {}

    public Empleado(int id, String nombre, String apellido, String cedula,
                    String telefono, String direccion, LocalDate fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaIngreso = fechaIngreso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    @Override
    public String toString() { return nombre + " " + apellido; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado emp = (Empleado) o;
        return id == emp.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
