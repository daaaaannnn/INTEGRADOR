package com.gestionpracticas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static Connection con;

    private static final String USUARIO = "PROYECTOJD";
    private static final String PASSWORD = "PROYECTOJD";

    private static final String[] URLS = {
        "jdbc:oracle:thin:@JESUS:1521:XE",
        "jdbc:oracle:thin:@//JESUS:1521/XE",
        "jdbc:oracle:thin:@192.168.196.28:1521:XE",
        "jdbc:oracle:thin:@//192.168.196.28:1521/XE",
        "jdbc:oracle:thin:@localhost:1521:XE",
        "jdbc:oracle:thin:@//localhost:1521/XE",
        "jdbc:oracle:thin:@127.0.0.1:1521:XE",
        "jdbc:oracle:thin:@//127.0.0.1:1521/XE"
    };

    public static Connection getConnection() {

        try {
            if (con != null && !con.isClosed()) {
                return con;
            }

            Class.forName("oracle.jdbc.driver.OracleDriver");

            for (String url : URLS) {
                try {
                    System.out.println("Intentando conectar con: " + url);

                    con = DriverManager.getConnection(
                        url,
                        USUARIO,
                        PASSWORD
                    );

                    System.out.println("Conexion Oracle OK con: " + url);
                    return con;

                } catch (SQLException e) {
                    System.out.println("No conecto con: " + url);
                    System.out.println("Detalle: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("Error general de conexion Oracle.");
            e.printStackTrace();
        }

        return null;
    }
}