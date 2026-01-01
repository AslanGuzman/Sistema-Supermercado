package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.ConfiguracionDao;
import com.supermarket.dao.interfaces.BackupRegistroDao;
import com.supermarket.model.Configuracion;
import com.supermarket.model.BackupRegistro;
import com.supermarket.model.Usuario;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConfiguracionController {
    
    private ConfiguracionDao configuracionDao;
    private BackupRegistroDao backupRegistroDao;
    
    public ConfiguracionController() throws SQLException {
        this.configuracionDao = DaoManager.getConfiguracionDAO();
        this.backupRegistroDao = DaoManager.getBackupRegistroDAO();
    }
    
    /**
     * Obtiene el valor de una configuración por su clave
     */
    public String obtenerConfiguracion(String clave) {
        Configuracion config = configuracionDao.findByClave(clave);
        return config != null ? config.getValor() : null;
    }
    
    /**
     * Actualiza una configuración existente
     */
    public boolean actualizarConfiguracion(String clave, String valor) {
        Configuracion config = configuracionDao.findByClave(clave);
        
        if (config != null) {
            // Actualizar existente
            Configuracion actualizada = Configuracion.builder()
                    .id(config.getId())
                    .clave(clave)
                    .valor(valor)
                    .build();
            return configuracionDao.update(actualizada);
        } else {
            // Crear nueva
            Configuracion nueva = Configuracion.builder()
                    .clave(clave)
                    .valor(valor)
                    .build();
            return configuracionDao.save(nueva);
        }
    }
    
    /**
     * Registra un backup realizado
     */
    public boolean registrarBackup(int usuarioId, String ruta) {
        BackupRegistro backup = BackupRegistro.builder()
                .usuario(Usuario.builder().id(usuarioId).build())
                .ruta(ruta)
                .fecha(LocalDateTime.now())
                .build();
        
        return backupRegistroDao.save(backup);
    }
    
    /**
     * Obtiene el tema actual
     */
    public String obtenerTemaActual() {
        String tema = obtenerConfiguracion("theme");
        return tema != null ? tema : "light";
    }
    
    /**
     * Cambia el tema de la aplicación
     */
    public boolean cambiarTema(String tema) {
        return actualizarConfiguracion("theme", tema);
    }
}