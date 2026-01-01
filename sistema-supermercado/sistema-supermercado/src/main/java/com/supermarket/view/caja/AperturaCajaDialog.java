package com.supermarket.view.caja;

import com.supermarket.controller.CajaController;
import com.supermarket.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class AperturaCajaDialog extends JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CajaController controller;
    private Usuario usuario;
    private boolean confirmado = false;
    private JTextField montoField;
    
    public AperturaCajaDialog(Frame parent, CajaController controller, Usuario usuario) {
        super(parent, "Apertura de Caja", true);
        this.controller = controller;
        this.usuario = usuario;
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        inicializarUI();
    }
    
    private void inicializarUI() {
        setSize(500, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);
        
        // Panel superior con icono y título
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(41, 128, 185));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel iconLabel = new JLabel("💰");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(iconLabel);
        
        topPanel.add(Box.createVerticalStrut(15));
        
        JLabel titleLabel = new JLabel("APERTURA DE CAJA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        
        topPanel.add(Box.createVerticalStrut(10));
        
        JLabel userLabel = new JLabel("Usuario: " + usuario.getUsername());
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(new Color(236, 240, 241));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(userLabel);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Panel central con formulario
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Instrucción
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel instrLabel = new JLabel("<html><center>Ingrese el monto inicial con el que<br>abrirá la caja hoy</center></html>");
        instrLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        instrLabel.setForeground(new Color(127, 140, 141));
        instrLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(instrLabel, gbc);
        
        // Label Monto
        gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel montoLabel = new JLabel("Monto Inicial:");
        montoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        montoLabel.setForeground(new Color(52, 73, 94));
        centerPanel.add(montoLabel, gbc);
        
        // Campo Monto (CON FONDO BLANCO Y BORDE)
        gbc.gridx = 1;
        montoField = new JTextField("0.00", 15);
        montoField.setFont(new Font("Arial", Font.PLAIN, 16));
        montoField.setHorizontalAlignment(JTextField.RIGHT);
        
        // ESTILOS PARA QUE SE VEA EL TEXTO
        montoField.setBackground(Color.WHITE);
        montoField.setForeground(Color.BLACK);
        montoField.setCaretColor(Color.BLACK);
        montoField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        montoField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    abrirCaja();
                }
            }
        });
        
        centerPanel.add(montoField, gbc);
        
        // Símbolo de moneda
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JLabel simboloLabel = new JLabel("💵 Ingrese la cantidad en pesos dominicanos (DOP)");
        simboloLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        simboloLabel.setForeground(new Color(149, 165, 166));
        simboloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(simboloLabel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        botonesPanel.setBackground(Color.WHITE);
        
        JButton abrirBtn = new JButton("✅ ABRIR CAJA");
        abrirBtn.setBackground(new Color(46, 204, 113));
        abrirBtn.setForeground(Color.WHITE);
        abrirBtn.setFont(new Font("Arial", Font.BOLD, 14));
        abrirBtn.setFocusPainted(false);
        abrirBtn.setBorderPainted(false);
        abrirBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        abrirBtn.setPreferredSize(new Dimension(180, 45));
        abrirBtn.addActionListener(e -> abrirCaja());
        
        // Hover
        abrirBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abrirBtn.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                abrirBtn.setBackground(new Color(46, 204, 113));
            }
        });
        
        botonesPanel.add(abrirBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
        
        // Focus inicial en el campo
        SwingUtilities.invokeLater(() -> {
            montoField.requestFocus();
            montoField.selectAll();
        });
    }
    
    private void abrirCaja() {
        try {
            String textoMonto = montoField.getText().trim().replace(",", ".");
            double monto = Double.parseDouble(textoMonto);
            
            if (monto < 0) {
                JOptionPane.showMessageDialog(this, 
                    "El monto no puede ser negativo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (controller.abrirCaja(usuario, monto)) {
                confirmado = true;
                JOptionPane.showMessageDialog(this, 
                    String.format("✅ Caja abierta exitosamente\nMonto inicial: $%.2f", monto),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir caja. Es posible que ya tenga una caja abierta.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un monto válido (use punto para decimales)",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            montoField.requestFocus();
            montoField.selectAll();
        }
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
}