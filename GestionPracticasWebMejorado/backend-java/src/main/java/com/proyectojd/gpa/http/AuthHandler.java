package com.proyectojd.gpa.http;

import com.proyectojd.gpa.dao.UsuarioDAO;
import com.proyectojd.gpa.model.UsuarioSesion;
import com.proyectojd.gpa.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Endpoint de login: POST /api/auth/login
 */
public class AuthHandler implements HttpHandler {
    private final UsuarioDAO usuarioDAO;

    public AuthHandler(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                JsonUtil.send(exchange, 405, JsonUtil.error("Método no permitido"));
                return;
            }
            Map<String, Object> body = JsonUtil.readMap(exchange);
            String correo = String.valueOf(body.getOrDefault("correo", ""));
            String password = String.valueOf(body.getOrDefault("password", ""));
            Optional<UsuarioSesion> sesion = usuarioDAO.autenticar(correo, password);
            if (sesion.isEmpty()) {
                JsonUtil.send(exchange, 401, JsonUtil.error("Credenciales inválidas o usuario inactivo"));
                return;
            }
            JsonUtil.send(exchange, 200, JsonUtil.ok(sesion.get()));
        } catch (Exception error) {
            JsonUtil.send(exchange, 500, JsonUtil.error(error.getMessage()));
        }
    }
}
