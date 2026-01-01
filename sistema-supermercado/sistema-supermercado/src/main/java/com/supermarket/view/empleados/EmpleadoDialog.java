package com.supermarket.view.empleados;

import com.supermarket.controller.EmpleadoController;
import com.supermarket.model.Empleado;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class EmpleadoDialog extends JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpleadoController controller;
    private Empleado empleado;
    private boolean confirmado = false;
    
    private JTextField cedulaField, nombreField, apellidoField;
    private JTextField telefonoField, direccionField;
    
    public EmpleadoDialog(Frame parent, EmpleadoController controller) {
        this(parent, controller, null);
    }
    
    public EmpleadoDialog(Frame parent, EmpleadoController controller, Empleado empleado) {
        super(parent, empleado == null ? "Agregar Empleado" : "Editar Empleado", true);
        this.controller = controller;
        this.empleado = empleado;
        
        inicializarUI();
        if (empleado != null) cargarDatos();
        
        SwingUtilities.invokeLater(() -> cedulaField.requestFocus());
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
        configurarEnter(telefonoField, direccionField);
        panel.add(telefonoField, gbc);
        
        // Dirección
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        direccionField = new JTextField(20);
        panel.add(direccionField, gbc);
        
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
        cedulaField.setText(empleado.getCedula());
        cedulaField.setEditable(false); // No cambiar cédula
        nombreField.setText(empleado.getNombre());
        apellidoField.setText(empleado.getApellido());
        telefonoField.setText(empleado.getTelefono());
        direccionField.setText(empleado.getDireccion());
    }
    
    private void guardar() {
        if (!validar()) return;
        
        try {
            String cedula = cedulaField.getText().trim();
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String direccion = direccionField.getText().trim();
            
            Empleado emp = Empleado.builder()
                    .id(empleado != null ? empleado.getId() : 0)
                    .cedula(cedula)
                    .nombre(nombre)
                    .apellido(apellido)
                    .telefono(telefono.isEmpty() ? null : telefono)
                    .direccion(direccion.isEmpty() ? null : direccion)
                    .fechaIngreso(empleado != null ? empleado.getFechaIngreso() : LocalDate.now())
                    .build();
            
            boolean exito = empleado == null ? 
                controller.guardar(emp) : controller.actualizar(emp);
            
            if (exito) {
                confirmado = true;
                JOptionPane.showMessageDialog(this, "Empleado guardado");
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