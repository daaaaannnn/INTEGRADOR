package gestionpracticas.dao;

import com.gestionpracticas.modelo.Programa;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.Curso;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    private final Connection con;

    public CursoDAO() { 
        this.con = ConexionBD.getInstancia().getConnection(); 
    }

    public boolean insertar(Curso c) {
        String sql = "INSERT INTO CURSO (ID_CURSO, NOMBRE, CODIGO, CREDITOS, ID_PROGRAMA, DESCRIPCION, ESTADO) VALUES (SEQ_CURSO.NEXTVAL,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre()); 
            ps.setString(2, c.getCodigo());
            ps.setInt(3, c.getCreditos());

            // VALIDACIÓN IMPORTANTE
            if (c.getPrograma() == null) {
                throw new SQLException("El curso no tiene programa asignado");
            }

            ps.setInt(4, c.getPrograma().getIdPrograma());

            ps.setString(5, c.getDescripcion()); 
            ps.setString(6, c.getEstado());

            ps.executeUpdate(); 
            con.commit(); 
            return true;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.insertar: " + e.getMessage()); 
            try { con.rollback(); } catch (SQLException ex) {} 
            return false; 
        } finally { 
            Utilidades.cerrar(ps); 
        }
    }

    public List<Curso> listarTodos() {
        List<Curso> lista = new ArrayList<>();
        PreparedStatement ps = null; 
        ResultSet rs = null;

        try {
            String sql = "SELECT C.*, P.NOMBRE AS NOM_PROG FROM CURSO C " +
                         "JOIN PROGRAMA P ON C.ID_PROGRAMA = P.ID_PROGRAMA " +
                         "ORDER BY C.ID_CURSO";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) { 
                lista.add(mapear(rs)); 
            }

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.listar: " + e.getMessage()); 
        } finally { 
            Utilidades.cerrar(rs); 
            Utilidades.cerrar(ps); 
        }

        return lista;
    }

    public Curso buscarPorId(int id) {
        PreparedStatement ps = null; 
        ResultSet rs = null;

        try {
            String sql = "SELECT C.*, P.NOMBRE AS NOM_PROG FROM CURSO C " +
                         "JOIN PROGRAMA P ON C.ID_PROGRAMA = P.ID_PROGRAMA " +
                         "WHERE C.ID_CURSO = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.buscar: " + e.getMessage()); 
        } finally { 
            Utilidades.cerrar(rs); 
            Utilidades.cerrar(ps); 
        }

        return null;
    }

    public boolean actualizar(Curso c) {
        PreparedStatement ps = null;

        try {
            String sql = "UPDATE CURSO SET NOMBRE=?, CODIGO=?, CREDITOS=?, ID_PROGRAMA=?, DESCRIPCION=?, ESTADO=? WHERE ID_CURSO=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre()); 
            ps.setString(2, c.getCodigo()); 
            ps.setInt(3, c.getCreditos());

            // VALIDACIÓN IMPORTANTE
            if (c.getPrograma() == null) {
                throw new SQLException("El curso no tiene programa asignado");
            }

            ps.setInt(4, c.getPrograma().getIdPrograma());

            ps.setString(5, c.getDescripcion());
            ps.setString(6, c.getEstado()); 
            ps.setInt(7, c.getIdCurso());

            ps.executeUpdate(); 
            con.commit(); 
            return true;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.actualizar: " + e.getMessage()); 
            try { con.rollback(); } catch (SQLException ex) {} 
            return false; 
        } finally { 
            Utilidades.cerrar(ps); 
        }
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;

        try {
            String sql = "UPDATE CURSO SET ESTADO='INACTIVO' WHERE ID_CURSO=?";
            ps = con.prepareStatement(sql);

            ps.setInt(1, id); 
            ps.executeUpdate(); 
            con.commit(); 
            return true;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.eliminar: " + e.getMessage()); 
            try { con.rollback(); } catch (SQLException ex) {} 
            return false; 
        } finally { 
            Utilidades.cerrar(ps); 
        }
    }

    private Curso mapear(ResultSet rs) throws SQLException {
        Curso c = new Curso();

        c.setIdCurso(rs.getInt("ID_CURSO")); 
        c.setNombre(rs.getString("NOMBRE"));
        c.setCodigo(rs.getString("CODIGO")); 
        c.setCreditos(rs.getInt("CREDITOS"));
        c.setDescripcion(rs.getString("DESCRIPCION")); 
        c.setEstado(rs.getString("ESTADO"));

        Programa p = new Programa(); 
        p.setIdPrograma(rs.getInt("ID_PROGRAMA")); 
        p.setNombre(rs.getString("NOM_PROG"));

        c.setPrograma(p); 

        return c;
    }
}