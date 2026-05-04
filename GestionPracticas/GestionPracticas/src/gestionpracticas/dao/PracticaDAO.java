package gestionpracticas.dao;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.modelo.Institucion;
import gestionpracticas.modelo.Practica;
import gestionpracticas.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PracticaDAO {

    private final Connection con;

    public PracticaDAO() {
        this.con = ConexionBD.getInstancia().getConnection();
    }

    public boolean insertar(Practica p) {
        String sql = "INSERT INTO PRACTICA (ID_PRACTICA, TITULO, DESCRIPCION, ID_INSTITUCION, " +
                     "ID_GRUPO, FECHA_INICIO, FECHA_FIN, TIPO_PRACTICA, ESTADO) " +
                     "VALUES (SEQ_PRACTICA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getinstitucion());
            ps.setInt(4, p.getGrupo().getIdGrupo());
            ps.setDate(5, new java.sql.Date(p.getFechaInicio().getTime()));
            ps.setDate(6, new java.sql.Date(p.getFechaFin().getTime()));
            ps.setString(7, p.getTipoPractica());
            ps.setString(8, p.getEstado());
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertando práctica: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            Utilidades.cerrar(ps);
        }
    }

    public List<Practica> listarTodos() {
        List<Practica> lista = new ArrayList<>();
        String sql = "SELECT P.*, I.NOMBRE AS NOM_INST FROM PRACTICA P " +
                     "JOIN INSTITUCION I ON P.ID_INSTITUCION = I.ID_INSTITUCION " +
                     "ORDER BY P.ID_PRACTICA";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listando prácticas: " + e.getMessage());
        } finally {
            Utilidades.cerrar(rs);
            Utilidades.cerrar(ps);
        }
        return lista;
    }

    public Practica buscarPorId(int id) {
        String sql = "SELECT P.*, I.NOMBRE AS NOM_INST FROM PRACTICA P " +
                     "JOIN INSTITUCION I ON P.ID_INSTITUCION = I.ID_INSTITUCION " +
                     "WHERE P.ID_PRACTICA = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error buscando práctica: " + e.getMessage());
        } finally {
            Utilidades.cerrar(rs);
            Utilidades.cerrar(ps);
        }
        return null;
    }

    public boolean actualizar(Practica p) {
        String sql = "UPDATE PRACTICA SET TITULO=?, DESCRIPCION=?, FECHA_INICIO=?, " +
                     "FECHA_FIN=?, TIPO_PRACTICA=?, ESTADO=? WHERE ID_PRACTICA=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setDate(3, new java.sql.Date(p.getFechaInicio().getTime()));
            ps.setDate(4, new java.sql.Date(p.getFechaFin().getTime()));
            ps.setString(5, p.getTipoPractica());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getIdPractica());
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error actualizando práctica: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            Utilidades.cerrar(ps);
        }
    }

    public boolean eliminar(int id) {
        String sql = "UPDATE PRACTICA SET ESTADO = 'INACTIVO' WHERE ID_PRACTICA = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error eliminando práctica: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            Utilidades.cerrar(ps);
        }
    }

    private Practica mapear(ResultSet rs) throws SQLException {
        Practica p = new Practica();
        p.setIdPractica(rs.getInt("ID_PRACTICA"));
        p.setTitulo(rs.getString("TITULO"));
        p.setDescripcion(rs.getString("DESCRIPCION"));
        p.setFechaInicio(rs.getDate("FECHA_INICIO"));
        p.setFechaFin(rs.getDate("FECHA_FIN"));
        p.setTipoPractica(rs.getString("TIPO_PRACTICA"));
        p.setEstado(rs.getString("ESTADO"));
        Institucion inst = new Institucion();
        inst.setIdInstitucion(rs.getInt("ID_INSTITUCION"));
        inst.setNombre(rs.getString("NOM_INST"));
        p.setInstitucion(inst);
        return p;
    }
}
