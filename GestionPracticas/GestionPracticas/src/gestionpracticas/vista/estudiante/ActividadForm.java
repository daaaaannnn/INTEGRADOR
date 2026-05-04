package gestionpracticas.vista.estudiante;

import gestionpracticas.logica.MatriculaPracticaLogica;
import gestionpracticas.logica.RegistroActividadLogica;
import gestionpracticas.modelo.MatriculaPractica;
import gestionpracticas.modelo.RegistroActividad;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActividadForm extends JFrame {

    private final Usuario usuarioActual;
    private final RegistroActividadLogica actividadLogica;
    private final MatriculaPracticaLogica matriculaLogica;

    private JComboBox<String> cmbMatricula, cmbTipo;
    private JTextField txtDescripcion, txtHoras;
    private JButton btnGuardar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private List<MatriculaPractica> listaMatriculas;

    public ActividadForm(Usuario usuario) {
        this.usuarioActual   = usuario;
        this.actividadLogica = new RegistroActividadLogica();
        this.matriculaLogica = new MatriculaPracticaLogica();
        initComponents();
        cargarMatriculas();
    }

    private void initComponents() {
        setTitle("Registrar Actividad de Práctica");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva Actividad"));
        form.setPreferredSize(new Dimension(320, 520));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(7,8,7,8); gbc.gridx = 0;

        gbc.gridy = 0; form.add(new JLabel("Mi Práctica:"), gbc);
        gbc.gridy = 1; cmbMatricula = new JComboBox<>(); form.add(cmbMatricula, gbc);

        gbc.gridy = 2; form.add(new JLabel("Descripción de la Actividad:"), gbc);
        gbc.gridy = 3; txtDescripcion = new JTextField(); form.add(txtDescripcion, gbc);

        gbc.gridy = 4; form.add(new JLabel("Horas trabajadas:"), gbc);
        gbc.gridy = 5; txtHoras = new JTextField(); form.add(txtHoras, gbc);

        gbc.gridy = 6; form.add(new JLabel("Tipo de Actividad:"), gbc);
        gbc.gridy = 7;
        cmbTipo = new JComboBox<>(new String[]{"PRESENCIAL", "VIRTUAL", "INVESTIGACION", "REUNION", "OTRO"});
        form.add(cmbTipo, gbc);

        gbc.gridy = 8;
        btnGuardar = new JButton("📤 Enviar Actividad");
        btnGuardar.setBackground(new Color(230,126,34)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFocusPainted(false);
        form.add(btnGuardar, gbc);

        // Info
        gbc.gridy = 9;
        JLabel lblInfo = new JLabel("<html><small>Estado inicial: PENDIENTE<br>El docente aprobará tu actividad.</small></html>");
        lblInfo.setForeground(Color.GRAY);
        form.add(lblInfo, gbc);

        String[] cols = {"ID", "Práctica", "Descripción", "Horas", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(320);
        add(split);

        btnGuardar.addActionListener(e -> guardar());
        cmbMatricula.addActionListener(e -> cargarTabla());
    }

    private void cargarMatriculas() {
        listaMatriculas = matriculaLogica.listarPorEstudiante(usuarioActual.getIdUsuario());
        cmbMatricula.removeAllItems();
        for (MatriculaPractica m : listaMatriculas)
            cmbMatricula.addItem("Práctica: " + (m.getPractica() != null ? m.getPractica().getTitulo() : "#" + m.getIdMatricula()));
        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        int idx = cmbMatricula.getSelectedIndex();
        if (idx < 0 || listaMatriculas.isEmpty()) return;
        int idMatricula = listaMatriculas.get(idx).getIdMatricula();
        for (RegistroActividad r : actividadLogica.listarPorMatricula(idMatricula)) {
            modeloTabla.addRow(new Object[]{
                r.getIdRegistro(),
                r.getMatricula() != null ? "#" + r.getMatricula().getIdMatricula() : "",
                r.getDescripcion(), r.getHoras(), r.getTipoActividad(), r.getEstado()
            });
        }
    }

    private void guardar() {
        try {
            if (txtDescripcion.getText().trim().isEmpty() || txtHoras.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
            }
            int idx = cmbMatricula.getSelectedIndex();
            if (idx < 0 || listaMatriculas.isEmpty()) { JOptionPane.showMessageDialog(this, "Selecciona una práctica."); return; }

            RegistroActividad r = new RegistroActividad();
            r.setMatricula(listaMatriculas.get(idx));
            r.setDescripcion(txtDescripcion.getText().trim());
            r.setHoras(Double.parseDouble(txtHoras.getText().trim()));
            r.setTipoActividad((String) cmbTipo.getSelectedItem());

            if (actividadLogica.registrar(r)) {
                JOptionPane.showMessageDialog(this, "✅ Actividad enviada correctamente. Estado: PENDIENTE.");
                txtDescripcion.setText(""); txtHoras.setText(""); cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Las horas deben ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }
}