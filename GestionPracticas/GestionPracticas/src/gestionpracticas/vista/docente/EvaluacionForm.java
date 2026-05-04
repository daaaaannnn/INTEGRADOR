package gestionpracticas.vista.docente;

import gestionpracticas.logica.EvaluacionLogica;
import gestionpracticas.logica.MatriculaPracticaLogica;
import gestionpracticas.logica.RubricaLogica;
import gestionpracticas.modelo.Evaluacion;
import gestionpracticas.modelo.MatriculaPractica;
import gestionpracticas.modelo.Rubrica;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EvaluacionForm extends JFrame {

    private final Usuario usuarioActual;
    private final EvaluacionLogica    evaluacionLogica;
    private final MatriculaPracticaLogica matriculaLogica;
    private final RubricaLogica       rubricaLogica;

    private JComboBox<String> cmbMatricula, cmbRubrica;
    private JTextField txtPuntaje, txtObservaciones;
    private JButton btnGuardar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private List<MatriculaPractica> listaMatriculas;
    private List<Rubrica> listaRubricas;

    public EvaluacionForm(Usuario usuario) {
        this.usuarioActual    = usuario;
        this.evaluacionLogica = new EvaluacionLogica();
        this.matriculaLogica  = new MatriculaPracticaLogica();
        this.rubricaLogica    = new RubricaLogica();
        initComponents();
        cargarCombos();
    }

    private void initComponents() {
        setTitle("Registrar Evaluación");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva Evaluación"));
        form.setPreferredSize(new Dimension(320, 520));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(7,8,7,8); gbc.gridx = 0;

        gbc.gridy = 0; form.add(new JLabel("Matrícula del Estudiante:"), gbc);
        gbc.gridy = 1; cmbMatricula = new JComboBox<>(); form.add(cmbMatricula, gbc);
        gbc.gridy = 2; form.add(new JLabel("Rúbrica:"), gbc);
        gbc.gridy = 3; cmbRubrica = new JComboBox<>(); form.add(cmbRubrica, gbc);
        gbc.gridy = 4; form.add(new JLabel("Puntaje Obtenido (0-100):"), gbc);
        gbc.gridy = 5; txtPuntaje = new JTextField(); form.add(txtPuntaje, gbc);
        gbc.gridy = 6; form.add(new JLabel("Observaciones:"), gbc);
        gbc.gridy = 7; txtObservaciones = new JTextField(); form.add(txtObservaciones, gbc);

        gbc.gridy = 8;
        btnGuardar = new JButton("💾 Guardar Evaluación");
        btnGuardar.setBackground(new Color(52,152,219)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFocusPainted(false);
        form.add(btnGuardar, gbc);

        String[] cols = {"ID", "Estudiante", "Rúbrica", "Puntaje", "Estado", "Fecha"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(320);
        add(split);

        btnGuardar.addActionListener(e -> guardar());
    }

    private void cargarCombos() {
        listaMatriculas = matriculaLogica.listarPorEstudiante(0); // cargar todas
        cmbMatricula.removeAllItems();
        for (MatriculaPractica m : listaMatriculas)
            cmbMatricula.addItem("Matrícula #" + m.getIdMatricula() + " - " +
                (m.getEstudiante() != null ? m.getEstudiante().getNombre() : ""));

        listaRubricas = rubricaLogica.listarPorDocente(usuarioActual.getIdUsuario());
        cmbRubrica.removeAllItems();
        for (Rubrica r : listaRubricas) cmbRubrica.addItem(r.getNombre());
    }

    private void guardar() {
        try {
            double puntaje = Double.parseDouble(txtPuntaje.getText().trim());
            int idxM = cmbMatricula.getSelectedIndex();
            int idxR = cmbRubrica.getSelectedIndex();
            if (idxM < 0 || idxR < 0) { JOptionPane.showMessageDialog(this, "Selecciona matrícula y rúbrica."); return; }

            Evaluacion ev = new Evaluacion();
            ev.setMatricula(listaMatriculas.get(idxM));
            ev.setRubrica(listaRubricas.get(idxR));
            ev.setEvaluador(usuarioActual);
            ev.setPuntajeObtenido(puntaje);
            ev.setObservaciones(txtObservaciones.getText().trim());

            if (evaluacionLogica.registrarEvaluacion(ev)) {
                JOptionPane.showMessageDialog(this, "✅ Evaluación registrada correctamente.");
                txtPuntaje.setText(""); txtObservaciones.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El puntaje debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }
}