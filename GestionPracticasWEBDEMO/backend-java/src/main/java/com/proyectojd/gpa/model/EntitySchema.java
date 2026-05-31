package com.proyectojd.gpa.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Define qué columnas puede manipular cada endpoint CRUD.
 * Esta lista blanca evita insertar campos no esperados desde el frontend.
 */
public class EntitySchema {
    private final String tableName;
    private final String idColumn;
    private final Set<String> writableColumns;
    private final boolean softDelete;

    public EntitySchema(String tableName, String idColumn, boolean softDelete, String... writableColumns) {
        this.tableName = tableName;
        this.idColumn = idColumn;
        this.softDelete = softDelete;
        this.writableColumns = new LinkedHashSet<>(Arrays.asList(writableColumns));
    }

    public String tableName() {
        return tableName;
    }

    public String idColumn() {
        return idColumn;
    }

    public Set<String> writableColumns() {
        return writableColumns;
    }

    public boolean softDelete() {
        return softDelete;
    }
}
