package com.gestionpracticas.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static Connection con;

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USUARIO = "PROYECTOJD";
    private static final String PASSWORD = "PROYECTOJD";

    public static Connection getConnection() {

        try {

            if (con == null || con.isClosed()) {

                Class.forName("oracle.jdbc.driver.OracleDriver");

                con = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    PASSWORD
                );

                System.out.println("Conexion Oracle LOCAL OK");
            }

        } catch (Exception e) {

            System.out.println("No se pudo conectar a Oracle local.");
            e.printStackTrace();
        }

        return con;
    }
}