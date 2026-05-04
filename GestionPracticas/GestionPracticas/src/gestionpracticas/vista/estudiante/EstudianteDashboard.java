package gestionpracticas.vista.estudiante;

import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EstudianteDashboard extends JFrame {

    private final Usuario usuarioActual;

    public EstudianteDashboard(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dashboard Estudiante - PROYECTOJD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(230, 126, 34));
        header.setPreferredSize(new Dimension(800, 60));
        JLabel lblTitulo = new JLabel("  Panel del Estudiante", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20)); lblTitulo.setForeground(Color.WHITE);
        JLabel lblUser = new JLabel("Bienvenido, " + usuarioActual.getNombre() + "  ", SwingConstants.RIGHT);
        lblUser.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST); header.add(lblUser, BorderLayout.EAST);

        JPanel botones = new JPanel(new GridLayout(2, 2, 20, 20));
        botones.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        botones.setBackground(new Color(245, 247, 250));

        JButton btnActividades  = crearBoton("📝  Registrar Actividad",   new Color(52, 152, 219));
        JButton btnCuestionario = crearBoton("📋  Cuestionario",           new Color(39, 174, 96));
        JButton btnMisPracticas = crearBoton("🎓  Mis Prácticas",          new Color(155, 89, 182));
        JButton btnCerrar       = crearBoton("🚪  Cerrar Sesión",          new Color(192, 57, 43));

        botones.add(btnActividades); botones.add(btnCuestionario);
        botones.add(btnMisPracticas); botones.add(btnCerrar);

        panel.add(header, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        add(panel);

        btnActividades.addActionListener(e  -> new ActividadForm(usuarioActual).setVisible(true));
        btnCuestionario.addActionListener((ActionEvent e) -> {
        });
        btnMisPracticas.addActionListener(e -> mostrarMisPracticas());
        btnCerrar.addActionListener(e -> {
            this.dispose();
            new com.gestionpracticas.vista.LoginForm().setVisible(true);
        });
    }

    private void mostrarMisPracticas() {
        JOptionPane.showMessageDialog(this, "Funcionalidad: Ver prácticas matriculadas del estudiante.");
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