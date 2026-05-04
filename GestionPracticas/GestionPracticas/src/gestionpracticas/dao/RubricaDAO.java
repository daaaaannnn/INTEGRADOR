package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.Practica;
import gestionpracticas.modelo.Rubrica;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RubricaDAO {
    private final Connection con;
    public RubricaDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(Rubrica r) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO RUBRICA (ID_RUBRICA,NOMBRE,DESCRIPCION,ID_DOCENTE,ID_PRACTICA,PUNTAJE_TOTAL,FECHA_CREACION,ESTADO) VALUES (SEQ_RUBRICA.NEXTVAL,?,?,?,?,?,SYSDATE,?)");
            ps.setString(1,r.getNombre()); ps.setString(2,r.getDescripcion()); ps.setInt(3,r.getDocente().getIdUsuario());
            ps.setInt(4,r.getPractica().getIdPractica()); ps.setDouble(5,r.getPuntajeTotal()); ps.setString(6,r.getEstado());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error RubricaDAO: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<Rubrica> listarPorDocente(int idDocente) {
        List<Rubrica> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT R.*, P.TITULO FROM RUBRICA R JOIN PRACTICA P ON R.ID_PRACTICA=P.ID_PRACTICA WHERE R.ID_DOCENTE=? AND R.ESTADO='ACTIVO'");
            ps.setInt(1,idDocente); rs = ps.executeQuery();
            while (rs.next()) {
                Rubrica r = new Rubrica(); r.setIdRubrica(rs.getInt("ID_RUBRICA")); r.setNombre(rs.getString("NOMBRE")); r.setDescripcion(rs.getString("DESCRIPCION")); r.setPuntajeTotal(rs.getDouble("PUNTAJE_TOTAL")); r.setEstado(rs.getString("ESTADO"));
                Practica p = new Practica(); p.setIdPractica(rs.getInt("ID_PRACTICA")); p.setTitulo(rs.getString("TITULO")); r.setPractica(p);
                lista.add(r);
            }
        } catch (SQLException e) { System.err.println("Error RubricaDAO.listar: "+e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE RUBRICA SET ESTADO='INACTIVO' WHERE ID_RUBRICA=?");
            ps.setInt(1,id); ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error RubricaDAO.eliminar: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}