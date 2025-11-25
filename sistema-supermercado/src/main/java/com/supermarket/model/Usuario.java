package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class Usuario extends AbstractEntity {

    private final String username;
    private final String password;
    private final Empleado empleado;

    private Usuario(Builder b) {
        this.id = b.id;
        this.username = b.username;
        this.password = b.password;
        this.empleado = b.empleado;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Empleado getEmpleado() { return empleado; }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private Empleado empleado;

        public Builder id(int id) { this.id = id; return this; }
        public Builder username(String u) { this.username = u; return this; }
        public Builder password(String p) { this.password = p; return this; }
        public Builder empleado(Empleado e) { this.empleado = e; return this; }

        public Usuario build() { return new Usuario(this); }
    }

    @Override public String toString() { return username; }
}
