package gestionpracticas;

import gestionpracticas.util.ConexionBD;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE PRÁCTICAS - PROYECTOJD");
        System.out.println("===========================================");
        
        Connection conn = ConexionBD.getInstancia().getConnection();
        
        if (conn != null) {
            System.out.println("✅ Conexión establecida correctamente");
        } else {
            System.out.println("❌ No se pudo conectar a la base de datos");
        }
    }
}