package com.proyectojd.gpa.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Filtro CORS para permitir que el frontend consuma el backend desde navegador.
 */
public class CorsFilter extends Filter {
    private final String origin;

    public CorsFilter(String origin) {
        this.origin = origin;
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", origin);
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization");
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return "CORS filter";
    }
}
