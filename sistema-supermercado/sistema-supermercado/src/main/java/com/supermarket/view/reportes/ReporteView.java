package com.supermarket.view.reportes;

import com.supermarket.controller.ReporteController;
import com.supermarket.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReporteController controller;
    private JTabbedPane tabbedPane;
    
    public ReporteView() {
        try {
            this.controller = new ReporteController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Pestañas
        tabbedPane.addTab("💰 Cierres de Caja", crearPanelCierresCaja());
        tabbedPane.addTab("📄 Facturas", crearPanelFacturas());
        tabbedPane.addTab("📊 Resumen", crearPanelResumen());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelCierresCaja() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Panel filtros
        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        filtrosPanel.add(new JLabel("Desde:"));
        JTextField desdeField = new JTextField(10);
        desdeField.setText(LocalDate.now().minusDays(7).toString());
        filtrosPanel.add(desdeField);
        
        filtrosPanel.add(new JLabel("Hasta:"));
        JTextField hastaField = new JTextField(10);
        hastaField.setText(LocalDate.now().toString());
        filtrosPanel.add(hastaField);
        
        JButton buscarBtn = new JButton("🔍 Buscar");
        buscarBtn.addActionListener(e -> {
            // Cargar cierres filtrados
            cargarCierresCaja((DefaultTableModel) 
                ((JTable) ((JScrollPane) panel.getComponent(1)).getViewport().getView()).getModel());
        });
        filtrosPanel.add(buscarBtn);
        
        panel.add(filtrosPanel, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Usuario", "Apertura", "Cierre", "Monto Inicial", 
                            "Monto Final", "Diferencia", "Estado"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(tableModel);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton exportarBtn = new JButton("📥 Exportar PDF");
        exportarBtn.setBackground(new Color(231, 76, 60));
        exportarBtn.setForeground(Color.WHITE);
        exportarBtn.addActionListener(e -> exportarCierresPDF());
        botonesPanel.add(exportarBtn);
        
        panel.add(botonesPanel, BorderLayout.SOUTH);
        
        // Cargar datos
        cargarCierresCaja(tableModel);
        
        return panel;
    }
    
    private void cargarCierresCaja(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Caja> cajas = controller.obtenerTodasCajas();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Caja c : cajas) {
            double diferencia = c.getMontoFinal() != null ? 
                c.getMontoFinal() - c.getMontoInicial() : 0;
            
            tableModel.addRow(new Object[]{
                c.getId(),
                "Usuario #" + c.getUsuario().getId(),
                c.getFechaApertura().format(formatter),
                c.getFechaCierre() != null ? c.getFechaCierre().format(formatter) : "N/A",
                String.format("$%.2f", c.getMontoInicial()),
                c.getMontoFinal() != null ? String.format("$%.2f", c.getMontoFinal()) : "N/A",
                String.format("$%.2f", diferencia),
                c.getEstado()
            });
        }
    }
    
    private JPanel crearPanelFacturas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Panel filtros
        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        filtrosPanel.add(new JLabel("Desde:"));
        JTextField desdeField = new JTextField(10);
        desdeField.setText(LocalDate.now().minusDays(7).toString());
        filtrosPanel.add(desdeField);
        
        filtrosPanel.add(new JLabel("Hasta:"));
        JTextField hastaField = new JTextField(10);
        hastaField.setText(LocalDate.now().toString());
        filtrosPanel.add(hastaField);
        
        JButton buscarBtn = new JButton("🔍 Buscar");
        buscarBtn.addActionListener(e -> {
            cargarFacturas((DefaultTableModel) 
                ((JTable) ((JScrollPane) panel.getComponent(1)).getViewport().getView()).getModel());
        });
        filtrosPanel.add(buscarBtn);
        
        panel.add(filtrosPanel, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Fecha", "Cliente", "Usuario", "Total", 
                            "Método Pago", "Membresía"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(tableModel);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton verDetalleBtn = new JButton("👁️ Ver Detalle");
        verDetalleBtn.setBackground(new Color(52, 152, 219));
        verDetalleBtn.setForeground(Color.WHITE);
        verDetalleBtn.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                verDetalleFactura(id);
            } else {
                JOptionPane.showMessageDialog(panel, "Seleccione una factura");
            }
        });
        botonesPanel.add(verDetalleBtn);
        
        JButton exportarBtn = new JButton("📥 Exportar PDF");
        exportarBtn.setBackground(new Color(231, 76, 60));
        exportarBtn.setForeground(Color.WHITE);
        exportarBtn.addActionListener(e -> exportarFacturasPDF());
        botonesPanel.add(exportarBtn);
        
        panel.add(botonesPanel, BorderLayout.SOUTH);
        
        // Cargar datos
        cargarFacturas(tableModel);
        
        return panel;
    }
    
    private void cargarFacturas(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Factura> facturas = controller.obtenerTodasFacturas();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Factura f : facturas) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getFecha().format(formatter),
                f.getCliente() != null ? f.getCliente().getNombre() : "Sin cliente",
                "Usuario #" + f.getUsuario().getId(),
                String.format("$%.2f", f.getTotal()),
                f.getMetodoPago(),
                f.isMembresiaAplicada() ? "✅ Sí" : "❌ No"
            });
        }
    }
    
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("📊 RESUMEN GENERAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, gbc);
        
        gbc.gridwidth = 1;
        
        // Total ventas
        gbc.gridy = 1;
        double totalVentas = controller.calcularTotalVentas();
        agregarEstadistica(panel, gbc, "💰 Total Ventas:", String.format("$%.2f", totalVentas));
        
        // Cantidad facturas
        gbc.gridy = 2;
        int cantFacturas = controller.obtenerTodasFacturas().size();
        agregarEstadistica(panel, gbc, "📄 Facturas Emitidas:", String.valueOf(cantFacturas));
        
        // Promedio venta
        gbc.gridy = 3;
        double promedio = cantFacturas > 0 ? totalVentas / cantFacturas : 0;
        agregarEstadistica(panel, gbc, "📊 Promedio por Factura:", String.format("$%.2f", promedio));
        
        // Cajas abiertas
        gbc.gridy = 4;
        int cajasAbiertas = controller.contarCajasAbiertas();
        agregarEstadistica(panel, gbc, "🔓 Cajas Abiertas:", String.valueOf(cajasAbiertas));
        
        return panel;
    }
    
    private void agregarEstadistica(JPanel panel, GridBagConstraints gbc, String label, String valor) {
        gbc.gridx = 0;
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLabel, gbc);
        
        gbc.gridx = 1;
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
        lblValor.setForeground(new Color(41, 128, 185));
        panel.add(lblValor, gbc);
    }
    
    private void verDetalleFactura(int facturaId) {
        List<FacturaDetalle> detalles = controller.obtenerDetallesFactura(facturaId);
        
        if (detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay detalles para esta factura");
            return;
        }
        
        // Crear diálogo con tabla de detalles
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Detalle de Factura #" + facturaId, true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        String[] columnas = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        
        for (FacturaDetalle d : detalles) {
            model.addRow(new Object[]{
                "Producto #" + d.getProducto().getId(),
                d.getCantidad(),
                String.format("$%.2f", d.getPrecioUnitario()),
                String.format("$%.2f", d.getSubtotal())
            });
        }
        
        JTable tabla = new JTable(model);
        dialog.add(new JScrollPane(tabla));
        dialog.setVisible(true);
    }
    
    private void exportarCierresPDF() {
        JOptionPane.showMessageDialog(this, 
            "Exportación a PDF en desarrollo.\nUtilice esta función para generar reportes.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportarFacturasPDF() {
        JOptionPane.showMessageDialog(this,
            "Exportación a PDF en desarrollo.\nUtilice esta función para generar reportes.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
}