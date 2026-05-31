package com.proyectojd.gpa.dao;

import com.proyectojd.gpa.config.ConexionBD;
import com.proyectojd.gpa.model.UsuarioSesion;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * DAO especializado para autenticación y perfil del usuario.
 */
public class UsuarioDAO {
    private final ConexionBD conexionBD;

    public UsuarioDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public Optional<UsuarioSesion> autenticar(String correo, String password) throws SQLException {
        String sql = "SELECT id_usuario, nombre, apellido, correo_electronico, rol, estado, contrasena_hash, foto_perfil_url, comentario_perfil " +
                "FROM USUARIO WHERE LOWER(correo_electronico) = LOWER(?)";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                String estado = rs.getString("estado");
                if (estado != null && estado.equalsIgnoreCase("INACTIVO")) {
                    return Optional.empty();
                }
                String stored = rs.getString("contrasena_hash");
                if (!validarPassword(password, stored)) {
                    return Optional.empty();
                }
                actualizarUltimaSesion(correo);
                return Optional.of(new UsuarioSesion(
                        rs.getLong("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico"),
                        rs.getString("rol"),
                        rs.getString("estado"),
                        rs.getString("foto_perfil_url"),
                        rs.getString("comentario_perfil")
                ));
            }
        }
    }

    public void cambiarPassword(long idUsuario, String nuevaPassword) throws SQLException {
        String hash = nuevaPassword.startsWith("$2") ? nuevaPassword : BCrypt.hashpw(nuevaPassword, BCrypt.gensalt(10));
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement("UPDATE USUARIO SET contrasena_hash = ? WHERE id_usuario = ?")) {
            ps.setString(1, hash);
            ps.setLong(2, idUsuario);
            ps.executeUpdate();
        }
    }

    public void actualizarPerfil(long idUsuario, String fotoPerfilUrl, String comentarioPerfil) throws SQLException {
        String sql = "UPDATE USUARIO SET foto_perfil_url = ?, comentario_perfil = ? WHERE id_usuario = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, fotoPerfilUrl);
            ps.setString(2, comentarioPerfil);
            ps.setLong(3, idUsuario);
            ps.executeUpdate();
        }
    }

    private boolean validarPassword(String password, String stored) {
        if (stored == null || stored.isBlank()) {
            return false;
        }
        if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
            return BCrypt.checkpw(password, stored);
        }
        return stored.equals(password);
    }

    private void actualizarUltimaSesion(String correo) throws SQLException {
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement("UPDATE USUARIO SET fecha_ultima_sesion = SYSDATE WHERE LOWER(correo_electronico) = LOWER(?)")) {
            ps.setString(1, correo);
            ps.executeUpdate();
        }
    }
}
