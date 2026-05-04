package gestionpracticas.vista.institucion;

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
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20)); lblTitulo.setForeground(Color.WHITE);
        JLabel lblUser = new JLabel("Bienvenido  ", SwingConstants.RIGHT);
        lblUser.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST); header.add(lblUser, BorderLayout.EAST);

        JPanel botones = new JPanel(new GridLayout(1, 2, 20, 20));
        botones.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        botones.setBackground(new Color(245, 247, 250));

        JButton btnHoras  = crearBoton("⏱  Gestionar Horas de Práctica", new Color(22, 160, 133));
        JButton btnCerrar = crearBoton("🚪  Cerrar Sesión",               new Color(192, 57, 43));

        botones.add(btnHoras); botones.add(btnCerrar);

        panel.add(header, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        add(panel);

        btnHoras.addActionListener(e -> new HorasForm(usuarioActual).setVisible(true));
        btnCerrar.addActionListener(e -> {
            this.dispose();
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