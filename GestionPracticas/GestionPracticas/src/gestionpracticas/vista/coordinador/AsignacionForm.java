package gestionpracticas.vista.coordinador;

// CORREGIDO: Usar un solo paquete consistente
import gestionpracticas.modelo.AsignacionDocente;
import gestionpracticas.modelo.Practica;
import gestionpracticas.modelo.Usuario;
import gestionpracticas.logica.AsignacionDocenteLogica;
import gestionpracticas.logica.PracticaLogica;
import gestionpracticas.logica.UsuarioLogica;
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
    private List<Usuario> listaDocentes;  // Solo contendrá docentes filtrados

    public AsignacionForm(Usuario usuario) {
        this.logica = new AsignacionDocenteLogica();
        this.practicaLogica = new PracticaLogica();
        this.usuarioLogica = new UsuarioLogica();
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.gridx = 0;

        gbc.gridy = 0;
        form.add(new JLabel("Práctica:"), gbc);
        gbc.gridy = 1;
        cmbPractica = new JComboBox<>();
        form.add(cmbPractica, gbc);
        gbc.gridy = 2;
        form.add(new JLabel("Docente:"), gbc);
        gbc.gridy = 3;
        cmbDocente = new JComboBox<>();
        form.add(cmbDocente, gbc);
        gbc.gridy = 4;
        form.add(new JLabel("Rol en la práctica:"), gbc);
        gbc.gridy = 5;
        txtRol = new JTextField();
        form.add(txtRol, gbc);

        gbc.gridy = 6;
        btnAsignar = new JButton("📌 Asignar");
        btnAsignar.setBackground(new Color(39, 174, 96));
        btnAsignar.setForeground(Color.WHITE);
        btnAsignar.setFocusPainted(false);
        btnAsignar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        form.add(btnAsignar, gbc);

        gbc.gridy = 7;
        btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBackground(new Color(192, 57, 43));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        form.add(btnEliminar, gbc);

        String[] cols = {"ID", "Práctica", "Docente", "Rol", "Fecha"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnEliminar.setEnabled(tabla.getSelectedRow() >= 0);
            }
        });
        btnEliminar.setEnabled(false);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(300);
        add(split);

        btnAsignar.addActionListener(e -> asignar());
        btnEliminar.addActionListener(e -> eliminar());
        cmbPractica.addActionListener(e -> cargarTabla());
    }

    private void cargarCombos() {
        // Cargar prácticas
        listaPracticas = practicaLogica.obtenerTodas();
        cmbPractica.removeAllItems();
        if (listaPracticas != null && !listaPracticas.isEmpty()) {
            for (Practica p : listaPracticas) {
                cmbPractica.addItem(p.getTitulo());
            }
        } else {
            cmbPractica.addItem("No hay prácticas disponibles");
            cmbPractica.setEnabled(false);
        }

        // Cargar solo docentes (CORREGIDO)
        List<Usuario> todosUsuarios = usuarioLogica.obtenerTodos();
        listaDocentes = new ArrayList<>();
        cmbDocente.removeAllItems();
        
        if (todosUsuarios != null) {
            for (Usuario u : todosUsuarios) {
                if (u != null && "DOCENTE".equals(u.getTipoUsuario())) {
                    listaDocentes.add(u);
                    cmbDocente.addItem(u.getNombre() + " " + u.getApellido());
                }
            }
        }
        
        if (listaDocentes.isEmpty()) {
            cmbDocente.addItem("No hay docentes disponibles");
            cmbDocente.setEnabled(false);
        } else {
            cmbDocente.setEnabled(true);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        
        if (listaPracticas == null || listaPracticas.isEmpty()) {
            return;
        }
        
        int idx = cmbPractica.getSelectedIndex();
        if (idx < 0 || idx >= listaPracticas.size()) {
            return;
        }
        
        int idPractica = listaPracticas.get(idx).getIdPractica();
        List<AsignacionDocente> asignaciones = logica.listarPorPractica(idPractica);
        
        if (asignaciones != null) {
            for (AsignacionDocente a : asignaciones) {
                String docNombre = "";
                if (a.getUsuarios() != null && !a.getUsuarios().isEmpty() && a.getUsuarios().get(0) != null) {
                    Usuario docente = a.getUsuarios().get(0);
                    docNombre = docente.getNombre() + " " + docente.getApellido();
                }
                
                String tituloPractica = a.getPractica() != null ? a.getPractica().getTitulo() : "";
                String rol = a.getRolEnPractica() != null ? a.getRolEnPractica() : "";
                String fecha = a.getFechaAsignacion() != null ? a.getFechaAsignacion().toString() : "";
                
                modeloTabla.addRow(new Object[]{
                    a.getIdAsignacionDocente(),
                    tituloPractica,
                    docNombre,
                    rol,
                    fecha
                });
            }
        }
    }

    private void asignar() {
        try {
            // Validaciones
            if (txtRol.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa el rol del docente.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idxP = cmbPractica.getSelectedIndex();
            if (idxP < 0 || listaPracticas == null || listaPracticas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona una práctica válida.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idxD = cmbDocente.getSelectedIndex();
            if (idxD < 0 || listaDocentes == null || listaDocentes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un docente válido.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear asignación
            AsignacionDocente a = new AsignacionDocente();
            a.setPractica(listaPracticas.get(idxP));
            a.setRolEnPractica(txtRol.getText().trim());
            
            // CORREGIDO: Usar listaDocentes directamente (ya está filtrada)
            List<Usuario> seleccionados = new ArrayList<>();
            seleccionados.add(listaDocentes.get(idxD));
            a.setUsuarios(seleccionados);
            
            // Guardar
            if (logica.asignar(a)) {
                JOptionPane.showMessageDialog(this, "✅ Docente asignado correctamente.");
                txtRol.setText("");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al asignar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una asignación para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de eliminar esta asignación?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (logica.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "✅ Asignación eliminada correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar la asignación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}