package gestionpracticas.dao;

import gestionpracticas.modelo.Usuario;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection con;

    public UsuarioDAO() {
        this.con = ConexionBD.getInstancia().getConnection();
    }

    // ── INSERT ─────────────────────────────────────────────────
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO USUARIO (ID_USUARIO, NOMBRE, APELLIDO, EMAIL, CONTRASENA, " +
                     "TIPO_USUARIO, TELEFONO, DOCUMENTO, ESTADO, FECHA_REGISTRO) " +
                     "VALUES (SEQ_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getTipoUsuario());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getDocumento());
            ps.setString(8, u.getEstado());
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertando usuario: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            Utilidades.cerrar(ps);
        }
    }

    // ── SELECT ALL ─────────────────────────────────────────────
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO ORDER BY ID_USUARIO";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listando usuarios: " + e.getMessage());
        } finally {
            Utilidades.cerrar(rs);
            Utilidades.cerrar(ps);
        }
        return lista;
    }

    // ── SELECT BY ID ───────────────────────────────────────────
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM USUARIO WHERE ID_USUARIO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error buscando usuario: " + e.getMessage());
        } finally {
            Utilidades.cerrar(rs);
            Utilidades.cerrar(ps);
        }
        return null;
    }

    // ── SELECT BY EMAIL Y CONTRASEÑA (Login) ───────────────────
    public Usuario login(String email, String contrasena) {
        String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND CONTRASENA = ? AND ESTADO = 'ACTIVO'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        } finally {
            Utilidades.cerrar(rs);
            Utilidades.cerrar(ps);
        }
        return null;
    }

    // ── UPDATE ─────────────────────────────────────────────────
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE USUARIO SET NOMBRE=?, APELLIDO=?, EMAIL=?, TELEFONO=?, " +
                     "TIPO_USUARIO=?, ESTADO=? WHERE ID_USUARIO=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getTipoUsuario());
            ps.setString(6, u.getEstado());
            ps.setInt(7, u.getIdUsuario());
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error actualizando usuario: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            Utilidades.cerrar(ps);
        }
    }

    // ── DELETE (lógico) ────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "UPDATE USUARIO SET ESTADO = 'INACTIVO' WHERE ID_USUARIO = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error eliminando usuario: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            Utilidades.cerrar(ps);
        }
    }

    // ── Mapeo ResultSet → Usuario ──────────────────────────────
    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("ID_USUARIO"));
        u.setNombre(rs.getString("NOMBRE"));
        u.setApellido(rs.getString("APELLIDO"));
        u.setEmail(rs.getString("EMAIL"));
        u.setContrasena(rs.getString("CONTRASENA"));
        u.setTipoUsuario(rs.getString("TIPO_USUARIO"));
        u.setTelefono(rs.getString("TELEFONO"));
        u.setDocumento(rs.getString("DOCUMENTO"));
        u.setEstado(rs.getString("ESTADO"));
        u.setFechaRegistro(rs.getDate("FECHA_REGISTRO"));
        return u;
    }
}
