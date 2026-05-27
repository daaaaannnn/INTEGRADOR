package com.proyectojd.gpa.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fábrica de conexiones JDBC para Oracle.
 * Cada DAO pide una conexión nueva y la cierra con try-with-resources.
 */
public class ConexionBD {
    private final AppConfig config;

    public ConexionBD(AppConfig config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.dbUrl(), config.dbUser(), config.dbPassword());
    }
}
