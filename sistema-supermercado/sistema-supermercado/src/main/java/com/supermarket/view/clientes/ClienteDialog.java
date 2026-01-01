package com.supermarket.view.clientes;

import com.supermarket.controller.ClienteController;
import com.supermarket.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ClienteDialog extends JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClienteController controller;
    private Cliente cliente;
    private boolean confirmado = false;
    
    private JTextField cedulaField, nombreField, apellidoField;
    private JTextField telefonoField, emailField;
    private JCheckBox membresiaCheck;
    
    public ClienteDialog(Frame parent, ClienteController controller) {
        this(parent, controller, null);
    }
    
    public ClienteDialog(Frame parent, ClienteController controller, Cliente cliente) {
        super(parent, cliente == null ? "Agregar Cliente" : "Editar Cliente", true);
        this.controller = controller;
        this.cliente = cliente;
        
        inicializarUI();
        if (cliente != null) cargarDatos();
        
        SwingUtilities.invokeLater(() -> cedulaField.requestFocus());
    }
    
    private void inicializarUI() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Cédula
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cédula:*"), gbc);
        gbc.gridx = 1;
        cedulaField = new JTextField(20);
        configurarEnter(cedulaField, nombreField);
        panel.add(cedulaField, gbc);
        
        // Nombre
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Nombre:*"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        configurarEnter(nombreField, apellidoField);
        panel.add(nombreField, gbc);
        
        // Apellido
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Apellido:*"), gbc);
        gbc.gridx = 1;
        apellidoField = new JTextField(20);
        configurarEnter(apellidoField, telefonoField);
        panel.add(apellidoField, gbc);
        
        // Teléfono
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        telefonoField = new JTextField(20);
        configurarEnter(telefonoField, emailField);
        panel.add(telefonoField, gbc);
        
        // Email
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        // Membresía
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        membresiaCheck = new JCheckBox("Cliente con Membresía (5% descuento)");
        panel.add(membresiaCheck, gbc);
        
        add(panel, BorderLayout.CENTER);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton guardarBtn = new JButton("💾 GUARDAR");
        guardarBtn.setBackground(new Color(76, 175, 80));
        guardarBtn.setForeground(Color.WHITE);
        guardarBtn.addActionListener(e -> guardar());
        botonesPanel.add(guardarBtn);
        
        JButton cancelarBtn = new JButton("❌ CANCELAR");
        cancelarBtn.addActionListener(e -> dispose());
        botonesPanel.add(cancelarBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
    }
    
    private void configurarEnter(JTextField actual, JTextField siguiente) {
        actual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    siguiente.requestFocus();
                }
            }
        });
    }
    
    private void cargarDatos() {
        cedulaField.setText(cliente.getCedula());
        cedulaField.setEditable(false);
        nombreField.setText(cliente.getNombre());
        apellidoField.setText(cliente.getApellido());
        telefonoField.setText(cliente.getTelefono());
        emailField.setText(cliente.getEmail());
        membresiaCheck.setSelected(cliente.isMembresia());
    }
    
    private void guardar() {
        if (!validar()) return;
        
        try {
            Cliente c = Cliente.builder()
                    .id(cliente != null ? cliente.getId() : 0)
                    .cedula(cedulaField.getText().trim())
                    .nombre(nombreField.getText().trim())
                    .apellido(apellidoField.getText().trim())
                    .telefono(telefonoField.getText().trim())
                    .email(emailField.getText().trim())
                    .membresia(membresiaCheck.isSelected())
                    .fechaRegistro(cliente != null ? cliente.getFechaRegistro() : LocalDate.now())
                    .build();
            
            boolean exito = cliente == null ? controller.guardar(c) : controller.actualizar(c);
            
            if (exito) {
                confirmado = true;
                JOptionPane.showMessageDialog(this, "Cliente guardado");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private boolean validar() {
        if (cedulaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La cédula es obligatoria");
            return false;
        }
        if (nombreField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        if (apellidoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido es obligatorio");
            return false;
        }
        return true;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
}
