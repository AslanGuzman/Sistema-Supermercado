package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.*;
import com.supermarket.model.*;
import java.sql.SQLException;

public class FacturacionController {
    
    private ProductoDao productoDao;
    private ClienteDao clienteDao;
    private FacturaDao facturaDao;
    private FacturaDetalleDao facturaDetalleDao;
    private InventarioDao inventarioDao;
    
    public FacturacionController() throws SQLException {
        this.productoDao = DaoManager.getProductoDAO();
        this.clienteDao = DaoManager.getClienteDAO();
        this.facturaDao = DaoManager.getFacturaDAO();
        this.setFacturaDetalleDao(DaoManager.getFacturaDetalleDAO());
        this.inventarioDao = DaoManager.getInventarioDAO();
    }
    
    /**
     * Busca un producto por su SKU
     */
    public Producto buscarProductoPorSku(String sku) {
        return productoDao.findBySku(sku);
    }
    
    /**
     * Busca un cliente por su cédula
     */
    public Cliente buscarClientePorCedula(String cedula) {
        return clienteDao.findByCedula(cedula);
    }
    
    /**
     * Verifica si hay inventario disponible
     */
    public boolean verificarInventario(int productoId, int cantidad) {
        Inventario inv = inventarioDao.findByProductoId(productoId);
        if (inv == null) return false;
        return inv.getCantidad() >= cantidad;
    }
    
    /**
     * Obtiene el nivel de inventario de un producto
     */
    public int obtenerInventario(int productoId) {
        Inventario inv = inventarioDao.findByProductoId(productoId);
        return inv != null ? inv.getCantidad() : 0;
    }
    
    /**
     * Guarda una factura completa (factura + detalles)
     */
    public boolean guardarFactura(Factura factura) {
        try {
            // Guardar factura
            boolean facturaGuardada = facturaDao.save(factura);
            if (!facturaGuardada) return false;
            
            // TODO: Aquí deberías obtener el ID de la factura generado
            // y luego guardar los detalles. Por ahora retorna true.
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene todas las facturas de un cliente
     */
    public java.util.List<Factura> obtenerFacturasCliente(int clienteId) {
        return facturaDao.findByClienteId(clienteId);
    }
    
    /**
     * Obtiene todas las facturas de un usuario
     */
    public java.util.List<Factura> obtenerFacturasUsuario(int usuarioId) {
        return facturaDao.findByUsuarioId(usuarioId);
    }

	public FacturaDetalleDao getFacturaDetalleDao() {
		return facturaDetalleDao;
	}

	public void setFacturaDetalleDao(FacturaDetalleDao facturaDetalleDao) {
		this.facturaDetalleDao = facturaDetalleDao;
	}
}