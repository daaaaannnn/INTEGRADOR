package gestionpracticas.vista.institucion;

import gestionpracticas.util.ConexionBD;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("Probando conexión a Oracle...");
        Connection conn = ConexionBD.getInstancia().getConnection();
        
        if (conn != null) {
            System.out.println("✅ ¡CONEXIÓN EXITOSA!");
        } else {
            System.out.println("❌ Error de conexión");
        }
    }
}