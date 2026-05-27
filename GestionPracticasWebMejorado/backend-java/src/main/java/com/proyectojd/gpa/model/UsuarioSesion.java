package com.proyectojd.gpa.model;

/**
 * Objeto de sesión que se devuelve al frontend después del login.
 */
public record UsuarioSesion(
        long idUsuario,
        String nombre,
        String apellido,
        String correoElectronico,
        String rol,
        String estado,
        String fotoPerfilUrl,
        String comentarioPerfil
) {}
