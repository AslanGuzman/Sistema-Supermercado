package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.CajaDao;
import com.supermarket.model.*;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CajaController {
    
    private CajaDao cajaDao;
    
    public CajaController() throws SQLException {
        this.cajaDao = DaoManager.getCajaDAO();
    }
    
    /**
     * Verifica si el usuario tiene una caja abierta
     */
    public boolean usuarioTieneCajaAbierta(int usuarioId) {
        Caja caja = cajaDao.findOpenCajaByUsuarioId(usuarioId);
        return caja != null;
    }
    
    /**
     * Obtiene la caja abierta del usuario
     */
    public Caja obtenerCajaAbierta(int usuarioId) {
        return cajaDao.findOpenCajaByUsuarioId(usuarioId);
    }
    
    /**
     * Abre una nueva caja
     */
    public boolean abrirCaja(Usuario usuario, double montoInicial) {
        // Verificar que no tenga caja abierta
        if (usuarioTieneCajaAbierta(usuario.getId())) {
            return false;
        }
        
        Caja caja = Caja.builder()
                .usuario(usuario)
                .fechaApertura(LocalDateTime.now())
                .montoInicial(montoInicial)
                .montoFinal(0.0)
                .estado("ABIERTA")
                .build();
        
        return cajaDao.save(caja);
    }
    
    /**
     * Cierra la caja actual
     */
    public boolean cerrarCaja(int usuarioId, double montoFinal) {
        Caja caja = cajaDao.findOpenCajaByUsuarioId(usuarioId);
        if (caja == null) return false;
        
        Caja cajaCerrada = Caja.builder()
                .id(caja.getId())
                .usuario(caja.getUsuario())
                .fechaApertura(caja.getFechaApertura())
                .fechaCierre(LocalDateTime.now())
                .montoInicial(caja.getMontoInicial())
                .montoFinal(montoFinal)
                .estado("CERRADA")
                .build();
        
        return cajaDao.update(cajaCerrada);
    }
}
