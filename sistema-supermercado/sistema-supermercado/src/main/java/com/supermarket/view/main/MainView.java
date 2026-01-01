package com.supermarket.view.main;

import com.supermarket.model.Usuario;
import com.supermarket.controller.CajaController;
import com.supermarket.model.Caja;
import com.supermarket.view.login.LoginView;
import com.supermarket.view.caja.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainView extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuarioActual;
    private JPanel contentPanel;
    private CajaController cajaController;
    private boolean esCajero;
    private Map<String, JButton> botonesMenu;
    private String vistaActual = "";
    
    // Colores del tema
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_SECUNDARIO = new Color(52, 73, 94);
    private static final Color COLOR_SIDEBAR = new Color(44, 62, 80);
    private static final Color COLOR_BOTON_NORMAL = new Color(52, 73, 94);
    private static final Color COLOR_BOTON_ACTIVO = new Color(41, 128, 185);
    private static final Color COLOR_BOTON_HOVER = new Color(70, 90, 110);
    
    public MainView(Usuario usuario) {
        this.usuarioActual = usuario;
        this.esCajero = "CAJERO".equals(usuario.getRol().getNombre());
        this.botonesMenu = new HashMap<>();
        
        try {
            this.cajaController = new CajaController();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        inicializarUI();
        
        // Si es cajero, verificar apertura de caja
        if (esCajero) {
            verificarAperturaCaja();
        }
    }
    
    private void verificarAperturaCaja() {
        SwingUtilities.invokeLater(() -> {
            if (!cajaController.usuarioTieneCajaAbierta(usuarioActual.getId())) {
                AperturaCajaDialog dialog = new AperturaCajaDialog(this, cajaController, usuarioActual);
                dialog.setVisible(true);
                
                if (!dialog.isConfirmado()) {
                    JOptionPane.showMessageDialog(this,
                        "Debe abrir caja para continuar",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                    cerrarSesion();
                }
            }
        });
    }
    
    private void inicializarUI() {
        setTitle("Sistema Supermercado - " + usuarioActual.getUsername());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // PANTALLA COMPLETA
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Listener de cierre personalizado
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                intentarCerrar();
            }
        });
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Top bar moderno
        JPanel topBar = crearTopBar();
        mainPanel.add(topBar, BorderLayout.NORTH);
        
        // Side panel con diseño mejorado
        JPanel sidePanel = crearPanelLateral();
        mainPanel.add(sidePanel, BorderLayout.WEST);
        
        // Content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(236, 240, 241));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de bienvenida inicial
        contentPanel.add(crearPanelBienvenida(), BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        
        // Icono
        JLabel iconLabel = new JLabel("🏪");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(iconLabel);
        
        contenido.add(Box.createVerticalStrut(20));
        
        // Bienvenida
        JLabel welcomeLabel = new JLabel("¡Bienvenido, " + usuarioActual.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(COLOR_SECUNDARIO);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(welcomeLabel);
        
        contenido.add(Box.createVerticalStrut(10));
        
        // Rol
        JLabel rolLabel = new JLabel("Rol: " + usuarioActual.getRol().getNombre());
        rolLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        rolLabel.setForeground(new Color(127, 140, 141));
        rolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(rolLabel);
        
        contenido.add(Box.createVerticalStrut(30));
        
        // Instrucciones
        JLabel instrLabel = new JLabel("Seleccione una opción del menú lateral");
        instrLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        instrLabel.setForeground(new Color(149, 165, 166));
        instrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(instrLabel);
        
        panel.add(contenido);
        return panel;
    }
    
    private JPanel crearTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(COLOR_SECUNDARIO);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Panel izquierdo con logo y título
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);
        
        JLabel logoLabel = new JLabel("🏪");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        leftPanel.add(logoLabel);
        
        JLabel titleLabel = new JLabel("SISTEMA SUPERMERCADO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(titleLabel);
        
        topBar.add(leftPanel, BorderLayout.WEST);
        
        // Panel derecho con info usuario y botón salir
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);
        
        JLabel infoLabel = new JLabel("👤 " + usuarioActual.getUsername() + " (" + 
                                      usuarioActual.getRol().getNombre() + ")");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(Color.WHITE);
        rightPanel.add(infoLabel);
        
        JButton salirBtn = new JButton(esCajero ? "🔒 CERRAR CAJA Y SALIR" : "🚪 SALIR");
        salirBtn.setBackground(new Color(231, 76, 60));
        salirBtn.setForeground(Color.WHITE);
        salirBtn.setFocusPainted(false);
        salirBtn.setBorderPainted(false);
        salirBtn.setFont(new Font("Arial", Font.BOLD, 11));
        salirBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salirBtn.addActionListener(e -> intentarCerrar());
        
        // Hover effect
        salirBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                salirBtn.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                salirBtn.setBackground(new Color(231, 76, 60));
            }
        });
        
        rightPanel.add(salirBtn);
        topBar.add(rightPanel, BorderLayout.EAST);
        
        return topBar;
    }
    
    private JPanel crearPanelLateral() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(COLOR_SIDEBAR);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidePanel.setPreferredSize(new Dimension(250, 0));
        
        String rol = usuarioActual.getRol().getNombre();
        
        // Título del menú
        JLabel menuTitle = new JLabel("MENÚ PRINCIPAL");
        menuTitle.setFont(new Font("Arial", Font.BOLD, 12));
        menuTitle.setForeground(new Color(189, 195, 199));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(menuTitle);
        sidePanel.add(Box.createVerticalStrut(20));
        
        // Facturación (todos)
        agregarBotonMenu(sidePanel, "facturacion", "📄 Facturación", 
            () -> cargarVista("facturacion", new com.supermarket.view.facturacion.FacturacionView(usuarioActual)));
        
        // Inventario (ADMIN y SUPERVISOR)
        if (rol.equals("ADMIN") || rol.equals("SUPERVISOR")) {
            agregarBotonMenu(sidePanel, "inventario", "📦 Inventario", 
                () -> cargarVista("inventario", new com.supermarket.view.inventario.InventarioView()));
        }
        
        // Clientes (todos)
        agregarBotonMenu(sidePanel, "clientes", "👥 Clientes", 
            () -> cargarVista("clientes", new com.supermarket.view.clientes.ClienteView()));
        
        // Mi Caja (CAJERO puede ver, ADMIN/SUPERVISOR pueden abrir opcional)
        if (esCajero) {
            agregarBotonMenu(sidePanel, "caja", "💰 Mi Caja", () -> verCajaActual());
        } else if (rol.equals("ADMIN") || rol.equals("SUPERVISOR")) {
            agregarBotonMenu(sidePanel, "caja", "💰 Caja", () -> gestionarCaja());
        }
        
        // Empleados (solo ADMIN)
        if (rol.equals("ADMIN")) {
            agregarBotonMenu(sidePanel, "empleados", "👨‍💼 Empleados", 
                () -> cargarVista("empleados", new com.supermarket.view.empleados.EmpleadoView()));
        }
        
        // Reportes (ADMIN y SUPERVISOR)
        if (rol.equals("ADMIN") || rol.equals("SUPERVISOR")) {
            agregarBotonMenu(sidePanel, "reportes", "📈 Reportes", 
                () -> cargarVista("reportes", new com.supermarket.view.reportes.ReporteView()));
        }
        
        sidePanel.add(Box.createVerticalStrut(20));
        
        // Separador
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(230, 1));
        sidePanel.add(sep);
        sidePanel.add(Box.createVerticalStrut(20));
        
        // Ajustes (todos)
        agregarBotonMenu(sidePanel, "ajustes", "⚙️ Ajustes", 
            () -> cargarVista("ajustes", new com.supermarket.view.ajustes.AjustesView(usuarioActual)));
        
        sidePanel.add(Box.createVerticalGlue());
        
        return sidePanel;
    }
    
    private void agregarBotonMenu(JPanel panel, String id, String texto, Runnable accion) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(230, 45));
        btn.setPreferredSize(new Dimension(230, 45));
        btn.setBackground(COLOR_BOTON_NORMAL);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!vistaActual.equals(id)) {
                    btn.setBackground(COLOR_BOTON_HOVER);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!vistaActual.equals(id)) {
                    btn.setBackground(COLOR_BOTON_NORMAL);
                }
            }
        });
        
        btn.addActionListener(e -> accion.run());
        
        botonesMenu.put(id, btn);
        panel.add(btn);
        panel.add(Box.createVerticalStrut(5));
    }
    
    private void cargarVista(String id, JPanel vista) {
        contentPanel.removeAll();
        contentPanel.add(vista, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        
        // Actualizar botón activo
        actualizarBotonActivo(id);
    }
    
    private void actualizarBotonActivo(String idActivo) {
        // Restaurar todos los botones a color normal
        for (Map.Entry<String, JButton> entry : botonesMenu.entrySet()) {
            entry.getValue().setBackground(COLOR_BOTON_NORMAL);
        }
        
        // Marcar el botón activo
        if (botonesMenu.containsKey(idActivo)) {
            botonesMenu.get(idActivo).setBackground(COLOR_BOTON_ACTIVO);
        }
        
        vistaActual = idActivo;
    }
    
    private void gestionarCaja() {
        // Para ADMIN/SUPERVISOR: pueden abrir caja opcional
        if (cajaController.usuarioTieneCajaAbierta(usuarioActual.getId())) {
            verCajaActual();
        } else {
            int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea abrir una caja?",
                "Gestión de Caja",
                JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                AperturaCajaDialog dialog = new AperturaCajaDialog(this, cajaController, usuarioActual);
                dialog.setVisible(true);
            }
        }
    }
    
    private void verCajaActual() {
        Caja caja = cajaController.obtenerCajaAbierta(usuarioActual.getId());
        if (caja == null) {
            JOptionPane.showMessageDialog(this, "No tiene una caja abierta");
        } else {
            String info = String.format(
                "Caja Abierta\n\n" +
                "Apertura: %s\n" +
                "Monto Inicial: $%.2f\n" +
                "Estado: %s",
                caja.getFechaApertura().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                caja.getMontoInicial(),
                caja.getEstado()
            );
            JOptionPane.showMessageDialog(this, info, "Mi Caja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void intentarCerrar() {
        if (esCajero && cajaController.usuarioTieneCajaAbierta(usuarioActual.getId())) {
            Caja caja = cajaController.obtenerCajaAbierta(usuarioActual.getId());
            
            CierreCajaDialog dialog = new CierreCajaDialog(this, cajaController, caja);
            dialog.setVisible(true);
            
            if (dialog.isConfirmado()) {
                cerrarSesion();
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                cerrarSesion();
            }
        }
    }
    
    private void cerrarSesion() {
        // CERRAR ESTE FRAME PRIMERO
        dispose();
        
        // ABRIR NUEVO LOGIN
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}