package com.proyectojd.gpa.http;

import com.proyectojd.gpa.dao.UsuarioDAO;
import com.proyectojd.gpa.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

/**
 * Endpoint para actualizar perfil y contraseña.
 */
public class ProfileHandler implements HttpHandler {
    private final UsuarioDAO usuarioDAO;

    public ProfileHandler(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (!"PUT".equalsIgnoreCase(exchange.getRequestMethod())) {
                JsonUtil.send(exchange, 405, JsonUtil.error("Método no permitido"));
                return;
            }
            Map<String, String> query = QueryParams.parse(exchange.getRequestURI().getRawQuery());
            long id = Long.parseLong(query.get("id"));
            Map<String, Object> body = JsonUtil.readMap(exchange);
            if (body.containsKey("password")) {
                usuarioDAO.cambiarPassword(id, String.valueOf(body.get("password")));
            }
            if (body.containsKey("foto_perfil_url") || body.containsKey("comentario_perfil")) {
                usuarioDAO.actualizarPerfil(
                        id,
                        String.valueOf(body.getOrDefault("foto_perfil_url", "")),
                        String.valueOf(body.getOrDefault("comentario_perfil", ""))
                );
            }
            JsonUtil.send(exchange, 200, JsonUtil.ok(Map.of("actualizado", true)));
        } catch (Exception error) {
            JsonUtil.send(exchange, 500, JsonUtil.error(error.getMessage()));
        }
    }
}
