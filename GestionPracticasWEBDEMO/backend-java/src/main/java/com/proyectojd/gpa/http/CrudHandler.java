package com.proyectojd.gpa.http;

import com.proyectojd.gpa.dao.CrudDAO;
import com.proyectojd.gpa.model.EntitySchema;
import com.proyectojd.gpa.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * Handler REST genérico por entidad.
 * Soporta:
 * GET    /api/entidad
 * GET    /api/entidad?id=1
 * POST   /api/entidad
 * PUT    /api/entidad?id=1
 * DELETE /api/entidad?id=1
 * PUT    /api/entidad/estado?id=1 con {"estado":"ACTIVO"}
 */
public class CrudHandler implements HttpHandler {
    private final CrudDAO dao;
    private final EntitySchema schema;

    public CrudHandler(CrudDAO dao, EntitySchema schema) {
        this.dao = dao;
        this.schema = schema;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod().toUpperCase();
            URI uri = exchange.getRequestURI();
            Map<String, String> query = QueryParams.parse(uri.getRawQuery());
            boolean estadoRoute = uri.getPath().endsWith("/estado");

            if ("GET".equals(method)) {
                if (query.containsKey("id")) {
                    JsonUtil.send(exchange, 200, JsonUtil.ok(dao.obtener(schema, Long.parseLong(query.get("id")))));
                } else if (query.containsKey("campo") && query.containsKey("valor")) {
                    JsonUtil.send(exchange, 200, JsonUtil.ok(dao.buscarPorCampo(schema, query.get("campo"), query.get("valor"))));
                } else {
                    JsonUtil.send(exchange, 200, JsonUtil.ok(dao.listar(schema)));
                }
                return;
            }

            if ("POST".equals(method)) {
                int rows = dao.crear(schema, JsonUtil.readMap(exchange));
                JsonUtil.send(exchange, 201, JsonUtil.ok(Map.of("filas", rows)));
                return;
            }

            if ("PUT".equals(method) && estadoRoute) {
                long id = Long.parseLong(query.get("id"));
                String estado = String.valueOf(JsonUtil.readMap(exchange).getOrDefault("estado", "ACTIVO"));
                int rows = dao.cambiarEstado(schema, id, estado);
                JsonUtil.send(exchange, 200, JsonUtil.ok(Map.of("filas", rows)));
                return;
            }

            if ("PUT".equals(method)) {
                long id = Long.parseLong(query.get("id"));
                int rows = dao.actualizar(schema, id, JsonUtil.readMap(exchange));
                JsonUtil.send(exchange, 200, JsonUtil.ok(Map.of("filas", rows)));
                return;
            }

            if ("DELETE".equals(method)) {
                long id = Long.parseLong(query.get("id"));
                int rows = dao.eliminar(schema, id);
                JsonUtil.send(exchange, 200, JsonUtil.ok(Map.of("filas", rows)));
                return;
            }

            JsonUtil.send(exchange, 405, JsonUtil.error("Método no permitido"));
        } catch (Exception error) {
            JsonUtil.send(exchange, 500, JsonUtil.error(error.getMessage()));
        }
    }
}
