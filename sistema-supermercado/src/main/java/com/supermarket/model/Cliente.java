package com.supermarket.model;

import com.supermarket.model.base.AbstractPersona;
import java.time.LocalDate;

public class Cliente extends AbstractPersona {

    private final String email;
    private final boolean membresia;
    private final LocalDate fechaRegistro;

    private Cliente(Builder b) {
        super(b);
        this.email = b.email;
        this.membresia = b.membresia;
        this.fechaRegistro = b.fechaRegistro;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getEmail() { return email; }
    public boolean isMembresia() { return membresia; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    public static class Builder extends AbstractPersona.Builder<Builder> {

        private String email;
        private boolean membresia;
        private LocalDate fechaRegistro;

        public Builder email(String e) { this.email = e; return self(); }
        public Builder membresia(boolean m) { this.membresia = m; return self(); }
        public Builder fechaRegistro(LocalDate fr) { this.fechaRegistro = fr; return self(); }

        public Cliente build() { return new Cliente(this); }

        @Override
        protected Builder self() { return this; }
    }

    @Override public String toString() {
        return getNombre() + " " + getApellido();
    }
}