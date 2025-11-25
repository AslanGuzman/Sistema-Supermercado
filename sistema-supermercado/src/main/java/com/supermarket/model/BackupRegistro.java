package com.supermarket.model;

import com.supermarket.model.base.AbstractEntity;

public class BackupRegistro extends AbstractEntity {

    private final String descripcion;
    private final String archivo;

    private BackupRegistro(Builder b) {
        this.id = b.id;
        this.descripcion = b.descripcion;
        this.archivo = b.archivo;
    }

    public String getDescripcion() { return descripcion; }
    public String getArchivo() { return archivo; }

    public static class Builder {
        private int id;
        private String descripcion;
        private String archivo;

        public Builder id(int id) { this.id = id; return this; }
        public Builder descripcion(String d) { this.descripcion = d; return this; }
        public Builder archivo(String a) { this.archivo = a; return this; }

        public BackupRegistro build() { return new BackupRegistro(this); }
    }
}
