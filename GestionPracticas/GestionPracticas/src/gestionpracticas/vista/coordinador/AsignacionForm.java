package gestionpracticas.vista.coordinador;

import com.gestionpracticas.modelo.*;
import gestionpracticas.logica.AsignacionDocenteLogica;
import gestionpracticas.logica.PracticaLogica;
import gestionpracticas.logica.UsuarioLogica;
import gestionpracticas.modelo.Practica;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionForm extends JFrame {

    private final AsignacionDocenteLogica logica;
    private final PracticaLogica practicaLogica;
    private final UsuarioLogica usuarioLogica;

    private JComboBox<String> cmbPractica, cmbDocente;
    private JTextField txtRol;
    private JButton btnAsignar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private List<Practica> listaPracticas;
    private List<Usuario> listaDocentes;

    public AsignacionForm(Usuario usuario) {
        this.logica         = new AsignacionDocenteLogica();
        this.practicaLogica = new PracticaLogica();
        this.usuarioLogica  = new UsuarioLogica();
        initComponents();
        cargarCombos();
    }

    private void initComponents() {
        setTitle("Asignación de Docentes a Prácticas");
        setSize(880, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva Asignación"));
        form.setPreferredSize(new Dimension(300, 520));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(7,8,7,8); gbc.gridx = 0;

        gbc.gridy = 0; form.add(new JLabel("Práctica:"), gbc);
        gbc.gridy = 1; cmbPractica = new JComboBox<>(); form.add(cmbPractica, gbc);
        gbc.gridy = 2; form.add(new JLabel("Docente:"), gbc);
        gbc.gridy = 3; cmbDocente = new JComboBox<>(); form.add(cmbDocente, gbc);
        gbc.gridy = 4; form.add(new JLabel("Rol en la práctica:"), gbc);
        gbc.gridy = 5; txtRol = new JTextField(); form.add(txtRol, gbc);

        gbc.gridy = 6;
        btnAsignar = new JButton("📌 Asignar");
        btnAsignar.setBackground(new Color(39,174,96)); btnAsignar.setForeground(Color.WHITE); btnAsignar.setFocusPainted(false);
        form.add(btnAsignar, gbc);

        gbc.gridy = 7;
        btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBackground(new Color(192,57,43)); btnEliminar.setForeground(Color.WHITE); btnEliminar.setFocusPainted(false);
        form.add(btnEliminar, gbc);

        String[] cols = {"ID", "Práctica", "Docente", "Rol", "Fecha"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(300);
        add(split);

        btnAsignar.addActionListener(e -> asignar());
        btnEliminar.addActionListener(e -> eliminar());
        cmbPractica.addActionListener(e -> cargarTabla());
    }

    private void cargarCombos() {
        listaPracticas = practicaLogica.obtenerTodas();
        cmbPractica.removeAllItems();
        for (Practica p : listaPracticas) cmbPractica.addItem(p.getTitulo());

        listaDocentes = usuarioLogica.obtenerTodos();
        cmbDocente.removeAllItems();
        for (Usuario u : listaDocentes)
            if ("DOCENTE".equals(u.getTipoUsuario())) cmbDocente.addItem(u.getNombre() + " " + u.getApellido());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        int idx = cmbPractica.getSelectedIndex();
        if (idx < 0 || listaPracticas.isEmpty()) return;
        int idPractica = listaPracticas.get(idx).getIdPractica();
        for (gestionpracticas.modelo.AsignacionDocente a : logica.listarPorPractica(idPractica)) {
            String docNombre = a.getUsuarios() != null && !a.getUsuarios().isEmpty()
                ? a.getUsuarios().get(0).getNombre() + " " + a.getUsuarios().get(0).getApellido() : "";
            modeloTabla.addRow(new Object[]{
                a.getIdAsignacionDocente(),
                a.getPractica() != null ? a.getPractica().getTitulo() : "",
                docNombre, a.getRolEnPractica(), a.getFechaAsignacion()
            });
        }
    }

    private void asignar() {
        try {
            if (txtRol.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Ingresa el rol."); return; }
            int idxP = cmbPractica.getSelectedIndex();
            if (idxP < 0) { JOptionPane.showMessageDialog(this, "Selecciona una práctica."); return; }

            AsignacionDocente a = new AsignacionDocente();
            a.setPractica(listaPracticas.get(idxP));
            a.setRolEnPractica(txtRol.getText().trim());

            int idxD = cmbDocente.getSelectedIndex();
            int cont = 0;
            List<Usuario> seleccionados = new ArrayList<>();
            for (Usuario u : listaDocentes) {
                if ("DOCENTE".equals(u.getTipoUsuario())) {
                    if (cont == idxD) { seleccionados.add(u); break; }
                    cont++;
                }
            }
            a.setUsuarios(seleccionados);

            if (logica.asignar(a)) {
                JOptionPane.showMessageDialog(this, "✅ Docente asignado correctamente.");
                txtRol.setText(""); cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al asignar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una asignación."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar esta asignación?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            logica.eliminar(id); cargarTabla();
        }
    }
}
