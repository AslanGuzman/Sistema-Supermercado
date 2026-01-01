package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.ProductoDao;
import com.supermarket.dao.interfaces.InventarioDao;
import com.supermarket.model.Producto;
import com.supermarket.model.Inventario;
import java.sql.SQLException;
import java.util.List;

public class ProductoController {
    
    private ProductoDao productoDao;
    private InventarioDao inventarioDao;
    
    public ProductoController() throws SQLException {
        this.productoDao = DaoManager.getProductoDAO();
        this.inventarioDao = DaoManager.getInventarioDAO();
    }
    
    public List<Producto> obtenerTodos() {
        return productoDao.findAll();
    }
    
    public Producto obtenerPorId(int id) {
        return productoDao.findById(id);
    }
    
    public Producto obtenerPorSku(String sku) {
        return productoDao.findBySku(sku);
    }
    
    public boolean guardar(Producto producto) {
        return productoDao.save(producto);
    }
    
    public boolean actualizar(Producto producto) {
        return productoDao.update(producto);
    }
    
    public boolean eliminar(int id) {
        return productoDao.delete(id);
    }
    
    public Inventario obtenerInventario(int productoId) {
        return inventarioDao.findByProductoId(productoId);
    }
}