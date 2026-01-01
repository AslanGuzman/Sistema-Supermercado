package com.supermarket.view.facturacion;

import com.supermarket.controller.FacturacionController;
import com.supermarket.controller.InventarioController;
import com.supermarket.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class FacturacionView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacturacionController controller;
    private Usuario usuario;
    
    // Componentes
    private JTextField skuField;
    private JTextField cantidadField;
    private JComboBox<String> metodoPagoCombo;
    private JTable detallesTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JLabel clienteLabel;
    private JButton agregarBtn;
    private JButton eliminarBtn;
    private JButton limpiarBtn;
    private JButton finalizarBtn;
    private JButton exportarPDFBtn;
    
    // Estado de la factura actual
    private Factura facturaActual;
    private double totalActual = 0;
    private Cliente clienteSeleccionado;
    
    public FacturacionView(Usuario usuario) {
        this.usuario = usuario;
        try {
            this.controller = new FacturacionController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar: " + e.getMessage());
        }
        
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Datos de entrada
        add(crearPanelEntrada(), BorderLayout.NORTH);
        
        // Panel central - Tabla de detalles
        add(crearPanelTabla(), BorderLayout.CENTER);
        
        // Panel inferior - Totales y botones
        add(crearPanelInferior(), BorderLayout.SOUTH);
        
        limpiarFactura();
    }
    
    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Nueva Factura"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cliente:"), gbc);
        
        JButton seleccionarClienteBtn = new JButton("Seleccionar Cliente");
        seleccionarClienteBtn.addActionListener(e -> seleccionarCliente());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1;
        panel.add(seleccionarClienteBtn, gbc2);
        
        clienteLabel = new JLabel("Sin cliente seleccionado");
        clienteLabel.setForeground(new Color(100, 100, 100));
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 2;
        panel.add(clienteLabel, gbc3);
        
        // SKU del producto
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 0;
        gbc4.gridy = 1;
        panel.add(new JLabel("SKU Producto:"), gbc4);
        
        skuField = new JTextField(15);
        skuField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    cantidadField.requestFocus();
                }
            }
        });
        GridBagConstraints gbc5 = new GridBagConstraints();

        gbc5.gridx = 1;
        panel.add(skuField, gbc5);
        
        // Cantidad
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.gridx = 2;
        panel.add(new JLabel("Cantidad:"), gbc6);
        
        cantidadField = new JTextField(10);
        cantidadField.setText("1");
        cantidadField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    agregarProducto();
                }
            }
        });
        GridBagConstraints gbc7 = new GridBagConstraints();

        gbc7.gridx = 3;
        panel.add(cantidadField, gbc7);
        
        // Botón agregar
        GridBagConstraints gbc8 = new GridBagConstraints();

        agregarBtn = new JButton("AGREGAR");
        agregarBtn.setBackground(new Color(76, 175, 80));
        agregarBtn.setForeground(Color.WHITE);
        agregarBtn.addActionListener(e -> agregarProducto());
        gbc8.gridx = 4;
        panel.add(agregarBtn, gbc8);
        
        // Método de pago
        GridBagConstraints gbc9 = new GridBagConstraints();

        gbc9.gridx = 0;
        gbc9.gridy = 2;
        panel.add(new JLabel("Método de Pago:"), gbc9);
        
        GridBagConstraints gbc0 = new GridBagConstraints();

        
        metodoPagoCombo = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Cheque"});
        gbc0.gridx = 1;
        panel.add(metodoPagoCombo, gbc0);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Productos"));
        
        // Crear tabla
        String[] columnas = {"SKU", "Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        detallesTable = new JTable(tableModel);
        detallesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        detallesTable.setPreferredScrollableViewportSize(new Dimension(800, 300));
        
        JScrollPane scrollPane = new JScrollPane(detallesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botones de tabla
        JPanel botonesTabla = new JPanel();
        
        eliminarBtn = new JButton("Eliminar Fila");
        eliminarBtn.setBackground(new Color(244, 67, 54));
        eliminarBtn.setForeground(Color.WHITE);
        eliminarBtn.addActionListener(e -> eliminarProducto());
        botonesTabla.add(eliminarBtn);
        
        panel.add(botonesTabla, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel totales
        JPanel totalesPanel = new JPanel();
        totalesPanel.setBorder(BorderFactory.createTitledBorder("Resumen"));
        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalesPanel.add(totalLabel);
        
        panel.add(totalesPanel, BorderLayout.WEST);
        
        // Panel botones
        JPanel botonesPanel = new JPanel();
        
        limpiarBtn = new JButton("LIMPIAR");
        limpiarBtn.setBackground(new Color(158, 158, 158));
        limpiarBtn.setForeground(Color.WHITE);
        limpiarBtn.addActionListener(e -> limpiarFactura());
        botonesPanel.add(limpiarBtn);
        
        exportarPDFBtn = new JButton("EXPORTAR PDF");
        exportarPDFBtn.setBackground(new Color(255, 152, 0));
        exportarPDFBtn.setForeground(Color.WHITE);
        exportarPDFBtn.addActionListener(e -> exportarFacturaPDF());
        botonesPanel.add(exportarPDFBtn);
        
        finalizarBtn = new JButton("FINALIZAR VENTA");
        finalizarBtn.setBackground(new Color(0, 150, 136));
        finalizarBtn.setForeground(Color.WHITE);
        finalizarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        finalizarBtn.addActionListener(e -> finalizarVenta());
        botonesPanel.add(finalizarBtn);
        
        JButton vistaFacturaBtn = new JButton("👁️ VISTA PREVIA");
        vistaFacturaBtn.setBackground(new Color(52, 152, 219));
        vistaFacturaBtn.setForeground(Color.WHITE);
        vistaFacturaBtn.addActionListener(e -> mostrarVistaPrevia());
        panel.add(vistaFacturaBtn);
        
        panel.add(botonesPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void mostrarVistaPrevia() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue productos primero");
            return;
        }
        
        String separador = String.join("", Collections.nCopies(50, "─"));
        
        // Crear diálogo con vista previa
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Vista Previa - Factura", true);
        dialog.setSize(600, 700);
        dialog.setLocationRelativeTo(this);
        
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        StringBuilder factura = new StringBuilder();
        factura.append("╔════════════════════════════════════════╗\n");
        factura.append("║       SUPERMERCADO - FACTURA          ║\n");
        factura.append("╚════════════════════════════════════════╝\n\n");
        factura.append("Fecha: ").append(LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        factura.append("Usuario: ").append(usuario.getUsername()).append("\n\n");
        
        if (clienteSeleccionado != null) {
            factura.append("Cliente: ").append(clienteSeleccionado.getNombre()).append("\n");
            if (clienteSeleccionado.isMembresia()) {
                factura.append("⭐ MEMBRESÍA: Descuento 5%\n");
            }
        }
        
        factura.append("\n").append(separador).append("\n");
        factura.append(String.format("%-20s %8s %10s %10s\n", 
            "PRODUCTO", "CANT", "P.UNIT", "SUBTOTAL"));
        factura.append(separador).append("\n");
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String nombre = tableModel.getValueAt(i, 1).toString();
            if (nombre.length() > 20) nombre = nombre.substring(0, 17) + "...";
            
            factura.append(String.format("%-20s %8s %10s %10s\n",
                nombre,
                tableModel.getValueAt(i, 2),
                tableModel.getValueAt(i, 3),
                tableModel.getValueAt(i, 4)
            ));
        }
        
        factura.append(separador).append("\n");
        factura.append(String.format("%40s: $%.2f\n", "TOTAL", totalActual));
        factura.append(separador).append("\n\n");
        factura.append("Método de Pago: ").append(metodoPagoCombo.getSelectedItem()).append("\n\n");
        factura.append("        ¡Gracias por su compra!\n");
        factura.append("       www.supermercado.com\n");
        
        textArea.setText(factura.toString());
        
        JScrollPane scroll = new JScrollPane(textArea);
        dialog.add(scroll);
        dialog.setVisible(true);
    }
    
    private void seleccionarCliente() {
        // Aquí iría un diálogo para seleccionar cliente
        // Por ahora es simplificado
        String cedula = JOptionPane.showInputDialog(this, "Ingrese cédula del cliente (opcional):");
        if (cedula != null && !cedula.isEmpty()) {
            clienteSeleccionado = controller.buscarClientePorCedula(cedula);
            if (clienteSeleccionado != null) {
                clienteLabel.setText(clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellido());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        }
    }
    
    private void agregarProducto() {
        String sku = skuField.getText().trim();
        String cantidadStr = cantidadField.getText().trim();
        
        if (sku.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();
            
            // Buscar producto
            Producto producto = controller.buscarProductoPorSku(sku);
            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
                return;
            }
            
            // Verificar inventario
            if (!new InventarioController().hayStock(producto.getId(), cantidad)) {
                JOptionPane.showMessageDialog(this, "Cantidad insuficiente en inventario");
                return;
            }
            
            // Calcular precio (con descuento si es miembro)
            double precioUnitario = producto.getPrecio();
            if (clienteSeleccionado != null && clienteSeleccionado.isMembresia()) {
                precioUnitario *= 0.95; // 5% de descuento
            }
            
            double subtotal = precioUnitario * cantidad;
            
            // Agregar a la tabla
            tableModel.addRow(new Object[]{
                producto.getSku(),
                producto.getNombre(),
                cantidad,
                String.format("$%.2f", precioUnitario),
                String.format("$%.2f", subtotal)
            });
            
            totalActual += subtotal;
            actualizarTotal();
            
            skuField.setText("");
            cantidadField.setText("1");
            skuField.requestFocus();
            
        } catch (NumberFormatException | HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }
    
    private void eliminarProducto() {
        int row = detallesTable.getSelectedRow();
        if (row != -1) {
            double subtotal = Double.parseDouble(
                tableModel.getValueAt(row, 4).toString().replace("$", "")
            );
            totalActual -= subtotal;
            tableModel.removeRow(row);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar");
        }
    }
    
    private void limpiarFactura() {
        tableModel.setRowCount(0);
        totalActual = 0;
        clienteSeleccionado = null;
        clienteLabel.setText("Sin cliente seleccionado");
        skuField.setText("");
        cantidadField.setText("1");
        actualizarTotal();
    }
    
    private void actualizarTotal() {
        totalLabel.setText(String.format("Total: $%.2f", totalActual));
    }
    
    private void exportarFacturaPDF() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la factura");
            return;
        }
        
        // Aquí iría la lógica de generación de PDF
        JOptionPane.showMessageDialog(this, "Función de PDF en desarrollo");
    }
    
    private void finalizarVenta() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto");
            return;
        }
        
        try {
            String metodoPago = (String) metodoPagoCombo.getSelectedItem();
            
            // Crear factura
            Factura factura = Factura.builder()
                    .cliente(clienteSeleccionado)
                    .usuario(usuario)
                    .fecha(LocalDateTime.now())
                    .total(totalActual)
                    .metodoPago(metodoPago)
                    .membresiaAplicada(clienteSeleccionado != null && clienteSeleccionado.isMembresia())
                    .build();
            
            boolean guardada = controller.guardarFactura(factura);
            
            if (guardada) {
                JOptionPane.showMessageDialog(this, "Venta realizada exitosamente");
                limpiarFactura();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la factura");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

	public Factura getFacturaActual() {
		return facturaActual;
	}

	public void setFacturaActual(Factura facturaActual) {
		this.facturaActual = facturaActual;
	}
}