package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.MatriculaPractica;
import gestionpracticas.modelo.Practica;
import gestionpracticas.modelo.Usuario;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaPracticaDAO {
    private final Connection con;
    public MatriculaPracticaDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(MatriculaPractica m) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO MATRICULA_PRACTICA (ID_MATRICULA, ID_ESTUDIANTE, ID_PRACTICA, FECHA_MATRICULA, ESTADO, OBSERVACIONES) VALUES (SEQ_MATRICULA_PRACTICA.NEXTVAL,?,?,SYSDATE,?,?)");
            ps.setInt(1, m.getEstudiante().getIdUsuario()); ps.setInt(2, m.getPractica().getIdPractica());
            ps.setString(3, m.getEstado()); ps.setString(4, m.getObservaciones());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error MatriculaDAO.insertar: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<MatriculaPractica> listarPorEstudiante(int idEstudiante) {
        List<MatriculaPractica> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT M.*, U.NOMBRE, U.APELLIDO, P.TITULO FROM MATRICULA_PRACTICA M JOIN USUARIO U ON M.ID_ESTUDIANTE=U.ID_USUARIO JOIN PRACTICA P ON M.ID_PRACTICA=P.ID_PRACTICA WHERE M.ID_ESTUDIANTE=?");
            ps.setInt(1, idEstudiante); rs = ps.executeQuery();
            while (rs.next()) {
                MatriculaPractica m = new MatriculaPractica();
                m.setIdMatricula(rs.getInt("ID_MATRICULA")); m.setEstado(rs.getString("ESTADO")); m.setObservaciones(rs.getString("OBSERVACIONES")); m.setFechaMatricula(rs.getDate("FECHA_MATRICULA"));
                Usuario u = new Usuario(); u.setIdUsuario(rs.getInt("ID_ESTUDIANTE")); u.setNombre(rs.getString("NOMBRE")); u.setApellido(rs.getString("APELLIDO")); m.setEstudiante(u);
                Practica p = new Practica(); p.setIdPractica(rs.getInt("ID_PRACTICA")); p.setTitulo(rs.getString("TITULO")); m.setPractica(p);
                lista.add(m);
            }
        } catch (SQLException e) { System.err.println("Error MatriculaDAO.listar: " + e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean actualizarEstado(int idMatricula, String estado) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE MATRICULA_PRACTICA SET ESTADO=? WHERE ID_MATRICULA=?");
            ps.setString(1, estado); ps.setInt(2, idMatricula);
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error MatriculaDAO.actualizarEstado: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}