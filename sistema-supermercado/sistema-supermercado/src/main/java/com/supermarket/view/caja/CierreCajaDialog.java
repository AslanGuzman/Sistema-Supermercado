package com.supermarket.view.caja;

import com.supermarket.controller.CajaController;
import com.supermarket.model.*;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class CierreCajaDialog extends JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CajaController controller;
    private Caja caja;
    private boolean confirmado = false;
    private JTextField montoFinalField;
    
    public CierreCajaDialog(Frame parent, CajaController controller, Caja caja) {
        super(parent, "Cierre de Caja", true);
        this.controller = controller;
        this.caja = caja;
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // No puede cerrar sin cerrar caja
        inicializarUI();
    }
    
    private void inicializarUI() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Título
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("🔒 CIERRE DE CAJA");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, gbc);
        gbc.gridwidth = 1;
        
        // Fecha apertura
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Apertura:"), gbc);
        gbc.gridx = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        panel.add(new JLabel(caja.getFechaApertura().format(formatter)), gbc);
        
        // Monto inicial
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Monto Inicial:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(String.format("$%.2f", caja.getMontoInicial())), gbc);
        
        // Separador
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
        
        // Monto final
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Monto Final:*"), gbc);
        gbc.gridx = 1;
        montoFinalField = new JTextField(15);
        panel.add(montoFinalField, gbc);
        
        // Diferencia (se calcula al escribir)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Diferencia:"), gbc);
        gbc.gridx = 1;
        JLabel diferenciaLabel = new JLabel("$0.00");
        diferenciaLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(diferenciaLabel, gbc);
        
        // Actualizar diferencia al escribir
        montoFinalField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    double montoFinal = Double.parseDouble(montoFinalField.getText().trim());
                    double diferencia = montoFinal - caja.getMontoInicial();
                    diferenciaLabel.setText(String.format("$%.2f", diferencia));
                    diferenciaLabel.setForeground(diferencia >= 0 ? new Color(0, 150, 0) : Color.RED);
                } catch (NumberFormatException e) {
                    diferenciaLabel.setText("$0.00");
                }
            }
        });
        
        add(panel, BorderLayout.CENTER);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton cerrarBtn = new JButton("✅ CERRAR CAJA");
        cerrarBtn.setBackground(new Color(244, 67, 54));
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFont(new Font("Arial", Font.BOLD, 12));
        cerrarBtn.addActionListener(e -> cerrarCaja());
        botonesPanel.add(cerrarBtn);
        
        JButton cancelarBtn = new JButton("❌ CANCELAR");
        cancelarBtn.addActionListener(e -> {
            @SuppressWarnings("unused")
			int confirm = JOptionPane.showConfirmDialog(this,
                "Debe cerrar la caja antes de salir",
                "Advertencia",
                JOptionPane.OK_CANCEL_OPTION);
        });
        botonesPanel.add(cancelarBtn);
        
        add(botonesPanel, BorderLayout.SOUTH);
        
        SwingUtilities.invokeLater(() -> montoFinalField.requestFocus());
    }
    
    private void cerrarCaja() {
        try {
            double montoFinal = Double.parseDouble(montoFinalField.getText().trim());
            
            if (montoFinal < 0) {
                JOptionPane.showMessageDialog(this, "El monto no puede ser negativo");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                String.format("¿Confirmar cierre de caja con $%.2f?", montoFinal),
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.cerrarCaja(caja.getUsuario().getId(), montoFinal)) {
                    confirmado = true;
                    JOptionPane.showMessageDialog(this, "Caja cerrada exitosamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al cerrar caja");
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido");
        }
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
}