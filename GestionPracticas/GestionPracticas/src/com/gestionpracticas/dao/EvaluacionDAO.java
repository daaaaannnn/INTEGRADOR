package com.gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.Evaluacion;
import gestionpracticas.modelo.MatriculaPractica;
import gestionpracticas.modelo.Rubrica;
import gestionpracticas.modelo.Usuario;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluacionDAO {
    private final Connection con;
    public EvaluacionDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(Evaluacion e) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO EVALUACION (ID_EVALUACION, ID_MATRICULA, ID_RUBRICA, ID_EVALUADOR, FECHA_EVALUACION, PUNTAJE_OBTENIDO, OBSERVACIONES, ESTADO) VALUES (SEQ_EVALUACION.NEXTVAL,?,?,?,SYSDATE,?,?,?)");
            ps.setInt(1, e.getMatricula().getIdMatricula()); ps.setInt(2, e.getRubrica().getIdRubrica());
            ps.setInt(3, e.getEvaluador().getIdUsuario()); ps.setDouble(4, e.getPuntajeObtenido());
            ps.setString(5, e.getObservaciones()); ps.setString(6, e.getEstado());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException ex) { System.err.println("Error EvaluacionDAO.insertar: " + ex.getMessage()); try { con.rollback(); } catch (SQLException exc) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<Evaluacion> listarPorMatricula(int idMatricula) {
        List<Evaluacion> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT E.*, R.NOMBRE AS NOM_RUB, U.NOMBRE AS NOM_EVAL, U.APELLIDO AS APE_EVAL FROM EVALUACION E JOIN RUBRICA R ON E.ID_RUBRICA=R.ID_RUBRICA JOIN USUARIO U ON E.ID_EVALUADOR=U.ID_USUARIO WHERE E.ID_MATRICULA=?");
            ps.setInt(1, idMatricula); rs = ps.executeQuery();
            while (rs.next()) {
                Evaluacion ev = new Evaluacion();
                ev.setIdEvaluacion(rs.getInt("ID_EVALUACION")); ev.setPuntajeObtenido(rs.getDouble("PUNTAJE_OBTENIDO"));
                ev.setObservaciones(rs.getString("OBSERVACIONES")); ev.setEstado(rs.getString("ESTADO")); ev.setFechaEvaluacion(rs.getDate("FECHA_EVALUACION"));
                Rubrica r = new Rubrica(); r.setIdRubrica(rs.getInt("ID_RUBRICA")); r.setNombre(rs.getString("NOM_RUB")); ev.setRubrica(r);
                Usuario u = new Usuario(); u.setIdUsuario(rs.getInt("ID_EVALUADOR")); u.setNombre(rs.getString("NOM_EVAL")); u.setApellido(rs.getString("APE_EVAL")); ev.setEvaluador(u);
                MatriculaPractica m = new MatriculaPractica(); m.setIdMatricula(idMatricula); ev.setMatricula(m);
                lista.add(ev);
            }
        } catch (SQLException e) { System.err.println("Error EvaluacionDAO.listar: " + e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean actualizar(Evaluacion e) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE EVALUACION SET PUNTAJE_OBTENIDO=?,OBSERVACIONES=?,ESTADO=? WHERE ID_EVALUACION=?");
            ps.setDouble(1, e.getPuntajeObtenido()); ps.setString(2, e.getObservaciones()); ps.setString(3, e.getEstado()); ps.setInt(4, e.getIdEvaluacion());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException ex) { System.err.println("Error EvaluacionDAO.actualizar: " + ex.getMessage()); try { con.rollback(); } catch (SQLException exc) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}