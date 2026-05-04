package gestionpracticas.vista.director;

import gestionpracticas.logica.GrupoLogica;
import gestionpracticas.logica.PracticaLogica;
import gestionpracticas.modelo.Grupo;
import gestionpracticas.modelo.Institucion;
import gestionpracticas.modelo.Practica;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.List;

public class PracticaForm extends JFrame {

    private final PracticaLogica practicaLogica;
    private final GrupoLogica grupoLogica;

    private JTextField txtTitulo, txtDescripcion, txtFechaInicio, txtFechaFin;
    private JComboBox<String> cmbTipo, cmbGrupo;
    private JButton btnGuardar, btnLimpiar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private List<Grupo> listaGrupos;

    public PracticaForm(Usuario usuario) {
        this.practicaLogica = new PracticaLogica();
        this.grupoLogica    = new GrupoLogica();
        initComponents();
        cargarGrupos();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestión de Prácticas");
        setSize(960, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos de la Práctica"));
        form.setPreferredSize(new Dimension(340, 580));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(6,8,6,8); gbc.gridx = 0;

        gbc.gridy = 0; form.add(new JLabel("Título:"), gbc);
        gbc.gridy = 1; txtTitulo = new JTextField(); form.add(txtTitulo, gbc);
        gbc.gridy = 2; form.add(new JLabel("Descripción:"), gbc);
        gbc.gridy = 3; txtDescripcion = new JTextField(); form.add(txtDescripcion, gbc);
        gbc.gridy = 4; form.add(new JLabel("Tipo:"), gbc);
        gbc.gridy = 5; cmbTipo = new JComboBox<>(new String[]{"EMPRESARIAL","SOCIAL","INVESTIGACION"}); form.add(cmbTipo, gbc);
        gbc.gridy = 6; form.add(new JLabel("Grupo:"), gbc);
        gbc.gridy = 7; cmbGrupo = new JComboBox<>(); form.add(cmbGrupo, gbc);
        gbc.gridy = 8; form.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"), gbc);
        gbc.gridy = 9; txtFechaInicio = new JTextField(); form.add(txtFechaInicio, gbc);
        gbc.gridy = 10; form.add(new JLabel("Fecha Fin (dd/MM/yyyy):"), gbc);
        gbc.gridy = 11; txtFechaFin = new JTextField(); form.add(txtFechaFin, gbc);

        gbc.gridy = 12;
        btnGuardar = new JButton("💾 Guardar Práctica");
        btnGuardar.setBackground(new Color(33,97,140)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFocusPainted(false);
        form.add(btnGuardar, gbc);
        gbc.gridy = 13;
        btnLimpiar = new JButton("🔄 Limpiar"); btnLimpiar.setFocusPainted(false); form.add(btnLimpiar, gbc);
        gbc.gridy = 14;
        btnEliminar = new JButton("🗑 Eliminar Seleccionado");
        btnEliminar.setBackground(new Color(192,57,43)); btnEliminar.setForeground(Color.WHITE); btnEliminar.setFocusPainted(false);
        form.add(btnEliminar, gbc);

        String[] cols = {"ID","Título","Tipo","Grupo","Estado"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(340);
        add(split);

        btnGuardar.addActionListener(e  -> guardar());
        btnLimpiar.addActionListener(e  -> limpiar());
        btnEliminar.addActionListener(e -> eliminar());
    }

    private void cargarGrupos() {
        listaGrupos = grupoLogica.obtenerTodos();
        cmbGrupo.removeAllItems();
        for (Grupo g : listaGrupos) cmbGrupo.addItem(g.getNombre() + " - " + g.getSemestre());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Practica p : practicaLogica.obtenerTodas()) {
            modeloTabla.addRow(new Object[]{
                p.getIdPractica(), p.getTitulo(), p.getTipoPractica(),
                p.getGrupo() != null ? p.getGrupo().getNombre() : "", p.getEstado()
            });
        }
    }

    private void guardar() {
        try {
            if (txtTitulo.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "El título es obligatorio."); return; }
            Practica p = new Practica();
            p.setTitulo(txtTitulo.getText().trim());
            p.setDescripcion(txtDescripcion.getText().trim());
            p.setTipoPractica((String) cmbTipo.getSelectedItem());
            int idxG = cmbGrupo.getSelectedIndex();
            if (idxG >= 0 && !listaGrupos.isEmpty()) p.setGrupo(listaGrupos.get(idxG));

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            if (!txtFechaInicio.getText().trim().isEmpty()) p.setFechaInicio(sdf.parse(txtFechaInicio.getText().trim()));
            if (!txtFechaFin.getText().trim().isEmpty())    p.setFechaFin(sdf.parse(txtFechaFin.getText().trim()));

            // Institución por defecto (id=1), ajustar según necesidad
            Institucion inst = new Institucion(); inst.setIdInstitucion(1); p.setInstitucion(inst);

            if (practicaLogica.crearPractica(p)) {
                JOptionPane.showMessageDialog(this, "✅ Práctica guardada correctamente.");
                limpiar(); cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una práctica."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this, "¿Desactivar esta práctica?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            practicaLogica.desactivarPractica(id); cargarTabla();
        }
    }

    private void limpiar() {
        txtTitulo.setText(""); txtDescripcion.setText("");
        txtFechaInicio.setText(""); txtFechaFin.setText("");
    }
}