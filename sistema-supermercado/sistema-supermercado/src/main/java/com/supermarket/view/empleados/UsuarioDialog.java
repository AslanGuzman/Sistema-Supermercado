package com.supermarket.view.empleados;

import com.supermarket.controller.EmpleadoController;
import com.supermarket.model.*;
import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.util.List;

public class UsuarioDialog extends JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpleadoController controller;
    private Empleado empleado;
    private boolean confirmado = false;
    
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<Rol> rolCombo;
    private JCheckBox activoCheck;
    
    public UsuarioDialog(Frame parent, EmpleadoController controller, Empleado empleado) {
        super(parent, "Crear Usuario para " + empleado.getNombre(), true);
        this.controller = controller;
        this.empleado = empleado;
        
        inicializarUI();
        SwingUtilities.invokeLater(() -> usernameField.requestFocus());
    }
    
    private void inicializarUI() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Info empleado
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Empleado: " + empleado.getNombre() + " " + empleado.getApellido());
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(infoLabel, gbc);
        gbc.gridwidth = 1;
        
        // Username
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Usuario:*"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        configurarEnter(usernameField, passwordField);
        panel.add(usernameField, gbc);
        
        // Password
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Contraseña:*"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        configurarEnter(passwordField, confirmPasswordField);
        panel.add(passwordField, gbc);
        
        // Confirm Password
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Confirmar:*"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField, gbc);
        
        // Rol
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Rol:*"), gbc);
        gbc.gridx = 1;
        rolCombo = new JComboBox<>();
        cargarRoles();
        panel.add(rolCombo, gbc);
        
        // Activo
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        activoCheck = new JCheckBox("Usuario activo");
        activoCheck.setSelected(true);
        panel.add(activoCheck, gbc);
        
        add(panel, BorderLayout.CENTER);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton guardarBtn = new JButton("💾 CREAR USUARIO");
        guardarBtn.setBackground(new Color(76, 175, 80));
        guardarBtn.setForeground(Color.WHITE);
        guardarBtn.addActionListener(e -> guardar());
        botonesPanel.add(guardarBtn);
        
        JButton cancelarBtn = new JButton("❌ CANCELAR");
        cancelarBtn.addActionListener(e -> dispose());
        botonesPanel.add(cancelarBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
    }
    
    private void configurarEnter(JComponent actual, JComponent siguiente) {
        actual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    siguiente.requestFocus();
                }
            }
        });
    }
    
    private void cargarRoles() {
        try {
            List<Rol> roles = controller.obtenerTodosRoles();
            for (Rol rol : roles) {
                rolCombo.addItem(rol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void guardar() {
        if (!validar()) return;
        
        try {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            Rol rol = (Rol) rolCombo.getSelectedItem();
            boolean activo = activoCheck.isSelected();
            
            String passwordHash = hashPassword(password);
            
            Usuario usuario = Usuario.builder()
                    .empleado(empleado)
                    .rol(rol)
                    .username(username)
                    .passwordHash(passwordHash)
                    .activo(activo)
                    .build();
            
            if (controller.crearUsuario(usuario)) {
                confirmado = true;
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear usuario");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
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
    
    private boolean validar() {
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario es obligatorio");
            return false;
        }
        
        String pass1 = new String(passwordField.getPassword());
        String pass2 = new String(confirmPasswordField.getPassword());
        
        if (pass1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La contraseña es obligatoria");
            return false;
        }
        
        if (!pass1.equals(pass2)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
            return false;
        }
        
        if (pass1.length() < 4) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres");
            return false;
        }
        
        if (rolCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un rol");
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
}