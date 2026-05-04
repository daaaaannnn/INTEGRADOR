package gestionpracticas.vista.institucion;

import com.gestionpracticas.vista.LoginForm;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class InstitucionDashboard extends JFrame {

    private final Usuario usuarioActual;

    public InstitucionDashboard(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dashboard Institución - PROYECTOJD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 480);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(22, 160, 133));
        header.setPreferredSize(new Dimension(750, 60));
        
        JLabel lblTitulo = new JLabel("  Panel de Institución", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblUser = new JLabel("Bienvenido " + usuarioActual.getNombre() + " " + usuarioActual.getApellido(), SwingConstants.RIGHT);
        lblUser.setForeground(Color.WHITE);
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        
        header.add(lblTitulo, BorderLayout.WEST);
        header.add(lblUser, BorderLayout.EAST);

        JPanel botones = new JPanel(new GridLayout(2, 2, 20, 20));  // CORREGIDO: 2x2 para más opciones
        botones.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        botones.setBackground(new Color(245, 247, 250));

        JButton btnHoras = crearBoton("⏱ Gestionar Horas de Práctica", new Color(22, 160, 133));
        JButton btnReportes = crearBoton("📊 Ver Reportes", new Color(52, 152, 219));
        JButton btnPerfil = crearBoton("👤 Mi Perfil", new Color(155, 89, 182));
        JButton btnCerrar = crearBoton("🚪 Cerrar Sesión", new Color(192, 57, 43));

        botones.add(btnHoras);
        botones.add(btnReportes);
        botones.add(btnPerfil);
        botones.add(btnCerrar);

        panel.add(header, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        add(panel);

        // Acciones de los botones
        btnHoras.addActionListener(e -> {
            HorasForm horasForm = new HorasForm(usuarioActual);
            horasForm.setVisible(true);
        });
        
        btnReportes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Módulo de reportes en desarrollo", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnPerfil.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Nombre: " + usuarioActual.getNombre() + " " + usuarioActual.getApellido() + "\n" +
                "Email: " + usuarioActual.getEmail() + "\n" +
                "Tipo: " + usuarioActual.getTipoUsuario(),
                "Mi Perfil", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCerrar.addActionListener(e -> {
            this.dispose();
            new LoginForm().setVisible(true);  // CORREGIDO: Sin paquete completo
        });
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        return btn;
    }
}