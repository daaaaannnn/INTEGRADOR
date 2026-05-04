/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    
package gestionpracticas.vista.docente;

import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DocenteDashboard extends JFrame {

    private final Usuario usuarioActual;

    public DocenteDashboard(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dashboard Docente - PROYECTOJD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(155, 89, 182));
        header.setPreferredSize(new Dimension(800, 60));
        JLabel lblTitulo = new JLabel("  Panel del Docente", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20)); lblTitulo.setForeground(Color.WHITE);
        JLabel lblUser = new JLabel("Bienvenido, " + usuarioActual.getNombre() + "  ", SwingConstants.RIGHT);
        lblUser.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST); header.add(lblUser, BorderLayout.EAST);

        JPanel botones = new JPanel(new GridLayout(2, 2, 20, 20));
        botones.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        botones.setBackground(new Color(245, 247, 250));

        JButton btnEvaluaciones = crearBoton("📝  Registrar Evaluación", new Color(52, 152, 219));
        JButton btnPreguntas    = crearBoton("❓  Gestionar Preguntas",  new Color(230, 126, 34));
        JButton btnMisRubricas  = crearBoton("📊  Mis Rúbricas",         new Color(39, 174, 96));
        JButton btnCerrar       = crearBoton("🚪  Cerrar Sesión",        new Color(192, 57, 43));

        botones.add(btnEvaluaciones); botones.add(btnPreguntas);
        botones.add(btnMisRubricas);  botones.add(btnCerrar);

        panel.add(header, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        add(panel);

        btnEvaluaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EvaluacionForm(usuarioActual).setVisible(true);
            }
        });
        btnPreguntas.addActionListener((ActionEvent e) -> {
        });
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
