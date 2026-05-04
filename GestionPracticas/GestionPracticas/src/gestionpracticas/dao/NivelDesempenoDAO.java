package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.CriterioRubrica;
import gestionpracticas.modelo.NivelDesempeno;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NivelDesempenoDAO {
    private final Connection con;
    public NivelDesempenoDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(NivelDesempeno n) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO NIVEL_DESEMPENO (ID_NIVEL,ID_CRITERIO,NOMBRE,DESCRIPCION,PUNTAJE) VALUES (SEQ_NIVEL_DESEMPENO.NEXTVAL,?,?,?,?)");
            ps.setInt(1,n.getCriterio().getIdCriterio()); ps.setString(2,n.getNombre()); ps.setString(3,n.getDescripcion()); ps.setDouble(4,n.getPuntaje());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error NivelDAO: "+e.getMessage()); try{con.rollback();}catch(SQLException ex){} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<NivelDesempeno> listarPorCriterio(int idCriterio) {
        List<NivelDesempeno> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM NIVEL_DESEMPENO WHERE ID_CRITERIO=? ORDER BY PUNTAJE DESC");
            ps.setInt(1,idCriterio); rs = ps.executeQuery();
            while (rs.next()) {
                NivelDesempeno n = new NivelDesempeno();
                n.setIdNivel(rs.getInt("ID_NIVEL")); n.setNombre(rs.getString("NOMBRE")); n.setDescripcion(rs.getString("DESCRIPCION")); n.setPuntaje(rs.getDouble("PUNTAJE"));
                CriterioRubrica c = new CriterioRubrica(); c.setIdCriterio(idCriterio); n.setCriterio(c);
                lista.add(n);
            }
        } catch (SQLException e) { System.err.println("Error NivelDAO.listar: "+e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }
}