package com.supermarket.controller;

import com.supermarket.dao.manager.DaoManager;
import com.supermarket.dao.interfaces.UsuarioDao;
import com.supermarket.model.Usuario;
import java.security.MessageDigest;
import java.sql.SQLException;

public class LoginController {
    
    private UsuarioDao usuarioDao;
    
    public LoginController() throws SQLException {
        this.usuarioDao = DaoManager.getUsuarioDAO();
    }
    
    /**
     * Valida las credenciales del usuario
     * @param username nombre de usuario
     * @param password contraseña en texto plano
     * @return Usuario si es válido, null si no
     */
    public Usuario autenticar(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }
        
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) return null;
        
        // Hashear la contraseña ingresada y comparar
        String passwordHash = hashPassword(password);
        if (passwordHash.equals(usuario.getPasswordHash())) {
            return usuario;
        }
        
        return null;
    }
    
    /**
     * Genera hash SHA-256 de la contraseña
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}