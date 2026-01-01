package com.supermarket.view.clientes;

import com.supermarket.controller.ClienteController;
import com.supermarket.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClienteController controller;
    private JTable tablaClientes;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public ClienteView() {
        try {
            this.controller = new ClienteController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
        
        cargarClientes();
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        
        panel.add(new JLabel("Buscar por Cédula o Nombre:"));
        searchField = new JTextField(20);
        searchField.addActionListener(e -> buscarCliente());
        panel.add(searchField);
        
        JButton buscarBtn = new JButton("🔍 Buscar");
        buscarBtn.addActionListener(e -> buscarCliente());
        panel.add(buscarBtn);
        
        JButton actualizarBtn = new JButton("🔄 Actualizar");
        actualizarBtn.addActionListener(e -> cargarClientes());
        panel.add(actualizarBtn);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Clientes"));
        
        String[] columnas = {"ID", "Cédula", "Nombre", "Apellido", "Teléfono", 
                            "Email", "Membresía", "Fecha Registro"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaClientes = new JTable(tableModel);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAgregar = new JButton("➕ AGREGAR CLIENTE");
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> agregarCliente());
        panel.add(btnAgregar);
        
        JButton btnEditar = new JButton("✏️ EDITAR");
        btnEditar.setBackground(new Color(33, 150, 243));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> editarCliente());
        panel.add(btnEditar);
        
        JButton btnEliminar = new JButton("🗑️ ELIMINAR");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarCliente());
        panel.add(btnEliminar);
        
        return panel;
    }
    
    private void cargarClientes() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = controller.obtenerTodos();
        
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getCedula(),
                c.getNombre(),
                c.getApellido(),
                c.getTelefono() != null ? c.getTelefono() : "N/A",
                c.getEmail() != null ? c.getEmail() : "N/A",
                c.isMembresia() ? "✅ Sí" : "❌ No",
                c.getFechaRegistro()
            });
        }
    }
    
    private void buscarCliente() {
        String busqueda = searchField.getText().trim();
        if (busqueda.isEmpty()) {
            cargarClientes();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Cliente> clientes = controller.obtenerTodos();
        
        for (Cliente c : clientes) {
            if (c.getCedula().contains(busqueda) ||
                c.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
                c.getApellido().toLowerCase().contains(busqueda.toLowerCase())) {
                
                tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getCedula(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getTelefono(),
                    c.getEmail(),
                    c.isMembresia() ? "✅ Sí" : "❌ No",
                    c.getFechaRegistro()
                });
            }
        }
    }
    
    private void agregarCliente() {
        ClienteDialog dialog = new ClienteDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), controller);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarClientes();
        }
    }
    
    private void editarCliente() {
        int row = tablaClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        
        int id = (int) tableModel.getValueAt(row, 0);
        Cliente cliente = controller.obtenerPorId(id);
        
        ClienteDialog dialog = new ClienteDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), controller, cliente);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarClientes();
        }
    }
    
    private void eliminarCliente() {
        int row = tablaClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este cliente?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            
            if (controller.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar");
            }
        }
    }
}