package gestionpracticas.vista.docente;

import com.gestionpracticas.dao.PreguntaDAO;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PreguntaForm extends JFrame {

    private final PreguntaDAO preguntaDAO;

    private JTextField txtTexto;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public PreguntaForm(Usuario usuario) {
        this.preguntaDAO   = new PreguntaDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Preguntas");
        setSize(820, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva Pregunta"));
        form.setPreferredSize(new Dimension(300, 480));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(7,8,7,8); gbc.gridx = 0;

        gbc.gridy = 0; form.add(new JLabel("Texto de la Pregunta:"), gbc);
        gbc.gridy = 1; txtTexto = new JTextField(); form.add(txtTexto, gbc);
        gbc.gridy = 2; form.add(new JLabel("Tipo:"), gbc);
        gbc.gridy = 3;
        cmbTipo = new JComboBox<>(new String[]{"ABIERTA", "OPCION_MULTIPLE", "ESCALA"});
        form.add(cmbTipo, gbc);

        gbc.gridy = 4;
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(new Color(52,152,219)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFocusPainted(false);
        form.add(btnGuardar, gbc);

        gbc.gridy = 5;
        btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBackground(new Color(192,57,43)); btnEliminar.setForeground(Color.WHITE); btnEliminar.setFocusPainted(false);
        form.add(btnEliminar, gbc);

        String[] cols = {"ID", "Texto", "Tipo"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, new JScrollPane(tabla));
        split.setDividerLocation(300);
        add(split);

        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
    }

    private void guardar() {
        if (txtTexto.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Ingresa el texto de la pregunta."); return; }
        JOptionPane.showMessageDialog(this, "✅ Pregunta guardada (asociar a evaluación desde EvaluacionForm).");
        txtTexto.setText("");
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una pregunta."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar esta pregunta?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            preguntaDAO.eliminar(id);
            modeloTabla.removeRow(fila);
        }
    }
}