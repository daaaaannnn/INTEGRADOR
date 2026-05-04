package gestionpracticas.vista.institucion;

import gestionpracticas.logica.RegistroActividadLogica;
import gestionpracticas.modelo.RegistroActividad;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HorasForm extends JFrame {

    private final RegistroActividadLogica logica;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnAprobar, btnRechazar, btnRefrescar;
    private JLabel lblTotalHoras;

    public HorasForm(Usuario usuario) {
        this.logica        = new RegistroActividadLogica();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Horas de Práctica");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header info
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        JLabel lblTitulo = new JLabel("Registro de Actividades - Validación de Horas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalHoras = new JLabel("Total horas aprobadas: 0");
        lblTotalHoras.setForeground(new Color(39, 174, 96));
        lblTotalHoras.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelInfo.add(lblTitulo, BorderLayout.WEST);
        panelInfo.add(lblTotalHoras, BorderLayout.EAST);

        // Tabla
        String[] cols = {"ID", "Matrícula", "Fecha", "Descripción", "Horas", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(cols, 0) {@Override
 public boolean isCellEditable(int r, int c) { return false; } };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla.getColumnModel().getColumn(1).setMaxWidth(70);
        tabla.getColumnModel().getColumn(4).setMaxWidth(60);

        // Colorear filas según estado
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                String estado = (String) modeloTabla.getValueAt(row, 6);
                if (!sel) {
                    if (null == estado)   setBackground(Color.WHITE);
                    else switch (estado) {
                        case "APROBADO" -> setBackground(new Color(212, 237, 218));
                        case "RECHAZADO" -> setBackground(new Color(248, 215, 218));
                        default -> setBackground(Color.WHITE);
                    }
                }
                return this;
            }
        });

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAprobar   = new JButton("✅ Aprobar Horas");
        btnRechazar  = new JButton("❌ Rechazar");
        btnRefrescar = new JButton("🔄 Refrescar");

        btnAprobar.setBackground(new Color(39,174,96));   btnAprobar.setForeground(Color.WHITE);   btnAprobar.setFocusPainted(false);
        btnRechazar.setBackground(new Color(192,57,43));  btnRechazar.setForeground(Color.WHITE);  btnRechazar.setFocusPainted(false);
        btnRefrescar.setFocusPainted(false);

        panelBotones.add(btnAprobar); panelBotones.add(btnRechazar); panelBotones.add(btnRefrescar);

        add(panelInfo, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAprobar.addActionListener(e   -> aprobar());
        btnRechazar.addActionListener(e  -> rechazar());
        btnRefrescar.addActionListener(e -> cargarTabla());

        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        double totalHoras = 0;
        // Cargar actividades pendientes (idMatricula=0 carga todas en este ejemplo)
        for (RegistroActividad r : logica.listarPorMatricula(0)) {
            modeloTabla.addRow(new Object[]{
                r.getIdRegistro(),
                r.getMatricula() != null ? r.getMatricula().getIdMatricula() : "",
                r.getFechaActividad(), r.getDescripcion(), r.getHoras(),
                r.getTipoActividad(), r.getEstado()
            });
            if ("APROBADO".equals(r.getEstado())) totalHoras += r.getHoras();
        }
        lblTotalHoras.setText("Total horas aprobadas: " + totalHoras);
    }

    private void aprobar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una actividad."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (logica.aprobar(id)) {
            JOptionPane.showMessageDialog(this, "✅ Horas aprobadas.");
            cargarTabla();
        }
    }

    private void rechazar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una actividad."); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (logica.rechazar(id)) {
            JOptionPane.showMessageDialog(this, "❌ Actividad rechazada.");
            cargarTabla();
        }
    }
}