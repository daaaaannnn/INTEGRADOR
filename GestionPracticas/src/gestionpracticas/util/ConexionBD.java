package gestionpracticas.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection connection = null;

    private final String url      = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String user     = "PROYECTOJD";
    private final String password = "PROYECTOJD";

    // Constructor privado para Singleton
    private ConexionBD() {
        conectar();
    }

    // Método para conectar a la base de datos
    private void conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("Conexión establecida: " + meta.getDriverName());
                System.out.println("Usuario: " + user);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,
                    "Driver Oracle no encontrado. Agrega ojdbc.jar al proyecto.", ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,
                    "Error al conectar con la base de datos PROYECTOJD", ex);
        }
    }

    // Método Singleton
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    // Devuelve el Connection para hacer consultas
    public Connection getConnection() {
        return connection;
    }

    // Cierra la conexión
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                instancia = null;
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,
                    "Error al cerrar la conexión", ex);
        }
    }
}
