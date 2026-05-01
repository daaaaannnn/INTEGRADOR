package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.Evaluacion;
import gestionpracticas.modelo.Pregunta;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO {
    private final Connection con;
    public PreguntaDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(Pregunta p) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO PREGUNTA (ID_PREGUNTA,TEXTO_PREGUNTA,TIPO_PREGUNTA,ID_EVALUACION,ID_PRACTICA) VALUES (SEQ_PREGUNTA.NEXTVAL,?,?,?,?)");
            ps.setString(1,p.getTextoPregunta()); ps.setString(2,p.getTipoPregunta());
            ps.setInt(3,p.getEvaluacion().getIdEvaluacion()); ps.setInt(4,p.getPractica().getIdPractica());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error PreguntaDAO: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<Pregunta> listarPorEvaluacion(int idEvaluacion) {
        List<Pregunta> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM PREGUNTA WHERE ID_EVALUACION=?");
            ps.setInt(1,idEvaluacion); rs = ps.executeQuery();
            while (rs.next()) {
                Pregunta p = new Pregunta();
                p.setIdPregunta(rs.getInt("ID_PREGUNTA")); p.setTextoPregunta(rs.getString("TEXTO_PREGUNTA")); p.setTipoPregunta(rs.getString("TIPO_PREGUNTA"));
                Evaluacion e = new Evaluacion(); e.setIdEvaluacion(idEvaluacion); p.setEvaluacion(e);
                lista.add(p);
            }
        } catch (SQLException e) { System.err.println("Error PreguntaDAO.listar: "+e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM PREGUNTA WHERE ID_PREGUNTA=?");
            ps.setInt(1,id); ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error PreguntaDAO.eliminar: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}