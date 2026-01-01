package com.supermarket.view.inventario;

import com.supermarket.controller.InventarioController;
import com.supermarket.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventarioView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventarioController controller;
    private JTable tablaInventario;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActualizar;
    
    public InventarioView() {
        try {
            this.controller = new InventarioController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - búsqueda y botones principales
        add(crearPanelSuperior(), BorderLayout.NORTH);
        
        // Panel central - tabla
        add(crearPanelTabla(), BorderLayout.CENTER);
        
        // Panel inferior - botones de acción
        add(crearPanelBotones(), BorderLayout.SOUTH);
        
        cargarInventario();
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        
        panel.add(new JLabel("Buscar Producto:"));
        searchField = new JTextField(20);
        panel.add(searchField);
        
        JButton buscarBtn = new JButton("🔍 Buscar");
        buscarBtn.addActionListener(e -> buscarProducto());
        panel.add(buscarBtn);
        
        btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.addActionListener(e -> cargarInventario());
        panel.add(btnActualizar);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Inventario de Productos"));
        
        String[] columnas = {"ID", "SKU", "Producto", "Categoría", "Precio", "Stock", "Mínimo", "Máximo", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaInventario = new JTable(tableModel);
        tablaInventario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaInventario.getTableHeader().setReorderingAllowed(false);
        
        // Ancho de columnas
        tablaInventario.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaInventario.getColumnModel().getColumn(1).setPreferredWidth(80);
        tablaInventario.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaInventario.getColumnModel().getColumn(8).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = new JButton("➕ AGREGAR PRODUCTO");
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.addActionListener(e -> agregarProducto());
        panel.add(btnAgregar);
        
        btnEditar = new JButton("✏️ EDITAR");
        btnEditar.setBackground(new Color(33, 150, 243));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.addActionListener(e -> editarProducto());
        panel.add(btnEditar);
        
        btnEliminar = new JButton("🗑️ ELIMINAR");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.addActionListener(e -> eliminarProducto());
        panel.add(btnEliminar);
        
        return panel;
    }
    
    private void cargarInventario() {
        tableModel.setRowCount(0);
        List<Inventario> inventarios = controller.obtenerTodoInventario();
        
        for (Inventario inv : inventarios) {
            Producto p = controller.obtenerProductoPorId(inv.getProducto().getId());
            if (p == null) continue;
            
            String estado = obtenerEstadoStock(inv);
            
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getSku(),
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría",
                String.format("$%.2f", p.getPrecio()),
                inv.getCantidad(),
                inv.getMinimo(),
                inv.getMaximo(),
                estado
            });
        }
    }
    
    private String obtenerEstadoStock(Inventario inv) {
        if (inv.getCantidad() <= inv.getMinimo()) {
            return "⚠️ BAJO";
        } else if (inv.getCantidad() >= inv.getMaximo()) {
            return "⚠️ EXCESO";
        } else {
            return "✅ NORMAL";
        }
    }
    
    private void buscarProducto() {
        String busqueda = searchField.getText().trim();
        if (busqueda.isEmpty()) {
            cargarInventario();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Inventario> inventarios = controller.obtenerTodoInventario();
        
        for (Inventario inv : inventarios) {
            Producto p = controller.obtenerProductoPorId(inv.getProducto().getId());
            if (p == null) continue;
            
            if (p.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
                p.getSku().toLowerCase().contains(busqueda.toLowerCase())) {
                
                String estado = obtenerEstadoStock(inv);
                
                tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getSku(),
                    p.getNombre(),
                    p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría",
                    String.format("$%.2f", p.getPrecio()),
                    inv.getCantidad(),
                    inv.getMinimo(),
                    inv.getMaximo(),
                    estado
                });
            }
        }
    }
    
    private void agregarProducto() {
        ProductoDialog dialog = new ProductoDialog((Frame) SwingUtilities.getWindowAncestor(this), controller);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarInventario();
        }
    }
    
    private void editarProducto() {
        int row = tablaInventario.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar");
            return;
        }
        
        int id = (int) tableModel.getValueAt(row, 0);
        Producto producto = controller.obtenerProductoPorId(id);
        Inventario inventario = controller.obtenerInventarioPorProductoId(id);
        
        ProductoDialog dialog = new ProductoDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), 
            controller, 
            producto, 
            inventario
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarInventario();
        }
    }
    
    private void eliminarProducto() {
        int row = tablaInventario.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este producto?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            
            if (controller.eliminarProducto(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
                cargarInventario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto");
            }
        }
    }
}