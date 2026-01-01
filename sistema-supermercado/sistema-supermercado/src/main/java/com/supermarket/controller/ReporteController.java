package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.*;
import com.supermarket.model.*;
import java.sql.SQLException;
import java.util.List;

public class ReporteController {
    
    private CajaDao cajaDao;
    private FacturaDao facturaDao;
    private FacturaDetalleDao facturaDetalleDao;
    
    public ReporteController() throws SQLException {
        this.cajaDao = DaoManager.getCajaDAO();
        this.facturaDao = DaoManager.getFacturaDAO();
        this.facturaDetalleDao = DaoManager.getFacturaDetalleDAO();
    }
    
    /**
     * Obtiene todas las cajas (abiertas y cerradas)
     */
    public List<Caja> obtenerTodasCajas() {
        return cajaDao.findAll();
    }
    
    /**
     * Obtiene todas las facturas
     */
    public List<Factura> obtenerTodasFacturas() {
        return facturaDao.findAll();
    }
    
    /**
     * Obtiene los detalles de una factura
     */
    public List<FacturaDetalle> obtenerDetallesFactura(int facturaId) {
        return facturaDetalleDao.findByFacturaId(facturaId);
    }
    
    /**
     * Calcula el total de ventas
     */
    public double calcularTotalVentas() {
        List<Factura> facturas = facturaDao.findAll();
        double total = 0;
        for (Factura f : facturas) {
            total += f.getTotal();
        }
        return total;
    }
    
    /**
     * Cuenta las cajas que están abiertas
     */
    public int contarCajasAbiertas() {
        List<Caja> cajas = cajaDao.findAll();
        int count = 0;
        for (Caja c : cajas) {
            if ("ABIERTA".equals(c.getEstado())) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Obtiene facturas por rango de fechas
     */
    public List<Factura> obtenerFacturasPorRango(java.time.LocalDateTime desde, 
                                                  java.time.LocalDateTime hasta) {
        List<Factura> todasFacturas = facturaDao.findAll();
        return todasFacturas.stream()
                .filter(f -> f.getFecha().isAfter(desde) && f.getFecha().isBefore(hasta))
                .collect(java.util.stream.Collectors.toList());
    }
}