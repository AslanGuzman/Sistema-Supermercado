package com.supermarket.model;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente {

    private int id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String email;
    private boolean membresia;
    private LocalDate fechaRegistro;

    public Cliente() {}

    public Cliente(int id, String nombre, String apellido, String cedula,
                   String telefono, String email, boolean membresia, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.membresia = membresia;
        this.fechaRegistro = fechaRegistro;
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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isMembresia() { return membresia; }
    public void setMembresia(boolean membresia) { this.membresia = membresia; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() { 
        return nombre + " " + apellido + (membresia ? " (Membresía)" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente c = (Cliente) o;
        return id == c.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
