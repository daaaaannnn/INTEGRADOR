package gestionpracticas.vista.coordinador;

import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CoordinadorDashboard extends JFrame {

    private final Usuario usuarioActual;

    public CoordinadorDashboard(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dashboard Coordinador - PROYECTOJD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(39, 174, 96));
        header.setPreferredSize(new Dimension(800, 60));
        JLabel lblTitulo = new JLabel("  Panel del Coordinador", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        JLabel lblUser = new JLabel("Bienvenido, " + usuarioActual.getNombre() + "  ", SwingConstants.RIGHT);
        lblUser.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);
        header.add(lblUser, BorderLayout.EAST);

        // Botones
        JPanel botones = new JPanel(new GridLayout(2, 2, 20, 20));
        botones.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        botones.setBackground(new Color(245, 247, 250));

        JButton btnGrupos     = crearBoton("👥  Gestionar Grupos",      new Color(52, 152, 219));
        JButton btnAsignacion = crearBoton("📌  Asignación Docentes",   new Color(230, 126, 34));
        JButton btnMatriculas = crearBoton("📋  Ver Matrículas",        new Color(155, 89, 182));
        JButton btnCerrar     = crearBoton("🚪  Cerrar Sesión",         new Color(192, 57, 43));

        botones.add(btnGrupos);
        botones.add(btnAsignacion);
        botones.add(btnMatriculas);
        botones.add(btnCerrar);

        panel.add(header, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        add(panel);

        btnGrupos.addActionListener((ActionEvent e) -> {
        });
        btnAsignacion.addActionListener(e -> new AsignacionForm(usuarioActual).setVisible(true));
        btnCerrar.addActionListener((ActionEvent e) -> {
            CoordinadorDashboard.this.dispose();
            new com.gestionpracticas.vista.LoginForm().setVisible(true);
        });
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color); btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}