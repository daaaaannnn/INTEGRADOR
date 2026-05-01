package gestionpracticas.dao;

import com.gestionpracticas.modelo.Programa;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDAO {
    private final Connection con;
    public ProgramaDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(Programa p) {
        String sql = "INSERT INTO PROGRAMA (ID_PROGRAMA, NOMBRE, CODIGO, DESCRIPCION, DURACION_SEMESTRES, ESTADO) VALUES (SEQ_PROGRAMA.NEXTVAL,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre()); ps.setString(2, p.getCodigo());
            ps.setString(3, p.getDescripcion()); ps.setInt(4, p.getDuracionSemestres());
            ps.setString(5, p.getEstado());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<Programa> listarTodos() {
        List<Programa> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM PROGRAMA ORDER BY ID_PROGRAMA");
            rs = ps.executeQuery();
            while (rs.next()) {
                Programa p = new Programa();
                p.setIdPrograma(rs.getInt("ID_PROGRAMA")); p.setNombre(rs.getString("NOMBRE"));
                p.setCodigo(rs.getString("CODIGO")); p.setDescripcion(rs.getString("DESCRIPCION"));
                p.setDuracionSemestres(rs.getInt("DURACION_SEMESTRES")); p.setEstado(rs.getString("ESTADO"));
                lista.add(p);
            }
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean actualizar(Programa p) {
        String sql = "UPDATE PROGRAMA SET NOMBRE=?, CODIGO=?, DESCRIPCION=?, DURACION_SEMESTRES=?, ESTADO=? WHERE ID_PROGRAMA=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre()); ps.setString(2, p.getCodigo());
            ps.setString(3, p.getDescripcion()); ps.setInt(4, p.getDuracionSemestres());
            ps.setString(5, p.getEstado()); ps.setInt(6, p.getIdPrograma());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE PROGRAMA SET ESTADO='INACTIVO' WHERE ID_PROGRAMA=?");
            ps.setInt(1, id); ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}
