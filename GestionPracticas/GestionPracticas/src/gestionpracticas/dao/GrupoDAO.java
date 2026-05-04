package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.Curso;
import gestionpracticas.modelo.Grupo;
import gestionpracticas.modelo.Usuario;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {
    private final Connection con;
    public GrupoDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(Grupo g) {
        String sql = "INSERT INTO GRUPO (ID_GRUPO, NOMBRE, ID_CURSO, ID_DOCENTE, SEMESTRE, ANIO, ESTADO) VALUES (SEQ_GRUPO.NEXTVAL,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, g.getNombre()); ps.setInt(2,getIdCurso());
            ps.setInt(3, g.getDocente().getIdUsuario()); ps.setString(4, g.getSemestre());
            ps.setInt(5, g.getAnio()); ps.setString(6, g.getEstado());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error GrupoDAO.insertar: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<Grupo> listarTodos() {
        List<Grupo> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT G.*, C.NOMBRE AS NOM_CURSO, U.NOMBRE AS NOM_DOC, U.APELLIDO AS APE_DOC FROM GRUPO G JOIN CURSO C ON G.ID_CURSO=C.ID_CURSO JOIN USUARIO U ON G.ID_DOCENTE=U.ID_USUARIO ORDER BY G.ID_GRUPO");
            rs = ps.executeQuery();
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (SQLException e) { System.err.println("Error GrupoDAO.listar: " + e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public Grupo buscarPorId(int id) {
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT G.*, C.NOMBRE AS NOM_CURSO, U.NOMBRE AS NOM_DOC, U.APELLIDO AS APE_DOC FROM GRUPO G JOIN CURSO C ON G.ID_CURSO=C.ID_CURSO JOIN USUARIO U ON G.ID_DOCENTE=U.ID_USUARIO WHERE G.ID_GRUPO=?");
            ps.setInt(1, id); rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) { System.err.println("Error GrupoDAO.buscar: " + e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return null;
    }

    public boolean actualizar(Grupo g) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE GRUPO SET NOMBRE=?,ID_CURSO=?,ID_DOCENTE=?,SEMESTRE=?,ANIO=?,ESTADO=? WHERE ID_GRUPO=?");
            ps.setString(1, g.getNombre()); ps.setInt(2, g.getCurso().getIdCurso());
            ps.setInt(3, g.getDocente().getIdUsuario()); ps.setString(4, g.getSemestre());
            ps.setInt(5, g.getAnio()); ps.setString(6, g.getEstado()); ps.setInt(7, g.getIdGrupo());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error GrupoDAO.actualizar: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE GRUPO SET ESTADO='INACTIVO' WHERE ID_GRUPO=?");
            ps.setInt(1, id); ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error GrupoDAO.eliminar: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    private Grupo mapear(ResultSet rs) throws SQLException {
        Grupo g = new Grupo();
        g.setIdGrupo(rs.getInt("ID_GRUPO")); g.setNombre(rs.getString("NOMBRE"));
        g.setSemestre(rs.getString("SEMESTRE")); g.setAnio(rs.getInt("ANIO")); g.setEstado(rs.getString("ESTADO"));
        Curso c = new Curso(); c.setIdCurso(rs.getInt("ID_CURSO")); c.setNombre(rs.getString("NOM_CURSO")); g.setCurso(c);
        Usuario u = new Usuario(); u.setIdUsuario(rs.getInt("ID_DOCENTE")); u.setNombre(rs.getString("NOM_DOC")); u.setApellido(rs.getString("APE_DOC")); g.setDocente(u);
        return g;
    }

    private int getIdCurso() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}