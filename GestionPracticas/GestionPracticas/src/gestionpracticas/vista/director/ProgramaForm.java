package gestionpracticas.vista.director;

import com.gestionpracticas.modelo.*;
import gestionpracticas.logica.ProgramaLogica;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProgramaForm extends JFrame {

    private final ProgramaLogica logica;

    private JTextField txtNombre, txtCodigo, txtDescripcion, txtDuracion;
    private JButton btnGuardar, btnLimpiar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public ProgramaForm(Usuario usuario) {
        this.logica        = new ProgramaLogica();
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestión de Programas Académicos");
        setSize(880, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del Programa"));
        form.setPreferredSize(new Dimension(310, 520));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(6,8,6,8); gbc.gridx = 0;

        gbc.gridy = 0; form.add(new JLabel("Nombre:"), gbc);
        gbc.gridy = 1; txtNombre = new JTextField(); form.add(txtNombre, gbc);
        gbc.gridy = 2; form.add(new JLabel("Código:"), gbc);
        gbc.gridy = 3; txtCodigo = new JTextField(); form.add(txtCodigo, gbc);
        gbc.gridy = 4; form.add(new JLabel("Descripción:"), gbc);
        gbc.gridy = 5; txtDescripcion = new JTextField(); form.add(txtDescripcion, gbc);
        gbc.gridy = 6; form.add(new JLabel("Duración (semestres):"), gbc);
        gbc.gridy = 7; txtDuracion = new JTextField(); form.add(txtDuracion, gbc);

        gbc.gridy = 8;
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(new Color(33,97,140)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFocusPainted(false);
        form.add(btnGuardar, gbc);
        gbc.gridy = 9; btnLimpiar = new JButton("🔄 Limpiar"); btnLimpiar.setFocusPainted(false); form.add(btnLimpiar, gbc);
        gbc.gridy = 10;
        btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBackground(new Color(192,57,43)); btnEliminar.setForeground(Color.WHITE); btnEliminar.setFocusPainted(false);
        form.add(btnEliminar, gbc);

        String[] cols = {"ID","Nombre","Código","Semestres","Estado"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24); tabla.getColumnModel().getColumn(0).setMaxWidth(40);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(310);
        add(split);

        btnGuardar.addActionListener(e  -> guardar());
        btnLimpiar.addActionListener(e  -> limpiar());
        btnEliminar.addActionListener(e -> eliminar());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Programa p : logica.obtenerTodos())
            modeloTabla.addRow(new Object[]{p.getIdPrograma(), p.getNombre(), p.getCodigo(), p.getDuracionSemestres(), p.getEstado()});
    }

    private void guardar() {
        try {
            if (txtNombre.getText().trim().isEmpty() || txtCodigo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y código son obligatorios."); return;
            }
            Programa p = new Programa();
            p.setNombre(txtNombre.getText().trim()); p.setCodigo(txtCodigo.getText().trim());
            p.setDescripcion(txtDescripcion.getText().trim());
            p.setDuracionSemestres(txtDuracion.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtDuracion.getText().trim()));

            if (logica.crearPrograma(p)) {
                JOptionPane.showMessageDialog(this, "✅ Programa guardado correctamente.");
                limpiar(); cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un programa."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this, "¿Desactivar este programa?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            logica.desactivar(id); cargarTabla();
        }
    }

    private void limpiar() { txtNombre.setText(""); txtCodigo.setText(""); txtDescripcion.setText(""); txtDuracion.setText(""); }
}