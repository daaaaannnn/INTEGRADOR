package gestionpracticas.vista.coordinador;

import gestionpracticas.logica.CursoLogica;
import gestionpracticas.logica.GrupoLogica;
import gestionpracticas.logica.UsuarioLogica;
import gestionpracticas.modelo.Curso;
import gestionpracticas.modelo.Grupo;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GrupoForm extends JFrame {

    private final GrupoLogica grupoLogica;
    private final CursoLogica cursoLogica;
    private final UsuarioLogica usuarioLogica;

    private JTextField txtNombre, txtSemestre, txtAnio;
    private JComboBox<String> cmbCurso, cmbDocente;
    private JButton btnGuardar, btnLimpiar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private List<Curso> listaCursos;
    private List<Usuario> listaDocentes;

    public GrupoForm(Usuario usuario) {
        this.grupoLogica   = new GrupoLogica();
        this.cursoLogica   = new CursoLogica();
        this.usuarioLogica = new UsuarioLogica();
        initComponents();
        cargarCombos();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestión de Grupos");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Grupo"));
        panelForm.setPreferredSize(new Dimension(320, 580));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.gridx = 0; gbc.gridy = 0;

        panelForm.add(new JLabel("Nombre del Grupo:"), gbc); gbc.gridy++;
        txtNombre = new JTextField(); panelForm.add(txtNombre, gbc); gbc.gridy++;

        panelForm.add(new JLabel("Curso:"), gbc); gbc.gridy++;
        cmbCurso = new JComboBox<>(); panelForm.add(cmbCurso, gbc); gbc.gridy++;

        panelForm.add(new JLabel("Docente:"), gbc); gbc.gridy++;
        cmbDocente = new JComboBox<>(); panelForm.add(cmbDocente, gbc); gbc.gridy++;

        panelForm.add(new JLabel("Semestre (ej: 2025-1):"), gbc); gbc.gridy++;
        txtSemestre = new JTextField(); panelForm.add(txtSemestre, gbc); gbc.gridy++;

        panelForm.add(new JLabel("Año:"), gbc); gbc.gridy++;
        txtAnio = new JTextField(); panelForm.add(txtAnio, gbc); gbc.gridy++;

        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(new Color(39,174,96)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFocusPainted(false);
        panelForm.add(btnGuardar, gbc); gbc.gridy++;

        btnLimpiar = new JButton("🔄 Limpiar");
        btnLimpiar.setFocusPainted(false);
        panelForm.add(btnLimpiar, gbc); gbc.gridy++;

        btnEliminar = new JButton("🗑 Eliminar Seleccionado");
        btnEliminar.setBackground(new Color(192,57,43)); btnEliminar.setForeground(Color.WHITE); btnEliminar.setFocusPainted(false);
        panelForm.add(btnEliminar, gbc);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Curso", "Docente", "Semestre", "Año", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
        JScrollPane scroll = new JScrollPane(tabla);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, scroll);
        split.setDividerLocation(320);
        add(split);

        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnEliminar.addActionListener(e -> eliminar());
    }

    private void cargarCombos() {
        listaCursos = cursoLogica.obtenerTodos();
        cmbCurso.removeAllItems();
        for (Curso c : listaCursos) cmbCurso.addItem(c.getNombre() + " (" + c.getCodigo() + ")");

        listaDocentes = usuarioLogica.obtenerTodos();
        cmbDocente.removeAllItems();
        for (Usuario u : listaDocentes)
            if ("DOCENTE".equals(u.getTipoUsuario())) cmbDocente.addItem(u.getNombre() + " " + u.getApellido());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Grupo g : grupoLogica.obtenerTodos()) {
            modeloTabla.addRow(new Object[]{
                g.getIdGrupo(), g.getNombre(),
                g.getCurso() != null ? g.getCurso().getNombre() : "",
                g.getDocente() != null ? g.getDocente().getNombre() + " " + g.getDocente().getApellido() : "",
                g.getSemestre(), g.getAnio(), g.getEstado()
            });
        }
    }

    private void guardar() {
        try {
            if (txtNombre.getText().trim().isEmpty() || txtSemestre.getText().trim().isEmpty() || txtAnio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
            }
            Grupo g = new Grupo();
            g.setNombre(txtNombre.getText().trim());
            g.setSemestre(txtSemestre.getText().trim());
            g.setAnio(Integer.parseInt(txtAnio.getText().trim()));

            int idxCurso = cmbCurso.getSelectedIndex();
            if (idxCurso >= 0) g.setCurso(listaCursos.get(idxCurso));

            int idxDoc = cmbDocente.getSelectedIndex();
            int contDocente = 0;
            for (Usuario u : listaDocentes) {
                if ("DOCENTE".equals(u.getTipoUsuario())) {
                    if (contDocente == idxDoc) { g.setDocente(u); break; }
                    contDocente++;
                }
            }

            if (grupoLogica.crearGrupo(g)) {
                JOptionPane.showMessageDialog(this, "✅ Grupo guardado correctamente.");
                limpiar(); cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un grupo de la tabla."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desactivar este grupo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) { grupoLogica.desactivar(id); cargarTabla(); }
    }

    private void limpiar() {
        txtNombre.setText(""); txtSemestre.setText(""); txtAnio.setText("");
        cmbCurso.setSelectedIndex(0); cmbDocente.setSelectedIndex(0);
    }
}