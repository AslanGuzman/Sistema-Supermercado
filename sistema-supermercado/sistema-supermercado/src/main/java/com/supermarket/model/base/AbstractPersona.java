package com.supermarket.model.base;

public abstract class AbstractPersona extends AbstractEntity {

    protected final String nombre;
    protected final String apellido;
    protected final String cedula;
    protected final String telefono;

    protected AbstractPersona(Builder<?> builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.cedula = builder.cedula;
        this.telefono = builder.telefono;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCedula() { return cedula; }
    public String getTelefono() { return telefono; }

    public static abstract class Builder<T extends Builder<T>> {
        protected int id;
        protected String nombre;
        protected String apellido;
        protected String cedula;
        protected String telefono;

        public T id(int id) { this.id = id; return self(); }
        public T nombre(String n) { this.nombre = n; return self(); }
        public T apellido(String a) { this.apellido = a; return self(); }
        public T cedula(String c) { this.cedula = c; return self(); }
        public T telefono(String t) { this.telefono = t; return self(); }

        protected abstract T self();
    }
}
