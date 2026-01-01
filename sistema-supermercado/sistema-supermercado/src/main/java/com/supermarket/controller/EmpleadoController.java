package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.*;
import com.supermarket.model.*;
import java.sql.SQLException;
import java.util.List;

public class EmpleadoController {
    
    private EmpleadoDao empleadoDao;
    private UsuarioDao usuarioDao;
    private RolDao rolDao;
    
    public EmpleadoController() throws SQLException {
        this.empleadoDao = DaoManager.getEmpleadoDAO();
        this.usuarioDao = DaoManager.getUsuarioDAO();
        this.rolDao = DaoManager.getRolDAO();
    }
    
    /**
     * Obtiene todos los empleados
     */
    public List<Empleado> obtenerTodos() {
        return empleadoDao.findAll();
    }
    
    /**
     * Obtiene un empleado por ID
     */
    public Empleado obtenerPorId(int id) {
        return empleadoDao.findById(id);
    }
    
    /**
     * Busca empleado por cédula
     */
    public Empleado obtenerPorCedula(String cedula) {
        return empleadoDao.findByCedula(cedula);
    }
    
    /**
     * Guarda un nuevo empleado
     */
    public boolean guardar(Empleado empleado) {
        // Verificar que no exista la cédula
        Empleado existe = empleadoDao.findByCedula(empleado.getCedula());
        if (existe != null) {
            return false; // Ya existe
        }
        return empleadoDao.save(empleado);
    }
    
    /**
     * Actualiza un empleado existente
     */
    public boolean actualizar(Empleado empleado) {
        return empleadoDao.update(empleado);
    }
    
    /**
     * Elimina un empleado
     */
    public boolean eliminar(int id) {
        // Verificar que no tenga usuario asociado
        if (empleadoTieneUsuario(id)) {
            return false;
        }
        return empleadoDao.delete(id);
    }
    
    /**
     * Verifica si un empleado tiene usuario asociado
     */
    public boolean empleadoTieneUsuario(int empleadoId) {
        List<Usuario> usuarios = usuarioDao.findAll();
        for (Usuario u : usuarios) {
            if (u.getEmpleado().getId() == empleadoId) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Obtiene el usuario de un empleado
     */
    public Usuario obtenerUsuarioDeEmpleado(int empleadoId) {
        List<Usuario> usuarios = usuarioDao.findAll();
        for (Usuario u : usuarios) {
            if (u.getEmpleado().getId() == empleadoId) {
                return u;
            }
        }
        return null;
    }
    
    /**
     * Crea un usuario para un empleado
     */
    public boolean crearUsuario(Usuario usuario) {
        // Verificar que el empleado no tenga usuario
        if (empleadoTieneUsuario(usuario.getEmpleado().getId())) {
            return false;
        }
        
        // Verificar que el username no exista
        Usuario existe = usuarioDao.findByUsername(usuario.getUsername());
        if (existe != null) {
            return false;
        }
        
        return usuarioDao.save(usuario);
    }
    
    /**
     * Obtiene todos los roles
     */
    public List<Rol> obtenerTodosRoles() {
        return rolDao.findAll();
    }
    
    /**
     * Actualiza un usuario
     */
    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDao.update(usuario);
    }
    
    /**
     * Elimina un usuario
     */
    public boolean eliminarUsuario(int usuarioId) {
        return usuarioDao.delete(usuarioId);
    }
}