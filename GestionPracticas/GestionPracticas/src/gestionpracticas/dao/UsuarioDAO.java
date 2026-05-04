package gestionpracticas.dao;

import gestionpracticas.modelo.Usuario;
import gestionpracticas.util.ConexionBD;
import java.sql.*;

public class UsuarioDAO {
    
    private final Connection con;
    
    public UsuarioDAO() {
        this.con = ConexionBD.getInstancia().getConnection();
    }
    
    public Usuario validarLogin(String email, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND ESTADO = 'ACTIVO'";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String passBD = rs.getString("PASSWORD");
                if (passBD.equals(password)) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("ID_USUARIO"));
                    u.setNombre(rs.getString("NOMBRE"));
                    u.setApellido(rs.getString("APELLIDO"));
                    u.setEmail(rs.getString("EMAIL"));
                    u.setPassword(rs.getString("PASSWORD"));
                    u.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                    u.setEstado(rs.getString("ESTADO"));
                    return u;
                }
            }
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error en validarLogin: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {}
        }
    }
}