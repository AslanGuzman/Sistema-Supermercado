package com.supermarket.view.inventario;

import java.util.List;
import com.supermarket.controller.InventarioController;
import com.supermarket.dao.impl.CategoriaDaoImpl;
import com.supermarket.dao.interfaces.CategoriaDao;
import com.supermarket.model.*;
import javax.swing.*;
import java.awt.*;


public class ProductoDialog extends JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventarioController controller;
    private Producto producto;
    private Inventario inventario;
    private boolean confirmado = false;
    
    // Componentes
    private JTextField skuField, nombreField, descripcionField, precioField;
    private JTextField cantidadField, minimoField, maximoField;
    private JComboBox<Categoria> categoriaCombo;
    private JButton guardarBtn, cancelarBtn;
    
    // Constructor para agregar nuevo
    public ProductoDialog(Frame parent, InventarioController controller) {
        this(parent, controller, null, null);
    }
    
    // Constructor para editar existente
    public ProductoDialog(Frame parent, InventarioController controller, 
                         Producto producto, Inventario inventario) {
        super(parent, producto == null ? "Agregar Producto" : "Editar Producto", true);
        this.controller = controller;
        this.producto = producto;
        this.inventario = inventario;
        
        inicializarUI();
        cargarDatos();
    }
    
    private void inicializarUI() {
        setSize(500, 450);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal con campos
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // SKU
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("SKU:"), gbc);
        gbc.gridx = 1;
        skuField = new JTextField(20);
        mainPanel.add(skuField, gbc);
        
        // Nombre
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        mainPanel.add(nombreField, gbc);
        
        // Descripción
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        descripcionField = new JTextField(20);
        mainPanel.add(descripcionField, gbc);
        
        // Categoría
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        categoriaCombo = new JComboBox<>();
        cargarCategorias();
        mainPanel.add(categoriaCombo, gbc);
        
        // Precio
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        precioField = new JTextField(20);
        mainPanel.add(precioField, gbc);
        
        // Separador
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
        
        // INVENTARIO
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JLabel invLabel = new JLabel("INVENTARIO");
        invLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(invLabel, gbc);
        gbc.gridwidth = 1;
        
        // Cantidad
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        cantidadField = new JTextField(20);
        cantidadField.setText("0");
        mainPanel.add(cantidadField, gbc);
        
        // Mínimo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 1;
        minimoField = new JTextField(20);
        minimoField.setText("10");
        mainPanel.add(minimoField, gbc);
        
        // Máximo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        mainPanel.add(new JLabel("Stock Máximo:"), gbc);
        gbc.gridx = 1;
        maximoField = new JTextField(20);
        maximoField.setText("1000");
        mainPanel.add(maximoField, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        guardarBtn = new JButton("💾 GUARDAR");
        guardarBtn.setBackground(new Color(76, 175, 80));
        guardarBtn.setForeground(Color.WHITE);
        guardarBtn.addActionListener(e -> guardar());
        botonesPanel.add(guardarBtn);
        
        cancelarBtn = new JButton("❌ CANCELAR");
        cancelarBtn.addActionListener(e -> dispose());
        botonesPanel.add(cancelarBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
    }
    
   
    private void cargarCategorias() {
        try {
            List<Categoria> categorias = controller.obtenerTodasCategorias();
            for (Categoria cat : categorias) {
                categoriaCombo.addItem(cat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void cargarDatos() {
        if (producto != null) {
            skuField.setText(producto.getSku());
            skuField.setEditable(false); // No permitir cambiar SKU
            nombreField.setText(producto.getNombre());
            descripcionField.setText(producto.getDescripcion());
            precioField.setText(String.valueOf(producto.getPrecio()));
            
            // Seleccionar categoría
            if (producto.getCategoria() != null) {
                for (int i = 0; i < categoriaCombo.getItemCount(); i++) {
                    Categoria cat = categoriaCombo.getItemAt(i);
                    if (cat.getId() == producto.getCategoria().getId()) {
                        categoriaCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        
        if (inventario != null) {
            cantidadField.setText(String.valueOf(inventario.getCantidad()));
            minimoField.setText(String.valueOf(inventario.getMinimo()));
            maximoField.setText(String.valueOf(inventario.getMaximo()));
        }
    }
    
    private void guardar() {
        try {
            // Validar campos
            if (!validarCampos()) return;
            
            // Crear o actualizar producto
            Producto prod = producto != null ? producto : Producto.builder().build();
            
            String sku = skuField.getText().trim();
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            double precio = Double.parseDouble(precioField.getText().trim());
            Categoria categoria = (Categoria) categoriaCombo.getSelectedItem();
            
            prod = Producto.builder()
                    .id(producto != null ? producto.getId() : 0)
                    .sku(sku)
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .precio(precio)
                    .categoria(categoria)
                    .categoriaId(categoria.getId())
                    .build();
            
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            int minimo = Integer.parseInt(minimoField.getText().trim());
            int maximo = Integer.parseInt(maximoField.getText().trim());
            
            boolean exito;
            if (producto == null) {
                // Nuevo producto
                exito = controller.agregarProductoConInventario(prod, cantidad, minimo, maximo);
            } else {
                // Actualizar existente
                exito = controller.actualizarProductoConInventario(prod, cantidad, minimo, maximo);
            }
            
            if (exito) {
                confirmado = true;
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique que los números sean válidos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean validarCampos() {
        if (skuField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El SKU es obligatorio");
            return false;
        }
        if (nombreField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        if (categoriaCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría");
            return false;
        }
        
        try {
            double precio = Double.parseDouble(precioField.getText().trim());
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
            return false;
        }
        
        try {
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            int minimo = Integer.parseInt(minimoField.getText().trim());
            int maximo = Integer.parseInt(maximoField.getText().trim());
            
            if (cantidad < 0 || minimo < 0 || maximo < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Las cantidades deben ser números válidos");
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
}