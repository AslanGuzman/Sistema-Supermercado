package com.supermarket.view.login;

import com.supermarket.controller.LoginController;
import com.supermarket.model.Usuario;
import com.supermarket.view.main.MainView;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginView extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;
    private LoginController loginController;
    private Usuario usuarioAutenticado;
    
    public LoginView() {
        try {
            this.loginController = new LoginController();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de conexión a la base de datos", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        inicializarUI();
    }
    
    private void inicializarUI() {
        setTitle("Sistema Supermercado - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con diseño dividido
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        
        // Panel izquierdo - Imagen/Logo
        JPanel leftPanel = crearPanelIzquierdo();
        mainPanel.add(leftPanel);
        
        // Panel derecho - Formulario
        JPanel rightPanel = crearPanelFormulario();
        mainPanel.add(rightPanel);
        
        add(mainPanel);
    }
    
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(41, 128, 185)); // Azul moderno
        
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        
        // Logo/Icono
        JLabel iconLabel = new JLabel("🏪");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(iconLabel);
        
        contenido.add(Box.createVerticalStrut(20));
        
        // Título
        JLabel titleLabel = new JLabel("SISTEMA DE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(titleLabel);
        
        JLabel titleLabel2 = new JLabel("SUPERMERCADO");
        titleLabel2.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(titleLabel2);
        
        contenido.add(Box.createVerticalStrut(20));
        
        // Descripción
        JLabel descLabel = new JLabel("<html><center>Gestión integral de<br>ventas e inventario</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descLabel.setForeground(new Color(230, 230, 230));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(descLabel);
        
        panel.add(contenido);
        return panel;
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        // Título del formulario
        gbc.gridy = 0;
        JLabel formTitle = new JLabel("Iniciar Sesión");
        formTitle.setFont(new Font("Arial", Font.BOLD, 28));
        formTitle.setForeground(new Color(52, 73, 94));
        panel.add(formTitle, gbc);
        
        // Subtítulo
        gbc.gridy = 1;
        JLabel subtitle = new JLabel("Ingrese sus credenciales");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(127, 140, 141));
        panel.add(subtitle, gbc);
        
        gbc.gridy = 2;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        // Usuario
        gbc.gridy = 3;
        JLabel userLabel = new JLabel("Usuario");
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setForeground(new Color(52, 73, 94));
        panel.add(userLabel, gbc);
        
        gbc.gridy = 4;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });
        panel.add(usernameField, gbc);
        
        // Contraseña
        gbc.gridy = 5;
        JLabel passLabel = new JLabel("Contraseña");
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passLabel.setForeground(new Color(52, 73, 94));
        panel.add(passLabel, gbc);
        
        gbc.gridy = 6;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
        panel.add(passwordField, gbc);
        
        // Error Label
        gbc.gridy = 7;
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(new Color(231, 76, 60));
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(errorLabel, gbc);
        
        // Botón Login
        gbc.gridy = 8;
        gbc.insets = new Insets(20, 10, 10, 10);
        loginButton = new JButton("INGRESAR");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(250, 45));
        loginButton.addActionListener(e -> handleLogin());
        
        // Efecto hover
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(52, 152, 219));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(41, 128, 185));
            }
        });
        
        panel.add(loginButton, gbc);
        
        // Footer
        gbc.gridy = 9;
        gbc.insets = new Insets(30, 10, 10, 10);
        JLabel footer = new JLabel("© 2025 Sistema Supermercado v1.0");
        footer.setFont(new Font("Arial", Font.ITALIC, 10));
        footer.setForeground(new Color(149, 165, 166));
        panel.add(footer, gbc);
        
        return panel;
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("⚠ Por favor complete todos los campos");
            return;
        }
        
        // Mostrar loading
        loginButton.setEnabled(false);
        loginButton.setText("Verificando...");
        
        SwingWorker<Usuario, Void> worker = new SwingWorker() {
            @Override
            protected Usuario doInBackground() {
                return loginController.autenticar(username, password);
            }
            
            @Override
            protected void done() {
                try {
                    usuarioAutenticado = (Usuario) get();
                    
                    if (usuarioAutenticado != null && usuarioAutenticado.isActivo()) {
                        errorLabel.setText("");
                        
                        // Animación de éxito
                        loginButton.setText("✓ Acceso concedido");
                        loginButton.setBackground(new Color(39, 174, 96));
                        
                        // Esperar un poco y abrir MainView
                        Timer timer = new Timer(500, evt -> {
                            MainView mainView = new MainView(usuarioAutenticado);
                            mainView.setVisible(true);
                            dispose();
                        });
                        timer.setRepeats(false);
                        timer.start();
                        
                    } else {
                        errorLabel.setText("⚠ Usuario o contraseña incorrectos");
                        passwordField.setText("");
                        loginButton.setEnabled(true);
                        loginButton.setText("INGRESAR");
                        loginButton.setBackground(new Color(41, 128, 185));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loginButton.setEnabled(true);
                    loginButton.setText("INGRESAR");
                }
            }
        };
        
        worker.execute();
    }
    
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}