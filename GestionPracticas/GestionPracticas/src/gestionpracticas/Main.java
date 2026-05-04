package gestionpracticas;

import gestionpracticas.util.ConexionBD;

public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE PRÁCTICAS - PROYECTOJD");
        System.out.println("===========================================");

        // Probar conexión a la base de datos
        ConexionBD conexion = ConexionBD.getInstancia();
        if (conexion.getConnection() != null) {
            System.out.println("✅ Sistema listo.");
            // Aquí se lanzaría la ventana de Login:
            // java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
        } else {
            System.err.println("❌ No se pudo conectar a la base de datos.");
            System.err.println("   Verifique que Oracle XE esté corriendo y que ojdbc.jar esté en Libraries.");
        }
    }
}
