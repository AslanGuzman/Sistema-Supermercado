package com.supermarket.view.ajustes;

import com.supermarket.controller.ConfiguracionController;
import com.supermarket.model.Usuario;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AjustesView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConfiguracionController controller;
    private Usuario usuario;
    private JComboBox<String> temaCombo;
    private JTextField backupPathField;
    private JButton cambiarTemaBtn, seleccionarRutaBtn, crearBackupBtn;
    
    public AjustesView(Usuario usuario) {
        this.usuario = usuario;
        try {
            this.controller = new ConfiguracionController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        inicializarUI();
    }
    
    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 13));
        tabs.setBackground(Color.WHITE);
        
        tabs.addTab(" 🎨 Apariencia ", crearPanelApariencia());
        tabs.addTab(" 💾 Backup ", crearPanelBackup());
        tabs.addTab(" ℹ️ Acerca de ", crearPanelAcercaDe());
        
        add(tabs, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelApariencia() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Tema de la Aplicación");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        GridBagConstraints gbc2 = new GridBagConstraints();
        panel.add(titulo, gbc2);
        
        // Descripción
        gbc.gridy = 1;
        JLabel desc = new JLabel("<html>Seleccione el tema visual de la aplicación.<br>" +
                                "Los cambios se aplicarán al reiniciar.</html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 11));
        GridBagConstraints gbc3 = new GridBagConstraints();
        panel.add(desc, gbc3);
        
        // Selector de tema
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridy = 2; gbc.gridwidth = 1;
        panel.add(new JLabel("Tema:"), gbc4);
        
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 1;
        temaCombo = new JComboBox<>(new String[]{"Claro", "Oscuro"});
        temaCombo.setPreferredSize(new Dimension(200, 30));
        
        // Cargar tema actual
        String temaActual = controller.obtenerConfiguracion("theme");
        if ("dark".equals(temaActual)) {
            temaCombo.setSelectedIndex(1);
        }
        GridBagConstraints gbc6 = new GridBagConstraints();
        
        panel.add(temaCombo, gbc6);
        
        // Botón cambiar tema
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.gridy = 3; gbc7.gridx = 0; gbc7.gridwidth = 2;
        gbc7.anchor = GridBagConstraints.CENTER;
        cambiarTemaBtn = new JButton("💡 APLICAR TEMA");
        cambiarTemaBtn.setBackground(new Color(33, 150, 243));
        cambiarTemaBtn.setForeground(Color.WHITE);
        cambiarTemaBtn.setFont(new Font("Arial", Font.BOLD, 12));
        cambiarTemaBtn.addActionListener(e -> cambiarTema());
        panel.add(cambiarTemaBtn, gbc7);
        
        return panel;
    }
    
    private JPanel crearPanelBackup() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.insets = new Insets(10, 10, 10, 10);
        gbc8.anchor = GridBagConstraints.WEST;
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.gridx = 0; gbc9.gridy = 0; gbc9.gridwidth = 3;
        JLabel titulo = new JLabel("Respaldo de la Base de Datos");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, gbc9);
        
        // Descripción
        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.gridy = 1;
        JLabel desc = new JLabel("<html>Cree copias de seguridad de la base de datos.<br>" +
                                "Se guardará un archivo SQL con todos los datos.</html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(desc, gbc10);
        
        // Ruta de backup
        GridBagConstraints gbc11 = new GridBagConstraints();
        gbc11.gridy = 2; gbc11.gridwidth = 1;
        panel.add(new JLabel("Ruta de respaldo:"), gbc11);
        
        GridBagConstraints gbc12 = new GridBagConstraints();
        gbc12.gridx = 1; gbc12.gridwidth = 1;
        gbc12.weightx = 1.0;
        backupPathField = new JTextField();
        backupPathField.setEditable(false);
        
        String rutaBackup = controller.obtenerConfiguracion("backup_path");
        backupPathField.setText(rutaBackup != null ? rutaBackup : "C:/supermarket/backups/");
        
        panel.add(backupPathField, gbc12);
        
        GridBagConstraints gbc13 = new GridBagConstraints();
        gbc13.gridx = 2; gbc13.weightx = 0;
        seleccionarRutaBtn = new JButton("📁");
        seleccionarRutaBtn.addActionListener(e -> seleccionarRuta());
        panel.add(seleccionarRutaBtn, gbc13);
        
        // Botón crear backup
        GridBagConstraints gbc14 = new GridBagConstraints();
        gbc14.gridy = 3; gbc14.gridx = 0; gbc14.gridwidth = 3;
        gbc14.anchor = GridBagConstraints.CENTER;
        crearBackupBtn = new JButton("💾 CREAR BACKUP");
        crearBackupBtn.setBackground(new Color(76, 175, 80));
        crearBackupBtn.setForeground(Color.WHITE);
        crearBackupBtn.setFont(new Font("Arial", Font.BOLD, 12));
        crearBackupBtn.addActionListener(e -> crearBackup());
        panel.add(crearBackupBtn, gbc14);
        
        // Información
        GridBagConstraints gbc15 = new GridBagConstraints();
        gbc15.gridy = 4;
        gbc15.anchor = GridBagConstraints.WEST;
        JLabel info = new JLabel("<html><i>Nota: El backup se creará como archivo SQL.<br>" +
                                "Para restaurar, ejecute el archivo en MySQL.</i></html>");
        info.setFont(new Font("Arial", Font.ITALIC, 10));
        panel.add(info, gbc15);
        
        return panel;
    }
    
    private JPanel crearPanelAcercaDe() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc16 = new GridBagConstraints();
        gbc16.insets = new Insets(10, 10, 10, 10);
        gbc16.anchor = GridBagConstraints.CENTER;
        gbc16.gridx = 0;
        
        // Logo o título
        gbc16.gridy = 0;
        JLabel logo = new JLabel("🏪");
        logo.setFont(new Font("Arial", Font.PLAIN, 48));
        panel.add(logo, gbc16);
        
        // Título
        GridBagConstraints gbc17 = new GridBagConstraints();
        gbc17.gridy = 1;
        JLabel titulo = new JLabel("Sistema de Supermercado");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, gbc17);
        
        // Versión
        GridBagConstraints gbc18 = new GridBagConstraints();
        gbc18.gridy = 2;
        JLabel version = new JLabel("Versión 1.0.0");
        version.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(version, gbc18);
        
        // Info
        GridBagConstraints gbc19 = new GridBagConstraints();
        gbc19.gridy = 3;
        JLabel info = new JLabel("<html><center>" +
                "Sistema de gestión para supermercados<br>" +
                "Incluye facturación, inventario y reportes<br><br>" +
                "© 2025 - Todos los derechos reservados" +
                "</center></html>");
        info.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(info, gbc19);
        
        return panel;
    }
    
    private void cambiarTema() {
        try {
            String tema = temaCombo.getSelectedIndex() == 0 ? "light" : "dark";
            
            // Guardar en configuración
            controller.actualizarConfiguracion("theme", tema);
            
            // Aplicar inmediatamente
            if ("light".equals(tema)) {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            
            // Actualizar todas las ventanas
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(this));
            
            JOptionPane.showMessageDialog(this, 
                "Tema aplicado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cambiar tema: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void seleccionarRuta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File folder = fileChooser.getSelectedFile();
            String ruta = folder.getAbsolutePath() + File.separator;
            backupPathField.setText(ruta);
            
            // Guardar en configuración
            controller.actualizarConfiguracion("backup_path", ruta);
        }
    }
    
    private void crearBackup() {
        try {
            String ruta = backupPathField.getText();
            
            // Crear carpeta si no existe
            File folder = new File(ruta);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            
            // Nombre del archivo con fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String fecha = LocalDateTime.now().format(formatter);
            String nombreArchivo = "backup_" + fecha + ".sql";
            String rutaCompleta = ruta + nombreArchivo;
            
            // Ejecutar backup (necesitas mysqldump instalado)
            String comando = String.format(
                "mysqldump -u root -p1234 supermarket_db > \"%s\"",
                rutaCompleta
            );
            
            Process process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", comando});
            process.waitFor();
            
            if (process.exitValue() == 0) {
                // Registrar backup
                controller.registrarBackup(usuario.getId(), rutaCompleta);
                
                JOptionPane.showMessageDialog(this, 
                    "Backup creado exitosamente:\n" + rutaCompleta, 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al crear backup. Verifique que mysqldump esté instalado.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear backup: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}