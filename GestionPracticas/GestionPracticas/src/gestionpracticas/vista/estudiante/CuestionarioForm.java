package com.gestionpracticas.vista.estudiante;

import com.gestionpracticas.dao.PreguntaDAO;
import com.gestionpracticas.logica.MatriculaPracticaLogica;
import com.gestionpracticas.modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CuestionarioForm extends JFrame {

    private Usuario usuarioActual;
    private MatriculaPracticaLogica matriculaLogica;

    private JComboBox<String> cmbMatricula;
    private JPanel panelPreguntas;
    private JButton btnCargar, btnEnviar;
    private List<MatriculaPractica> listaMatriculas;

    public CuestionarioForm(Usuario usuario) {
        this.usuarioActual  = usuario;
        this.matriculaLogica = new MatriculaPracticaLogica();
        initComponents();
        cargarMatriculas();
    }

    private void initComponents() {
        setTitle("Cuestionario de Práctica");
        setSize(700, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel superior
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("Selecciona tu práctica"));
        cmbMatricula = new JComboBox<>();
        btnCargar    = new JButton("🔄 Cargar Cuestionario");
        btnCargar.setBackground(new Color(52,152,219)); btnCargar.setForeground(Color.WHITE); btnCargar.setFocusPainted(false);
        panelTop.add(new JLabel("Práctica:"));
        panelTop.add(cmbMatricula);
        panelTop.add(btnCargar);

        // Panel de preguntas
        panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        panelPreguntas.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        JScrollPane scroll = new JScrollPane(panelPreguntas);

        // Panel inferior
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEnviar = new JButton("📤 Enviar Respuestas");
        btnEnviar.setBackground(new Color(39,174,96)); btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 13)); btnEnviar.setFocusPainted(false);
        panelBottom.add(btnEnviar);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> cargarPreguntas());
        btnEnviar.addActionListener(e -> enviarRespuestas());
    }

    private void cargarMatriculas() {
        listaMatriculas = matriculaLogica.listarPorEstudiante(usuarioActual.getIdUsuario());
        cmbMatricula.removeAllItems();
        for (MatriculaPractica m : listaMatriculas)
            cmbMatricula.addItem("Práctica: " + (m.getPractica() != null ? m.getPractica().getTitulo() : "#" + m.getIdMatricula()));
    }

    private void cargarPreguntas() {
        panelPreguntas.removeAll();
        // Preguntas de ejemplo — se reemplaza con PreguntaDAO.listarPorEvaluacion()
        String[] preguntasEjemplo = {
            "¿Cómo fue tu experiencia en la práctica?",
            "¿Qué habilidades desarrollaste?",
            "¿El tutor te brindó el apoyo necesario?",
            "¿Recomendarías esta institución? (Escala 1-5)",
            "¿Cuál fue tu mayor aprendizaje?"
        };

        for (int i = 0; i < preguntasEjemplo.length; i++) {
            JPanel pregPanel = new JPanel(new BorderLayout(5, 5));
            pregPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 0, 8, 0),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)
            ));
            pregPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

            JLabel lblPregunta = new JLabel("  " + (i+1) + ". " + preguntasEjemplo[i]);
            lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 13));

            JTextField txtRespuesta = new JTextField();
            txtRespuesta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtRespuesta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,180,180)),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
            ));

            pregPanel.add(lblPregunta, BorderLayout.NORTH);
            pregPanel.add(txtRespuesta, BorderLayout.CENTER);
            panelPreguntas.add(pregPanel);
            panelPreguntas.add(Box.createVerticalStrut(4));
        }

        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    private void enviarRespuestas() {
        if (panelPreguntas.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(this, "Primero carga el cuestionario.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
        }
        JOptionPane.showMessageDialog(this, "✅ Respuestas enviadas correctamente. ¡Gracias!", "Enviado", JOptionPane.INFORMATION_MESSAGE);
        panelPreguntas.removeAll(); panelPreguntas.revalidate(); panelPreguntas.repaint();
    }
}