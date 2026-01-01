package com.supermarket;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import com.supermarket.controller.ConfiguracionController;
import com.supermarket.view.login.LoginView;

public class App {
    
    public static void main(String[] args) {
        // Cargar tema desde configuración
        cargarTema();
        
        // Lanzar LoginView
        java.awt.EventQueue.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
    
    private static void cargarTema() {
        try {
            ConfiguracionController configController = new ConfiguracionController();
            String tema = configController.obtenerTemaActual();
            
            if ("dark".equals(tema)) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            
        } catch (Exception ex) {
            // Si falla, usar tema claro por defecto
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                System.err.println("Failed to initialize FlatLaf");
            }
        }
    }
}