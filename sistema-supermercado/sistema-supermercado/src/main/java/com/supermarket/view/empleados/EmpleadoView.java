package com.supermarket.view.empleados;

import com.supermarket.controller.EmpleadoController;
import com.supermarket.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmpleadoView extends JPanel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpleadoController controller;
    private JTable tablaEmpleados;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton btnAgregar, btnEditar, btnEliminar, btnCrearUsuario;
    
    public EmpleadoView() {
        try {
            this.controller = new EmpleadoController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior
        add(crearPanelSuperior(), BorderLayout.NORTH);
        
        // Tabla
        add(crearPanelTabla(), BorderLayout.CENTER);
        
        // Botones
        add(crearPanelBotones(), BorderLayout.SOUTH);
        
        cargarEmpleados();
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        
        panel.add(new JLabel("Buscar por Cédula o Nombre:"));
        searchField = new JTextField(20);
        searchField.addActionListener(e -> buscarEmpleado());
        panel.add(searchField);
        
        JButton buscarBtn = new JButton("🔍 Buscar");
        buscarBtn.addActionListener(e -> buscarEmpleado());
        panel.add(buscarBtn);
        
        JButton actualizarBtn = new JButton("🔄 Actualizar");
        actualizarBtn.addActionListener(e -> cargarEmpleados());
        panel.add(actualizarBtn);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Empleados"));
        
        String[] columnas = {"ID", "Cédula", "Nombre", "Apellido", "Teléfono", 
                            "Dirección", "Fecha Ingreso", "Tiene Usuario"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEmpleados = new JTable(tableModel);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = new JButton("➕ AGREGAR EMPLEADO");
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> agregarEmpleado());
        panel.add(btnAgregar);
        
        btnEditar = new JButton("✏️ EDITAR");
        btnEditar.setBackground(new Color(33, 150, 243));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> editarEmpleado());
        panel.add(btnEditar);
        
        btnCrearUsuario = new JButton("👤 CREAR USUARIO");
        btnCrearUsuario.setBackground(new Color(255, 152, 0));
        btnCrearUsuario.setForeground(Color.WHITE);
        btnCrearUsuario.addActionListener(e -> crearUsuario());
        panel.add(btnCrearUsuario);
        
        btnEliminar = new JButton("🗑️ ELIMINAR");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        panel.add(btnEliminar);
        
        return panel;
    }
    
    private void cargarEmpleados() {
        tableModel.setRowCount(0);
        List<Empleado> empleados = controller.obtenerTodos();
        
        for (Empleado emp : empleados) {
            boolean tieneUsuario = controller.empleadoTieneUsuario(emp.getId());
            
            tableModel.addRow(new Object[]{
                emp.getId(),
                emp.getCedula(),
                emp.getNombre(),
                emp.getApellido(),
                emp.getTelefono() != null ? emp.getTelefono() : "N/A",
                emp.getDireccion() != null ? emp.getDireccion() : "N/A",
                emp.getFechaIngreso(),
                tieneUsuario ? "✅ Sí" : "❌ No"
            });
        }
    }
    
    private void buscarEmpleado() {
        String busqueda = searchField.getText().trim();
        if (busqueda.isEmpty()) {
            cargarEmpleados();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Empleado> empleados = controller.obtenerTodos();
        
        for (Empleado emp : empleados) {
            if (emp.getCedula().contains(busqueda) ||
                emp.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
                emp.getApellido().toLowerCase().contains(busqueda.toLowerCase())) {
                
                boolean tieneUsuario = controller.empleadoTieneUsuario(emp.getId());
                
                tableModel.addRow(new Object[]{
                    emp.getId(),
                    emp.getCedula(),
                    emp.getNombre(),
                    emp.getApellido(),
                    emp.getTelefono(),
                    emp.getDireccion(),
                    emp.getFechaIngreso(),
                    tieneUsuario ? "✅ Sí" : "❌ No"
                });
            }
        }
    }
    
    private void agregarEmpleado() {
        EmpleadoDialog dialog = new EmpleadoDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), controller);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarEmpleados();
        }
    }
    
    private void editarEmpleado() {
        int row = tablaEmpleados.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }
        
        int id = (int) tableModel.getValueAt(row, 0);
        Empleado empleado = controller.obtenerPorId(id);
        
        EmpleadoDialog dialog = new EmpleadoDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), controller, empleado);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarEmpleados();
        }
    }
    
    private void crearUsuario() {
        int row = tablaEmpleados.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }
        
        int empleadoId = (int) tableModel.getValueAt(row, 0);
        
        // Verificar si ya tiene usuario
        if (controller.empleadoTieneUsuario(empleadoId)) {
            JOptionPane.showMessageDialog(this, 
                "Este empleado ya tiene un usuario asociado");
            return;
        }
        
        Empleado empleado = controller.obtenerPorId(empleadoId);
        
        UsuarioDialog dialog = new UsuarioDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), controller, empleado);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarEmpleados();
        }
    }
    
    private void eliminarEmpleado() {
        int row = tablaEmpleados.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }
        
        int id = (int) tableModel.getValueAt(row, 0);
        
        // Verificar si tiene usuario
        if (controller.empleadoTieneUsuario(id)) {
            JOptionPane.showMessageDialog(this, 
                "No puede eliminar un empleado con usuario asociado.\n" +
                "Primero elimine el usuario.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este empleado?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado");
                cargarEmpleados();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar");
            }
        }
    }
}