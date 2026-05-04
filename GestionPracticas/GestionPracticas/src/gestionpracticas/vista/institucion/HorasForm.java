package gestionpracticas.vista.institucion;

import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class HorasForm extends JFrame {
    
    private final Usuario usuarioActual;
    
    public HorasForm(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Gestión de Horas de Práctica");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Gestión de Horas de Práctica", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        // Tabla temporal para mostrar actividades
        String[] columnas = {"ID", "Estudiante", "Fecha", "Descripción", "Horas", "Estado"};
        JTable tabla = new JTable(new Object[][]{}, columnas);
        JScrollPane scroll = new JScrollPane(tabla);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAprobar = new JButton("Aprobar");
        JButton btnRechazar = new JButton("Rechazar");
        JButton btnRefrescar = new JButton("Refrescar");
        
        btnAprobar.setBackground(new Color(39, 174, 96));
        btnAprobar.setForeground(Color.WHITE);
        btnRechazar.setBackground(new Color(192, 57, 43));
        btnRechazar.setForeground(Color.WHITE);
        
        panelBotones.add(btnAprobar);
        panelBotones.add(btnRechazar);
        panelBotones.add(btnRefrescar);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        add(panel);
        
        // Acciones temporales
        btnAprobar.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo"));
        btnRechazar.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo"));
        btnRefrescar.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo"));
    }
}