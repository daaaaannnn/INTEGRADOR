package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.AsignacionDocente;
import gestionpracticas.modelo.Practica;
import gestionpracticas.modelo.Usuario;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDocenteDAO {
    private final Connection con;
    public AsignacionDocenteDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(AsignacionDocente a) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO ASIGNACION_DOCENTE (ID_ASIGNACION_DOCENTE,FECHA_ASIGNACION,ROL_EN_PRACTICA,ID_PRACTICA,ID_USUARIO) VALUES (SEQ_ASIGNACION_DOCENTE.NEXTVAL,SYSDATE,?,?,?)");
            ps.setString(1,a.getRolEnPractica()); ps.setInt(2,a.getPractica().getIdPractica()); ps.setInt(3,a.getUsuarios().get(0).getIdUsuario());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error AsignacionDAO: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<AsignacionDocente> listarPorPractica(int idPractica) {
        List<AsignacionDocente> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT A.*, U.NOMBRE, U.APELLIDO, P.TITULO FROM ASIGNACION_DOCENTE A JOIN USUARIO U ON A.ID_USUARIO=U.ID_USUARIO JOIN PRACTICA P ON A.ID_PRACTICA=P.ID_PRACTICA WHERE A.ID_PRACTICA=?");
            ps.setInt(1,idPractica); rs = ps.executeQuery();
            while (rs.next()) {
                AsignacionDocente a = new AsignacionDocente();
                a.setIdAsignacionDocente(rs.getInt("ID_ASIGNACION_DOCENTE")); a.setRolEnPractica(rs.getString("ROL_EN_PRACTICA")); a.setFechaAsignacion(rs.getDate("FECHA_ASIGNACION"));
                Practica p = new Practica(); p.setIdPractica(idPractica); p.setTitulo(rs.getString("TITULO")); a.setPractica(p);
                Usuario u = new Usuario(); u.setIdUsuario(rs.getInt("ID_USUARIO")); u.setNombre(rs.getString("NOMBRE")); u.setApellido(rs.getString("APELLIDO"));
                List<Usuario> usuarios = new ArrayList<>(); usuarios.add(u); a.setUsuarios(usuarios);
                lista.add(a);
            }
        } catch (SQLException e) { System.err.println("Error AsignacionDAO.listar: "+e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM ASIGNACION_DOCENTE WHERE ID_ASIGNACION_DOCENTE=?");
            ps.setInt(1,id); ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error AsignacionDAO.eliminar: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public boolean insertar(com.gestionpracticas.modelo.AsignacionDocente a) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}