package com.gestionpracticas.vista.estudiante;

import com.gestionpracticas.modelo.Usuario;
import com.gestionpracticas.util.DBHelper;
import com.gestionpracticas.util.SemillasProyecto;
import com.gestionpracticas.vista.LoginForm;
import com.gestionpracticas.vista.comun.CrudTablaFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EstudianteDashboard extends JFrame {

    private final Usuario usuario;

    public EstudianteDashboard(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(230, 42));
        return boton;
    }

    private JPanel crearCard(String titulo, String descripcion) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(215, 215, 215)), new EmptyBorder(18, 18, 18, 18)));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 97, 140));
        JTextArea txt = new JTextArea(descripcion);
        txt.setEditable(false); txt.setLineWrap(true); txt.setWrapStyleWord(true); txt.setOpaque(false);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13)); txt.setForeground(new Color(70, 70, 70));
        card.add(lblTitulo, BorderLayout.NORTH); card.add(txt, BorderLayout.CENTER); return card;
    }

    private void initComponents() {
        setTitle("Dashboard Estudiante - " + usuario.getNombre());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        com.gestionpracticas.util.PantallaUtil.instalarMenuResolucion(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout()); principal.setBackground(new Color(245, 247, 250));
        JPanel menu = new JPanel(new GridLayout(11, 1, 8, 8));
        menu.setPreferredSize(new Dimension(265, 0)); menu.setBackground(new Color(27, 38, 59)); menu.setBorder(new EmptyBorder(25, 20, 25, 20));
        JLabel tituloMenu = new JLabel("PROYECTOJD"); tituloMenu.setForeground(Color.WHITE); tituloMenu.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JButton btnDashboard = crearBoton("Dashboard");
        JButton btnActividad = crearBoton("Registrar Actividad/Horas");
        JButton btnCuestionario = crearBoton("Responder Cuestionario");
        JButton btnReporte = crearBoton("Reportar Avance");
        JButton btnCalificacion = crearBoton("Consultar Calificación");
        JButton btnDatosBase = crearBoton("Crear Matrícula Prueba");
        JButton btnCerrar = crearBoton("Cerrar Sesión");
        btnActividad.addActionListener(e -> new ActividadForm(usuario).setVisible(true));
        btnCuestionario.addActionListener(e -> new CuestionarioForm(usuario).setVisible(true));
        btnReporte.addActionListener(e -> new CrudTablaFrame("Reportes del Estudiante", "REPORTE", "ID_REPORTE", new String[]{"ID_REPORTE","ID_USUARIO","ID_ESTUDIANTE","TITULO","NOMBRE","DESCRIPCION","DETALLE","COMENTARIOS","ESTADO","FECHA","FECHA_GENERACION"}).setVisible(true));
        btnCalificacion.addActionListener(e -> new CrudTablaFrame("Consulta de Evaluación", "EVALUACION", "ID_EVALUACION", new String[]{"ID_EVALUACION","ID_MATRICULA_PRACTICA","ID_RUBRICA","ID_EVALUADOR","PUNTAJE_OBTENIDO","NOTA_EVALUACION","OBSERVACIONES","FECHA_EVALUACION","ESTADO"}).setVisible(true));
        btnDatosBase.addActionListener(e -> JOptionPane.showMessageDialog(this, SemillasProyecto.asegurarDatosBase(usuario), "Matrícula y datos de prueba", JOptionPane.INFORMATION_MESSAGE));
        btnCerrar.addActionListener(e -> { dispose(); new LoginForm().setVisible(true); });
        menu.add(tituloMenu); menu.add(btnDashboard); menu.add(btnActividad); menu.add(btnCuestionario); menu.add(btnReporte); menu.add(btnCalificacion); menu.add(btnDatosBase); menu.add(new JLabel("")); menu.add(new JLabel("")); menu.add(new JLabel("")); menu.add(btnCerrar);

        JPanel contenido = new JPanel(new BorderLayout(20, 20)); contenido.setOpaque(false);
        JPanel header = new JPanel(new BorderLayout()); header.setBackground(new Color(41, 128, 185)); header.setBorder(new EmptyBorder(20, 25, 20, 25));
        JLabel bienvenida = new JLabel("Bienvenido estudiante: " + usuario.getNombre() + " " + usuario.getApellido());
        bienvenida.setForeground(Color.WHITE); bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 26));
        JLabel subtitulo = new JLabel("Seguimiento de práctica: actividades, horas, evidencias, cuestionarios y retroalimentación");
        subtitulo.setForeground(Color.WHITE); subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPanel textos = new JPanel(new GridLayout(2, 1)); textos.setOpaque(false); textos.add(bienvenida); textos.add(subtitulo); header.add(textos, BorderLayout.WEST);

        JPanel stats = new JPanel(new GridLayout(1, 4, 20, 20)); stats.setOpaque(false); stats.setBorder(new EmptyBorder(25, 30, 5, 30));
        int matriculas = DBHelper.contarWhere("MATRICULA_PRACTICA", "ID_ESTUDIANTE = " + usuario.getIdUsuario() + " OR ID_USUARIO = " + usuario.getIdUsuario());
        int reportes = DBHelper.contar("REPORTE");
        int respuestas = DBHelper.contarWhere("RESPUESTA", "ID_USUARIO = " + usuario.getIdUsuario() + " OR ID_ESTUDIANTE = " + usuario.getIdUsuario());
        int actividades = DBHelper.contar("REGISTRO_ACTIVIDAD");
        stats.add(crearCard("Mi matrícula", matriculas + " registro(s) activo(s)"));
        stats.add(crearCard("Actividades", actividades + " registros del sistema"));
        stats.add(crearCard("Respuestas", respuestas + " respuestas registradas"));
        stats.add(crearCard("Reportes", reportes + " reportes disponibles"));

        JPanel tarjetas = new JPanel(new GridLayout(2, 2, 20, 20)); tarjetas.setOpaque(false); tarjetas.setBorder(new EmptyBorder(10, 30, 30, 30));
        tarjetas.add(crearCard("Registrar actividades y horas", "Registra las actividades pedagógicas realizadas durante la práctica, indicando descripción, tipo y horas invertidas. Si no aparece una práctica, usa Crear Matrícula Prueba."));
        tarjetas.add(crearCard("Responder evaluación", "Responde las preguntas configuradas por el docente asesor y deja evidencia del proceso formativo."));
        tarjetas.add(crearCard("Curso y carrera", "La práctica se vincula al programa, curso, grupo e institución receptora para mantener trazabilidad académica."));
        tarjetas.add(crearCard("Calificación", "Consulta la nota final, la observación general y la retroalimentación del docente asesor."));
        contenido.add(header, BorderLayout.NORTH); contenido.add(stats, BorderLayout.CENTER); contenido.add(tarjetas, BorderLayout.SOUTH);
        principal.add(menu, BorderLayout.WEST); principal.add(contenido, BorderLayout.CENTER); setContentPane(principal);
    }
}
