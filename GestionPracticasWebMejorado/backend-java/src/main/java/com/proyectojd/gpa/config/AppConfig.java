package com.proyectojd.gpa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centraliza la lectura de configuración del backend.
 * Permite usar variables de entorno para no dejar datos sensibles fijos en el código.
 */
public class AppConfig {
    private final Properties properties = new Properties();

    public AppConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException error) {
            throw new IllegalStateException("No fue posible cargar application.properties", error);
        }
    }

    public String dbUrl() {
        return get("DB_URL", "db.url", "jdbc:oracle:thin:@localhost:1521:orcl");
    }

    public String dbUser() {
        return get("DB_USER", "db.user", "proyectojd");
    }

    public String dbPassword() {
        return get("DB_PASSWORD", "db.password", "proyectojd");
    }

    public int serverPort() {
        return Integer.parseInt(get("SERVER_PORT", "server.port", "8080"));
    }

    public String corsOrigin() {
        return get("CORS_ORIGIN", "cors.origin", "*");
    }

    private String get(String envName, String propertyName, String defaultValue) {
        String env = System.getenv(envName);
        if (env != null && !env.isBlank()) {
            return env;
        }
        return properties.getProperty(propertyName, defaultValue);
    }
}
