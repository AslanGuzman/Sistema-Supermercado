package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.*;
import com.supermarket.model.*;
import java.sql.SQLException;
import java.util.List;

public class InventarioController {
    
    private ProductoDao productoDao;
    private InventarioDao inventarioDao;
    private CategoriaDao categoriaDao;
    
    public InventarioController() throws SQLException {
        this.productoDao = DaoManager.getProductoDAO();
        this.inventarioDao = DaoManager.getInventarioDAO();
        this.categoriaDao = DaoManager.getCategoriaDAO();
    }
    
    /**
     * Obtiene todo el inventario
     */
    public List<Inventario> obtenerTodoInventario() {
        return inventarioDao.findAll();
    }
    
    /**
     * Obtiene un producto por ID
     */
    public Producto obtenerProductoPorId(int id) {
        return productoDao.findById(id);
    }
    
    /**
     * Obtiene inventario por producto ID
     */
    public Inventario obtenerInventarioPorProductoId(int productoId) {
        return inventarioDao.findByProductoId(productoId);
    }
    
    /**
     * Obtiene todas las categorías
     */
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaDao.findAll();
    }
    
    /**
     * Agrega un producto nuevo con su inventario
     */
    public boolean agregarProductoConInventario(Producto producto, int cantidad, 
                                                int minimo, int maximo) {
        try {
            // 1. Guardar producto
            boolean productoGuardado = productoDao.save(producto);
            if (!productoGuardado) return false;
            
            // 2. Buscar el producto recién guardado para obtener su ID
            Producto productoGuardado1 = productoDao.findBySku(producto.getSku());
            if (productoGuardado1 == null) return false;
            
            // 3. Crear inventario
            Inventario inventario = Inventario.builder()
                    .producto(productoGuardado1)
                    .cantidad(cantidad)
                    .minimo(minimo)
                    .maximo(maximo)
                    .build();
            
            return inventarioDao.save(inventario);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza un producto y su inventario
     */
    public boolean actualizarProductoConInventario(Producto producto, int cantidad, 
                                                   int minimo, int maximo) {
        try {
            // 1. Actualizar producto
            boolean productoActualizado = productoDao.update(producto);
            if (!productoActualizado) return false;
            
            // 2. Actualizar inventario
            Inventario inventario = inventarioDao.findByProductoId(producto.getId());
            
            if (inventario == null) {
                // Crear inventario si no existe
                inventario = Inventario.builder()
                        .producto(producto)
                        .cantidad(cantidad)
                        .minimo(minimo)
                        .maximo(maximo)
                        .build();
                return inventarioDao.save(inventario);
            } else {
                // Actualizar inventario existente
                inventario = Inventario.builder()
                        .id(inventario.getId())
                        .producto(producto)
                        .cantidad(cantidad)
                        .minimo(minimo)
                        .maximo(maximo)
                        .build();
                return inventarioDao.update(inventario);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina un producto y su inventario
     */
    public boolean eliminarProducto(int productoId) {
        try {
            // 1. Eliminar inventario primero (FK constraint)
            Inventario inv = inventarioDao.findByProductoId(productoId);
            if (inv != null) {
                inventarioDao.delete(inv.getId());
            }
            
            // 2. Eliminar producto
            return productoDao.delete(productoId);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza solo la cantidad en inventario (para ventas)
     */
    public boolean actualizarCantidad(int productoId, int nuevaCantidad) {
        try {
            Inventario inv = inventarioDao.findByProductoId(productoId);
            if (inv == null) return false;
            
            Inventario actualizado = Inventario.builder()
                    .id(inv.getId())
                    .producto(inv.getProducto())
                    .cantidad(nuevaCantidad)
                    .minimo(inv.getMinimo())
                    .maximo(inv.getMaximo())
                    .build();
            
            return inventarioDao.update(actualizado);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Reduce inventario (para ventas)
     */
    public boolean reducirInventario(int productoId, int cantidadVendida) {
        try {
            Inventario inv = inventarioDao.findByProductoId(productoId);
            if (inv == null) return false;
            
            int nuevaCantidad = inv.getCantidad() - cantidadVendida;
            if (nuevaCantidad < 0) return false;
            
            return actualizarCantidad(productoId, nuevaCantidad);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verifica si hay stock disponible
     */
    public boolean hayStock(int productoId, int cantidadRequerida) {
        Inventario inv = inventarioDao.findByProductoId(productoId);
        if (inv == null) return false;
        return inv.getCantidad() >= cantidadRequerida;
    }
}