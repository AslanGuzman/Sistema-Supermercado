package com.supermarket.view.producto;

import com.supermarket.controller.ProductoController;
import com.supermarket.model.Categoria;
import com.supermarket.model.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductoView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductoController controller;
    private JTable tablaProductos;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public ProductoView() {
        try {
            this.controller = new ProductoController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Buscar SKU:"));
        searchField = new JTextField(20);
        searchField.addActionListener(e -> cargarProductos());
        searchPanel.add(searchField);
        
        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.addActionListener(e -> cargarProductos());
        searchPanel.add(buscarBtn);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Panel central - tabla
        String[] columnas = {"ID", "SKU", "Nombre", "Descripción", "Precio", "Categoría"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProductos = new JTable(tableModel);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior - botones
        JPanel botonesPanel = new JPanel();
        
        JButton agregarBtn = new JButton("AGREGAR");
        agregarBtn.setBackground(new Color(76, 175, 80));
        agregarBtn.setForeground(Color.WHITE);
        agregarBtn.addActionListener(e -> agregarProducto());
        botonesPanel.add(agregarBtn);
        
        JButton editarBtn = new JButton("EDITAR");
        editarBtn.setBackground(new Color(33, 150, 243));
        editarBtn.setForeground(Color.WHITE);
        editarBtn.addActionListener(e -> editarProducto());
        botonesPanel.add(editarBtn);
        
        JButton eliminarBtn = new JButton("ELIMINAR");
        eliminarBtn.setBackground(new Color(244, 67, 54));
        eliminarBtn.setForeground(Color.WHITE);
        eliminarBtn.addActionListener(e -> eliminarProducto());
        botonesPanel.add(eliminarBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
        
        cargarProductos();
    }
    
    private void cargarProductos() {
        tableModel.setRowCount(0);
        java.util.List<Producto> productos = controller.obtenerTodos();
        for (Producto p : productos) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getSku(),
                p.getNombre(),
                p.getDescripcion(),
                String.format("$%.2f", p.getPrecio()),
                p.getCategoria() != null ? p.getCategoria().getNombre() : "N/A"
            });
        }
    }
    
    private void agregarProducto() {
        JOptionPane.showMessageDialog(this, "Función en desarrollo");
    }
    
    private void editarProducto() {
        int row = tablaProductos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        } else {
            JOptionPane.showMessageDialog(this, "Función en desarrollo");
        }
    }
    
    private void eliminarProducto() {
        int row = tablaProductos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
        } else {
            JOptionPane.showMessageDialog(this, "Función en desarrollo");
        }
    }
}