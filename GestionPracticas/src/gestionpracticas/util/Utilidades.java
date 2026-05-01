package com.gestionpracticas.util;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    // Formato de fecha estándar del proyecto
    private static final String FORMATO_FECHA = "dd/MM/yyyy";

    // Cierra un ResultSet de forma segura
    public static void cerrar(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando ResultSet: " + e.getMessage());
        }
    }

    // Cierra un PreparedStatement de forma segura
    public static void cerrar(PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
        }
    }

    // Convierte una fecha a String
    public static String fechaAString(Date fecha) {
        if (fecha == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.format(fecha);
    }

    // Valida que un String no sea nulo o vacío
    public static boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    // Valida que un email tenga formato correcto
    public static boolean esEmailValido(String email) {
        if (esVacio(email)) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Capitaliza la primera letra de un String
    public static String capitalizar(String texto) {
        if (esVacio(texto)) return "";
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
