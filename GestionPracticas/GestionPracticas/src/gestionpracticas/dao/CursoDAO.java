package gestionpracticas.dao;

// CORREGIDO: Todos los imports del mismo paquete
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
        String sql = "INSERT INTO CURSO (ID_CURSO, NOMBRE, CODIGO, CREDITOS, ID_PROGRAMA, DESCRIPCION, ESTADO) " +
                     "VALUES (SEQ_CURSO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return false;
        }
        
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre()); 
            ps.setString(2, c.getCodigo());
            ps.setInt(3, c.getCreditos());

            if (c.getPrograma() == null) {
                throw new SQLException("El curso no tiene programa asignado");
            }

            // CORREGIDO: El método es getIdPrograma(), no getidPrograma()
            int idPrograma;
            idPrograma = c.getPrograma().getidPrograma();
            if (idPrograma <= 0) {
                throw new SQLException("ID de programa inválido: " + idPrograma);
            }
            ps.setInt(4, idPrograma);
            
            ps.setString(5, c.getDescripcion()); 
            ps.setString(6, c.getEstado());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.insertar: " + e.getMessage());
            return false; 
        } finally { 
            Utilidades.cerrar(ps); 
        }
    }

    public List<Curso> listarTodos() {
        List<Curso> lista = new ArrayList<>();
        PreparedStatement ps = null; 
        ResultSet rs = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return lista;
        }

        try {
            String sql = "SELECT C.*, P.NOMBRE AS NOM_PROG FROM CURSO C " +
                         "INNER JOIN PROGRAMA P ON C.ID_PROGRAMA = P.ID_PROGRAMA " +
                         "ORDER BY C.ID_CURSO";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) { 
                Curso curso = mapear(rs);
                if (curso != null) {
                    lista.add(curso);
                }
            }

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.listarTodos: " + e.getMessage());
        } finally { 
            Utilidades.cerrar(rs); 
            Utilidades.cerrar(ps); 
        }

        return lista;
    }
    
    public List<Curso> listarActivos() {
        List<Curso> lista = new ArrayList<>();
        PreparedStatement ps = null; 
        ResultSet rs = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return lista;
        }

        try {
            String sql = "SELECT C.*, P.NOMBRE AS NOM_PROG FROM CURSO C " +
                         "INNER JOIN PROGRAMA P ON C.ID_PROGRAMA = P.ID_PROGRAMA " +
                         "WHERE C.ESTADO = 'ACTIVO' " +
                         "ORDER BY C.NOMBRE";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) { 
                lista.add(mapear(rs)); 
            }

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.listarActivos: " + e.getMessage()); 
        } finally { 
            Utilidades.cerrar(rs); 
            Utilidades.cerrar(ps); 
        }

        return lista;
    }

    public Curso buscarPorId(int id) {
        PreparedStatement ps = null; 
        ResultSet rs = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return null;
        }

        try {
            String sql = "SELECT C.*, P.NOMBRE AS NOM_PROG FROM CURSO C " +
                         "INNER JOIN PROGRAMA P ON C.ID_PROGRAMA = P.ID_PROGRAMA " +
                         "WHERE C.ID_CURSO = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.buscarPorId: " + e.getMessage()); 
        } finally { 
            Utilidades.cerrar(rs); 
            Utilidades.cerrar(ps); 
        }

        return null;
    }
    
    public List<Curso> buscarPorPrograma(int idPrograma) {
        List<Curso> lista = new ArrayList<>();
        PreparedStatement ps = null; 
        ResultSet rs = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return lista;
        }

        try {
            String sql = "SELECT C.*, P.NOMBRE AS NOM_PROG FROM CURSO C " +
                         "INNER JOIN PROGRAMA P ON C.ID_PROGRAMA = P.ID_PROGRAMA " +
                         "WHERE C.ID_PROGRAMA = ? " +
                         "ORDER BY C.NOMBRE";

            ps = con.prepareStatement(sql);
            ps.setInt(1, idPrograma);
            rs = ps.executeQuery();

            while (rs.next()) { 
                lista.add(mapear(rs)); 
            }

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.buscarPorPrograma: " + e.getMessage()); 
        } finally { 
            Utilidades.cerrar(rs); 
            Utilidades.cerrar(ps); 
        }

        return lista;
    }

    public boolean actualizar(Curso c) {
        PreparedStatement ps = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return false;
        }

        try {
            String sql = "UPDATE CURSO SET NOMBRE=?, CODIGO=?, CREDITOS=?, " +
                         "ID_PROGRAMA=?, DESCRIPCION=?, ESTADO=? WHERE ID_CURSO=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());      
            ps.setString(2, c.getCodigo());      
            ps.setInt(3, c.getCreditos());       

            if (c.getPrograma() == null) {
                throw new SQLException("El curso no tiene programa asignado");
            }

            int idPrograma = c.getPrograma().getIdPrograma();
            if (idPrograma <= 0) {
                throw new SQLException("ID de programa inválido: " + idPrograma);
            }
            ps.setInt(4, idPrograma);
            
            ps.setString(5, c.getDescripcion());
            ps.setString(6, c.getEstado());
            ps.setInt(7, c.getIdCurso());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.actualizar: " + e.getMessage());
            return false; 
        } finally { 
            Utilidades.cerrar(ps);
        }
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return false;
        }

        try {
            String sql = "UPDATE CURSO SET ESTADO='INACTIVO' WHERE ID_CURSO=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id); 
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.eliminar: " + e.getMessage()); 
            return false; 
        } finally { 
            Utilidades.cerrar(ps);
        }
    }
    
    public boolean eliminarFisico(int id) {
        PreparedStatement ps = null;

        if (con == null) {
            System.err.println("Error: No hay conexión a la base de datos");
            return false;
        }

        try {
            String sql = "DELETE FROM CURSO WHERE ID_CURSO=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id); 
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { 
            System.err.println("Error CursoDAO.eliminarFisico: " + e.getMessage()); 
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