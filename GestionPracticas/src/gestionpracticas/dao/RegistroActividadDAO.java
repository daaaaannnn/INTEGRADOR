package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.MatriculaPractica;
import gestionpracticas.modelo.RegistroActividad;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroActividadDAO {
    private final Connection con;
    public RegistroActividadDAO() { this.con = ConexionBD.getInstancia().getConnection(); }

    public boolean insertar(RegistroActividad r) {
        String sql = "INSERT INTO REGISTRO_ACTIVIDAD (ID_REGISTRO, ID_MATRICULA, FECHA_ACTIVIDAD, " +
                     "DESCRIPCION, HORAS, TIPO_ACTIVIDAD, ESTADO, OBSERVACIONES) " +
                     "VALUES (SEQ_REGISTRO_ACTIVIDAD.NEXTVAL, ?, SYSDATE, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, r.getMatricula().getIdMatricula());
            ps.setString(2, r.getDescripcion());
            ps.setDouble(3, r.getHoras());
            ps.setString(4, r.getTipoActividad());
            ps.setString(5, r.getEstado());
            ps.setString(6, r.getObservaciones());
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }

    public List<RegistroActividad> listarPorMatricula(int idMatricula) {
        List<RegistroActividad> lista = new ArrayList<>();
        PreparedStatement ps = null; ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT * FROM REGISTRO_ACTIVIDAD WHERE ID_MATRICULA=? ORDER BY FECHA_ACTIVIDAD DESC");
            ps.setInt(1, idMatricula);
            rs = ps.executeQuery();
            while (rs.next()) {
                RegistroActividad r = new RegistroActividad();
                r.setIdRegistro(rs.getInt("ID_REGISTRO"));
                MatriculaPractica m = new MatriculaPractica();
                m.setIdMatricula(rs.getInt("ID_MATRICULA"));
                r.setMatricula(m);
                r.setFechaActividad(rs.getDate("FECHA_ACTIVIDAD"));
                r.setDescripcion(rs.getString("DESCRIPCION"));
                r.setHoras(rs.getDouble("HORAS"));
                r.setTipoActividad(rs.getString("TIPO_ACTIVIDAD"));
                r.setEstado(rs.getString("ESTADO"));
                r.setObservaciones(rs.getString("OBSERVACIONES"));
                lista.add(r);
            }
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
        finally { Utilidades.cerrar(rs); Utilidades.cerrar(ps); }
        return lista;
    }

    public boolean actualizarEstado(int idRegistro, String nuevoEstado) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE REGISTRO_ACTIVIDAD SET ESTADO=? WHERE ID_REGISTRO=?");
            ps.setString(1, nuevoEstado); ps.setInt(2, idRegistro);
            ps.executeUpdate(); con.commit(); return true;
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); try { con.rollback(); } catch (SQLException ex) {} return false; }
        finally { Utilidades.cerrar(ps); }
    }
}
