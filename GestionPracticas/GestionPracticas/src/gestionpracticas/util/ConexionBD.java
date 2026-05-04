package gestionpracticas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static ConexionBD instancia;
    private Connection connection = null;
    
    private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USUARIO = "PROYECTOJD";
    private final String CONTRASENA = "PROYECTOJD";
    
    private ConexionBD() {
        try {
            System.out.println("=========================================");
            System.out.println("  Conectando a Oracle XE...");
            System.out.println("=========================================");
            
            // Cargar el driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            
            System.out.println("✅ ¡CONEXIÓN EXITOSA!");
            System.out.println("=========================================");
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver no encontrado");
            System.err.println("Verifica que ojdbc8.jar esté en Libraries");
            System.err.println("=========================================");
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            System.err.println("Verifica que Oracle esté corriendo");
            System.err.println("Verifica el usuario PROYECTOJD");
            System.err.println("=========================================");
        }
    }
    
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}