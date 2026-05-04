package vista;

import gestionpracticas.logica.UsuarioLogica;
import gestionpracticas.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

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
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(33, 97, 140));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel lblTitulo = new JLabel("GESTIÓN DE PRÁCTICAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);
        
        // Email
        JLabel lblEmail = new JLabel("Correo Electrónico:");
        lblEmail.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        txtEmail.setPreferredSize(new Dimension(250, 35));
        gbc.gridy = 2;
        panel.add(txtEmail, gbc);
        
        // Password
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        gbc.gridy = 3;
        panel.add(lblPass, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setPreferredSize(new Dimension(250, 35));
        gbc.gridy = 4;
        panel.add(txtPassword, gbc);
        
        // Mostrar contraseña
        chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setForeground(Color.WHITE);
        chkMostrar.setOpaque(false);
        chkMostrar.addActionListener(e -> {
            if (chkMostrar.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
        gbc.gridy = 5;
        panel.add(chkMostrar, gbc);
        
        // Error
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridy = 6;
        panel.add(lblError, gbc);
        
        // Botón Ingresar
        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setBackground(new Color(39, 174, 96));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setPreferredSize(new Dimension(250, 40));
        btnIngresar.addActionListener(e -> login());
        gbc.gridy = 7;
        panel.add(btnIngresar, gbc);
        
        add(panel);
        
        txtPassword.addActionListener(e -> login());
    }
    
    private void login() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (email.isEmpty()) {
            lblError.setText("⚠ Ingrese su correo electrónico");
            return;
        }
        if (password.isEmpty()) {
            lblError.setText("⚠ Ingrese su contraseña");
            return;
        }
        
        btnIngresar.setEnabled(false);
        lblError.setText("Verificando...");
        lblError.setForeground(Color.BLUE);
        
        SwingWorker<Usuario, Void> worker = new SwingWorker<>() {
            @Override
            protected Usuario doInBackground() {
                try {
                    return new UsuarioLogica().iniciarSesion(email, password);
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
                        JOptionPane.showMessageDialog(LoginForm.this,
                            "Bienvenido " + usuario.getNombre() + " " + usuario.getApellido(),
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        lblError.setText("⚠ Correo o contraseña incorrectos");
                        lblError.setForeground(Color.RED);
                        txtPassword.setText("");
                    }
                } catch (Exception e) {
                    lblError.setText("⚠ Error de conexión");
                } finally {
                    btnIngresar.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}