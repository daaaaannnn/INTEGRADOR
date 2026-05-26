package com.gestionpracticas.vista.coordinador;

import com.gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class GrupoForm extends JFrame {
    
    private final Usuario usuario;
    
    public GrupoForm(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Gestión de Grupos - " + usuario.getNombre());
        setSize(800, 500);
        setLocationRelativeTo(null);
        com.gestionpracticas.util.PantallaUtil.instalarMenuResolucion(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("👥 Módulo de Gestión de Grupos", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);
        
        JLabel info = new JLabel("Próximamente: Crear, editar y gestionar grupos", SwingConstants.CENTER);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(Color.GRAY);
        panel.add(info, BorderLayout.SOUTH);
        
        add(panel);
    }
}