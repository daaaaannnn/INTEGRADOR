package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.CriterioRubrica;
import gestionpracticas.modelo.Rubrica;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriterioRubricaDAO {
    private final Connection con;
    public CriterioRubricaDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(CriterioRubrica c) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO CRITERIO_RUBRICA (ID_CRITERIO,ID_RUBRICA,NOMBRE,DESCRIPCION,PUNTAJE_MAXIMO,PESO_PORCENTAJE,ORDEN) VALUES (SEQ_CRITERIO_RUBRICA.NEXTVAL,?,?,?,?,?,?)");
            ps.setInt(1,c.getRubrica().getIdRubrica()); ps.setString(2,c.getNombre()); ps.setString(3,c.getDescripcion());
            ps.setDouble(4,c.getPuntajeMaximo()); ps.setDouble(5,c.getPesoPorcentaje()); ps.setInt(6,c.getOrden());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error CriterioDAO: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<CriterioRubrica> listarPorRubrica(int idRubrica) {
        List<CriterioRubrica> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM CRITERIO_RUBRICA WHERE ID_RUBRICA=? ORDER BY ORDEN");
            ps.setInt(1,idRubrica); rs = ps.executeQuery();
            while (rs.next()) {
                CriterioRubrica c = new CriterioRubrica();
                c.setIdCriterio(rs.getInt("ID_CRITERIO")); c.setNombre(rs.getString("NOMBRE")); c.setDescripcion(rs.getString("DESCRIPCION"));
                c.setPuntajeMaximo(rs.getDouble("PUNTAJE_MAXIMO")); c.setPesoPorcentaje(rs.getDouble("PESO_PORCENTAJE")); c.setOrden(rs.getInt("ORDEN"));
                Rubrica r = new Rubrica(); r.setIdRubrica(idRubrica); c.setRubrica(r);
                lista.add(c);
            }
        } catch (SQLException e) { System.err.println("Error CriterioDAO.listar: "+e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean eliminar(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM CRITERIO_RUBRICA WHERE ID_CRITERIO=?");
            ps.setInt(1,id); ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error CriterioDAO.eliminar: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}