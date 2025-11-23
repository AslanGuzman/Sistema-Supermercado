package com.supermarket.model;

import java.util.Objects;

public class Usuario {
	 
	private int id;
	private Empleado empleado;
	private Rol rol;
	private String username;
	private String passwordHash;
	private boolean activo;
	
	public Usuario() {}
	
	public Usuario(int id, Empleado empleado, Rol rol, String username, String passwordHash, boolean activo) 
	{
		this.id = id;
		this.empleado = empleado;
		this.rol = rol;
        this.username = username;
        this.passwordHash = passwordHash;
        this.activo = activo;
	}
	
	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    
    @Override
    public String toString() {return username + " (" + rol.getNombre() + ") ";}
    
    
    @Override
    public boolean equals(Object o) {
    	if(this == o) return true;
    	if(!(o instanceof Usuario)) return false;
    	Usuario u = (Usuario) o;
    	return id == u.id;
    }
    

    @Override
    public int hashCode() {return Objects.hash(id);}
	
	
}
