package vista;

import gestionpracticas.logica.UsuarioLogica;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

public class LoginForm extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblError;
    private JCheckBox chkMostrar;

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("PROYECTOJD - Sistema de Gestión de Prácticas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Sin bordes para diseño custom

        // Panel principal con fondo degradado
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(21, 67, 96), 0, getHeight(), new Color(33, 97, 140));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ── Panel superior (logo / título) ─────────────────────
        JPanel panelTop = new JPanel(new GridBagLayout());
        panelTop.setOpaque(false);
        panelTop.setPreferredSize(new Dimension(480, 180));

        // Botón cerrar (X) arriba derecha
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setForeground(new Color(255, 255, 255, 180));
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> System.exit(0));

        JPanel panelCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        panelCerrar.setOpaque(false);
        panelCerrar.add(btnCerrar);

        // Ícono circular
        JLabel lblIcono = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillOval(0, 0, 70, 70);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 30));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("🎓", (70 - fm.stringWidth("🎓")) / 2, 50);
            }
        };
        lblIcono.setPreferredSize(new Dimension(70, 70));

        JLabel lblTitulo = new JLabel("GESTIÓN DE PRÁCTICAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("PROYECTOJD");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(255, 255, 255, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 0, 8, 0);
        panelTop.add(lblIcono, gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 4, 0);
        panelTop.add(lblTitulo, gbc);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 0, 0);
        panelTop.add(lblSubtitulo, gbc);

        JPanel wrapTop = new JPanel(new BorderLayout());
        wrapTop.setOpaque(false);
        wrapTop.add(panelCerrar, BorderLayout.NORTH);
        wrapTop.add(panelTop, BorderLayout.CENTER);

        // ── Panel de formulario (card blanco redondeado) ────────
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(400, 310));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 30, 8, 30);

        // Título del card
        JLabel lblAcceso = new JLabel("Iniciar Sesión");
        lblAcceso.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAcceso.setForeground(new Color(21, 67, 96));
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(25, 30, 15, 30);
        card.add(lblAcceso, c);

        // Email label
        JLabel lblEmail = new JLabel("Correo electrónico");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(new Color(100, 100, 100));
        c.gridy = 1; c.insets = new Insets(4, 30, 2, 30);
        card.add(lblEmail, c);

        // Email field
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(340, 40));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        txtEmail.setBackground(new Color(248, 249, 250));
        // Placeholder
        txtEmail.setText("ejemplo@correo.com");
        txtEmail.setForeground(Color.GRAY);
        txtEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals("ejemplo@correo.com")) {
                    txtEmail.setText(""); txtEmail.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().isEmpty()) {
                    txtEmail.setText("ejemplo@correo.com"); txtEmail.setForeground(Color.GRAY);
                }
            }
        });
        c.gridy = 2; c.insets = new Insets(0, 30, 8, 30);
        card.add(txtEmail, c);

        // Password label
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setForeground(new Color(100, 100, 100));
        c.gridy = 3; c.insets = new Insets(4, 30, 2, 30);
        card.add(lblPass, c);

        // Password field
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(340, 40));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        txtPassword.setBackground(new Color(248, 249, 250));
        c.gridy = 4; c.insets = new Insets(0, 30, 4, 30);
        card.add(txtPassword, c);

        // Mostrar contraseña
        chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkMostrar.setForeground(new Color(120, 120, 120));
        chkMostrar.setOpaque(false);
        chkMostrar.setFocusPainted(false);
        chkMostrar.addActionListener(e ->
            txtPassword.setEchoChar(chkMostrar.isSelected() ? (char) 0 : '•')
        );
        c.gridy = 5; c.insets = new Insets(0, 28, 6, 30);
        card.add(chkMostrar, c);

        // Error label
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(192, 57, 43));
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 6; c.insets = new Insets(0, 30, 2, 30);
        card.add(lblError, c);

        // Botón ingresar
        btnIngresar = new JButton("INGRESAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(new Color(21, 67, 96));
                else if (getModel().isRollover()) g2.setColor(new Color(40, 116, 166));
                else g2.setColor(new Color(33, 97, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };
        btnIngresar.setPreferredSize(new Dimension(340, 44));
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.gridy = 7; c.insets = new Insets(4, 30, 25, 30);
        card.add(btnIngresar, c);

        // ── Wrapper del card centrado ───────────────────────────
        JPanel wrapCard = new JPanel(new GridBagLayout());
        wrapCard.setOpaque(false);
        wrapCard.setBorder(BorderFactory.createEmptyBorder(0, 30, 40, 30));
        wrapCard.add(card);

        panelPrincipal.add(wrapTop, BorderLayout.NORTH);
        panelPrincipal.add(wrapCard, BorderLayout.CENTER);

        // Footer
        JLabel lblFooter = new JLabel("© 2025 PROYECTOJD - Sistema de Gestión de Prácticas", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFooter.setForeground(new Color(255, 255, 255, 100));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panelPrincipal.add(lblFooter, BorderLayout.SOUTH);

        add(panelPrincipal);

        // ── Acciones ───────────────────────────────────────────
        btnIngresar.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());

        // Permitir arrastrar la ventana (sin decoración)
        PanelDrag drag = new PanelDrag(this);
        panelPrincipal.addMouseListener(drag);
        panelPrincipal.addMouseMotionListener(drag);
    }

    private void login() {
        String email = txtEmail.getText().trim();
        String pass  = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || email.equals("ejemplo@correo.com")) {
            lblError.setText("⚠ Ingresa tu correo electrónico."); return;
        }
        if (pass.isEmpty()) {
            lblError.setText("⚠ Ingresa tu contraseña."); return;
        }

        lblError.setText("Verificando...");
        lblError.setForeground(new Color(52, 152, 219));

        // Usar SwingWorker para no bloquear la UI
        SwingWorker<Usuario, Void> worker = new SwingWorker<>() {
            @Override
            protected Usuario doInBackground() {
                try {
                    return new UsuarioLogica().iniciarSesion(email, pass);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    Usuario usuario = get();
                    if (usuario != null) {
                        lblError.setText(" ");
                        dispose();
                        abrirDashboard(usuario);
                    } else {
                        lblError.setForeground(new Color(192, 57, 43));
                        lblError.setText("⚠ Correo o contraseña incorrectos.");
                        txtPassword.setText("");
                        txtPassword.requestFocus();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    lblError.setForeground(new Color(192, 57, 43));
                    lblError.setText("⚠ Error de conexión con la base de datos.");
                }
            }
        };
        worker.execute();
    }

    private void abrirDashboard(Usuario usuario) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switch (usuario.getTipoUsuario().toUpperCase()) {
                    case "DIRECTOR" -> new com.gestionpracticas.vista.director.DirectorDashboard(usuario).setVisible(true);
                    case "COORDINADOR" -> new com.gestionpracticas.vista.coordinador.CoordinadorDashboard(usuario).setVisible(true);
                    case "DOCENTE" -> new com.gestionpracticas.vista.docente.DocenteDashboard(usuario).setVisible(true);
                    case "ESTUDIANTE" -> new com.gestionpracticas.vista.estudiante.EstudianteDashboard(usuario).setVisible(true);
                    case "INSTITUCION" -> new com.gestionpracticas.vista.institucion.InstitucionDashboard(usuario).setVisible(true);
                    default -> JOptionPane.showMessageDialog(null,
                            "Tipo de usuario no reconocido: " + usuario.getTipoUsuario(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // ── Clase para arrastrar la ventana sin decoración ──────────
    private static class PanelDrag extends MouseAdapter {
        private final JFrame frame;
        private Point inicio;

        public PanelDrag(JFrame frame) { this.frame = frame; }

        @Override public void mousePressed(MouseEvent e)  { inicio = e.getPoint(); }
        @Override public void mouseDragged(MouseEvent e)  {
            if (inicio != null) {
                Point actual = frame.getLocation();
                frame.setLocation(actual.x + e.getX() - inicio.x, actual.y + e.getY() - inicio.y);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ignored) {}
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}