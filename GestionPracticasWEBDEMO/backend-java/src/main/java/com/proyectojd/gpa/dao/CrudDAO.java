package com.proyectojd.gpa.dao;

import com.proyectojd.gpa.config.ConexionBD;
import com.proyectojd.gpa.model.EntitySchema;
import com.proyectojd.gpa.util.JsonUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * DAO CRUD genérico basado en una lista blanca de columnas por entidad.
 * Se usa para listar, crear, editar, activar, inactivar y eliminar registros.
 *
 * Ajuste del parche:
 * - Antes todas las entidades se ordenaban por la columna estado.
 * - Algunas tablas como MENSAJE_INTERNO, HISTORIAL_REVISION o COMPATIBILIDAD_INSTITUCION
 *   no siempre tienen estado.
 * - Ahora solo se usa el orden por estado cuando la entidad tiene esa columna habilitada.
 */
public class CrudDAO {
    private final ConexionBD conexionBD;

    public CrudDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public List<Map<String, Object>> listar(EntitySchema schema) throws SQLException {
        String sql = "SELECT * FROM " + schema.tableName() + orderBySql(schema);
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return JsonUtil.resultSetToList(rs);
        }
    }

    public List<Map<String, Object>> buscarPorCampo(EntitySchema schema, String campo, Object valor) throws SQLException {
        if (!schema.writableColumns().contains(campo) && !schema.idColumn().equalsIgnoreCase(campo)) {
            throw new IllegalArgumentException("Campo no permitido: " + campo);
        }
        String sql = "SELECT * FROM " + schema.tableName() + " WHERE " + campo + " = ?" + orderBySql(schema);
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                return JsonUtil.resultSetToList(rs);
            }
        }
    }

    public Map<String, Object> obtener(EntitySchema schema, long id) throws SQLException {
        String sql = "SELECT * FROM " + schema.tableName() + " WHERE " + schema.idColumn() + " = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> rows = JsonUtil.resultSetToList(rs);
                return rows.isEmpty() ? new LinkedHashMap<>() : rows.get(0);
            }
        }
    }

    public int crear(EntitySchema schema, Map<String, Object> payload) throws SQLException {
        List<String> columns = filtrarColumnas(schema, payload);
        if (columns.isEmpty()) {
            throw new IllegalArgumentException("No se recibieron campos válidos para crear el registro");
        }
        StringJoiner colSql = new StringJoiner(", ");
        StringJoiner valSql = new StringJoiner(", ");
        for (String column : columns) {
            colSql.add(column);
            valSql.add("?");
        }
        String sql = "INSERT INTO " + schema.tableName() + " (" + colSql + ") VALUES (" + valSql + ")";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            bind(ps, columns, payload);
            return ps.executeUpdate();
        }
    }

    public int actualizar(EntitySchema schema, long id, Map<String, Object> payload) throws SQLException {
        List<String> columns = filtrarColumnas(schema, payload);
        if (columns.isEmpty()) {
            throw new IllegalArgumentException("No se recibieron campos válidos para actualizar el registro");
        }
        StringJoiner setSql = new StringJoiner(", ");
        for (String column : columns) {
            setSql.add(column + " = ?");
        }
        String sql = "UPDATE " + schema.tableName() + " SET " + setSql + " WHERE " + schema.idColumn() + " = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            int i = bind(ps, columns, payload);
            ps.setLong(i, id);
            return ps.executeUpdate();
        }
    }

    public int cambiarEstado(EntitySchema schema, long id, String estado) throws SQLException {
        if (!schema.writableColumns().contains("estado")) {
            throw new IllegalArgumentException("La entidad no tiene estado configurable");
        }
        String sql = "UPDATE " + schema.tableName() + " SET estado = ? WHERE " + schema.idColumn() + " = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setLong(2, id);
            return ps.executeUpdate();
        }
    }

    public int eliminar(EntitySchema schema, long id) throws SQLException {
        if (schema.softDelete() && schema.writableColumns().contains("estado")) {
            return cambiarEstado(schema, id, "INACTIVO");
        }
        String sql = "DELETE FROM " + schema.tableName() + " WHERE " + schema.idColumn() + " = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }

    private String orderBySql(EntitySchema schema) {
        if (schema.writableColumns().contains("estado")) {
            return " ORDER BY CASE estado WHEN 'ACTIVO' THEN 1 WHEN 'PENDIENTE' THEN 2 WHEN 'APROBADO' THEN 3 WHEN 'INACTIVO' THEN 4 WHEN 'REPROBADO' THEN 5 ELSE 6 END, " + schema.idColumn();
        }
        return " ORDER BY " + schema.idColumn();
    }

    private List<String> filtrarColumnas(EntitySchema schema, Map<String, Object> payload) {
        List<String> columns = new ArrayList<>();
        for (String column : schema.writableColumns()) {
            if (payload.containsKey(column)) {
                columns.add(column);
            }
        }
        return columns;
    }

    private int bind(PreparedStatement ps, List<String> columns, Map<String, Object> payload) throws SQLException {
        int index = 1;
        for (String column : columns) {
            Object value = payload.get(column);
            if (value instanceof Double d && d % 1 == 0) {
                ps.setLong(index++, d.longValue());
            } else {
                ps.setObject(index++, value);
            }
        }
        return index;
    }
}
