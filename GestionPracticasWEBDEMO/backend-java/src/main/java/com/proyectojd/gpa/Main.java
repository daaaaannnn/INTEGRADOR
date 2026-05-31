package com.proyectojd.gpa;

import com.proyectojd.gpa.config.AppConfig;
import com.proyectojd.gpa.config.ConexionBD;
import com.proyectojd.gpa.dao.CrudDAO;
import com.proyectojd.gpa.dao.UsuarioDAO;
import com.proyectojd.gpa.http.AuthHandler;
import com.proyectojd.gpa.http.CorsFilter;
import com.proyectojd.gpa.http.CrudHandler;
import com.proyectojd.gpa.http.ProfileHandler;
import com.proyectojd.gpa.model.EntitySchema;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Servidor HTTP ligero para la versión web del sistema.
 * Se eligió HttpServer para evitar frameworks pesados y reducir consumo de recursos.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AppConfig config = new AppConfig();
        ConexionBD conexionBD = new ConexionBD(config);
        CrudDAO crudDAO = new CrudDAO(conexionBD);
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);

        HttpServer server = HttpServer.create(new InetSocketAddress(config.serverPort()), 0);
        CorsFilter cors = new CorsFilter(config.corsOrigin());

        HttpContext health = server.createContext("/api/health", exchange -> {
            String respuesta = "{\"ok\":true,\"message\":\"API Gestión de Prácticas activa\"}";
            byte[] data = respuesta.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, data.length);
            exchange.getResponseBody().write(data);
            exchange.close();
        });
        health.getFilters().add(cors);

        add(server, "/api/auth/login", new AuthHandler(usuarioDAO), cors);
        add(server, "/api/perfil", new ProfileHandler(usuarioDAO), cors);

        add(server, "/api/usuarios", new CrudHandler(crudDAO, new EntitySchema(
                "USUARIO", "id_usuario", true,
                "nombre", "apellido", "tipo_documento", "documento", "correo_electronico", "telefono", "rol", "estado", "foto_perfil_url", "comentario_perfil", "contrasena_hash"
        )), cors);
        add(server, "/api/usuarios/estado", new CrudHandler(crudDAO, new EntitySchema(
                "USUARIO", "id_usuario", true,
                "nombre", "apellido", "tipo_documento", "documento", "correo_electronico", "telefono", "rol", "estado", "foto_perfil_url", "comentario_perfil", "contrasena_hash"
        )), cors);
        add(server, "/api/practicas", new CrudHandler(crudDAO, new EntitySchema(
                "PRACTICA", "id_practica", true,
                "id_programa", "nombre", "descripcion", "objetivos", "horas_reglamentarias", "tipo_modalidad", "numero_practica", "fecha_inicio", "fecha_fin", "estado"
        )), cors);
        add(server, "/api/practicas/estado", new CrudHandler(crudDAO, new EntitySchema(
                "PRACTICA", "id_practica", true,
                "id_programa", "nombre", "descripcion", "objetivos", "horas_reglamentarias", "tipo_modalidad", "numero_practica", "fecha_inicio", "fecha_fin", "estado"
        )), cors);
        add(server, "/api/cursos", new CrudHandler(crudDAO, new EntitySchema(
                "CURSO", "id_curso", true,
                "id_programa", "nombre", "codigo", "semestre", "estado"
        )), cors);
        add(server, "/api/cursos/estado", new CrudHandler(crudDAO, new EntitySchema(
                "CURSO", "id_curso", true,
                "id_programa", "nombre", "codigo", "semestre", "estado"
        )), cors);
        add(server, "/api/instituciones", new CrudHandler(crudDAO, new EntitySchema(
                "INSTITUCION", "id_institucion", true,
                "nombre", "direccion", "telefono", "contacto_nombre", "fecha_inicio_convenio", "fecha_fin_convenio", "nit", "ciudad", "representante", "email", "sitio_web", "logo_url", "estado"
        )), cors);
        add(server, "/api/instituciones/estado", new CrudHandler(crudDAO, new EntitySchema(
                "INSTITUCION", "id_institucion", true,
                "nombre", "direccion", "telefono", "contacto_nombre", "fecha_inicio_convenio", "fecha_fin_convenio", "nit", "ciudad", "representante", "email", "sitio_web", "logo_url", "estado"
        )), cors);
        add(server, "/api/grupos", new CrudHandler(crudDAO, new EntitySchema(
                "GRUPO", "id_grupo", true,
                "id_curso", "id_practica", "id_institucion", "id_docente", "nombre_grupo", "periodo_academico", "fecha_inicio", "fecha_fin", "cupo_maximo", "estado"
        )), cors);
        add(server, "/api/grupos/estado", new CrudHandler(crudDAO, new EntitySchema(
                "GRUPO", "id_grupo", true,
                "id_curso", "id_practica", "id_institucion", "id_docente", "nombre_grupo", "periodo_academico", "fecha_inicio", "fecha_fin", "cupo_maximo", "estado"
        )), cors);
        EntitySchema asignacionSchema = new EntitySchema(
                "ASIGNACION_DOCENTE", "id_asignacion_docente", true,
                "id_usuario", "id_grupo", "fecha_asignacion", "id_grupo_practica", "estado"
        );
        add(server, "/api/asignaciones", new CrudHandler(crudDAO, asignacionSchema), cors);
        add(server, "/api/asignaciones/estado", new CrudHandler(crudDAO, asignacionSchema), cors);
        EntitySchema actividadSchema = new EntitySchema(
                "REGISTRO_ACTIVIDAD", "id_actividad", true,
                "id_usuario", "id_institucion", "id_matricula_practica", "fecha_actividad", "descripcion", "id_evidencia", "horas", "tipo_actividad", "estado", "comentarios"
        );
        add(server, "/api/actividades", new CrudHandler(crudDAO, actividadSchema), cors);
        add(server, "/api/actividades/estado", new CrudHandler(crudDAO, actividadSchema), cors);
        add(server, "/api/mensajes", new CrudHandler(crudDAO, new EntitySchema(
                "MENSAJE_INTERNO", "id_mensaje", false,
                "id_remitente", "id_destinatario", "asunto", "mensaje", "leido"
        )), cors);
        EntitySchema notificacionSchema = new EntitySchema(
                "NOTIFICACION", "id_notificacion", true,
                "id_usuario", "titulo", "mensaje", "tipo", "estado"
        );
        add(server, "/api/notificaciones", new CrudHandler(crudDAO, notificacionSchema), cors);
        add(server, "/api/notificaciones/estado", new CrudHandler(crudDAO, notificacionSchema), cors);
        EntitySchema convenioSchema = new EntitySchema(
                "CONVENIO_INSTITUCION", "id_convenio", true,
                "id_institucion", "numero_convenio", "fecha_inicio", "fecha_fin", "objeto", "estado", "documento_url"
        );
        add(server, "/api/convenios", new CrudHandler(crudDAO, convenioSchema), cors);
        add(server, "/api/convenios/estado", new CrudHandler(crudDAO, convenioSchema), cors);
        add(server, "/api/compatibilidad", new CrudHandler(crudDAO, new EntitySchema(
                "COMPATIBILIDAD_INSTITUCION", "id_compatibilidad", false,
                "id_usuario", "id_institucion", "porcentaje", "criterios"
        )), cors);
        add(server, "/api/historial", new CrudHandler(crudDAO, new EntitySchema(
                "HISTORIAL_REVISION", "id_historial", false,
                "entidad", "id_entidad", "id_usuario", "accion", "descripcion"
        )), cors);

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("API Gestión de Prácticas ejecutándose en http://localhost:" + config.serverPort());
    }

    private static void add(HttpServer server, String path, com.sun.net.httpserver.HttpHandler handler, CorsFilter cors) {
        HttpContext context = server.createContext(path, handler);
        context.getFilters().add(cors);
    }
}
