package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.ClienteDao;
import com.supermarket.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    
    private ClienteDao clienteDao;
    
    public ClienteController() throws SQLException {
        this.clienteDao = DaoManager.getClienteDAO();
    }
    
    public List<Cliente> obtenerTodos() {
        return clienteDao.findAll();
    }
    
    public Cliente obtenerPorId(int id) {
        return clienteDao.findById(id);
    }
    
    public Cliente obtenerPorCedula(String cedula) {
        return clienteDao.findByCedula(cedula);
    }
    
    public boolean guardar(Cliente cliente) {
        return clienteDao.save(cliente);
    }
    
    public boolean actualizar(Cliente cliente) {
        return clienteDao.update(cliente);
    }
    
    public boolean eliminar(int id) {
        return clienteDao.delete(id);
    }
}