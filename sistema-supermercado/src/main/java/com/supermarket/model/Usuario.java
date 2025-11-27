package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Usuario extends AbstractEntity {

    private final String username;
    private final String passwordHash;
    private final Empleado empleado;
    private final Rol rol;
    private final boolean activo;

    private Usuario(Builder b) {
        this.id = b.id;
        this.username = b.username;
        this.passwordHash = b.passwordHash;
        this.empleado = b.empleado;
        this.rol = b.rol;
        this.activo = b.activo;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Empleado getEmpleado() { return empleado; }
    public Rol getRol() { return rol; }
    public boolean isActivo() { return activo; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String username;
        private String passwordHash;
        private Empleado empleado;
        private Rol rol;
        private boolean activo;

        public Builder id(int id) { this.id = id; return this; }
        public Builder username(String u) { this.username = u; return this; }
        public Builder passwordHash(String p) { this.passwordHash = p; return this; }
        public Builder empleado(Empleado e) { this.empleado = e; return this; }
        public Builder rol(Rol r) { this.rol = r; return this; }
        public Builder activo(boolean a) { this.activo = a; return this; }

        public Usuario build() { return new Usuario(this); }
    }

    @Override 
    public String toString() { return username; }
}