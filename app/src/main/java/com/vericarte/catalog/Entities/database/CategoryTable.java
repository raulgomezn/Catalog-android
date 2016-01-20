package com.vericarte.catalog.entities.database;

/**
 * Clase que genera los script de BD.
 * Created by raulgomez on 17/01/16.
 */
public class CategoryTable {
    public static final String TABLE_NAME = "CATEGORY";
    public static final String COL_ID = "app_id";
    public static final String COL_NAME = "app_name";

    public static String createTableStringCategory() {
        String sentence = "create table " + TABLE_NAME + " ("
                + "%s integer primary key," + // id
                "%s text null" + // name
                ")";
        // Asignacion de nombres a la consulta
        String stringCreateTable = String.format(sentence, COL_ID, COL_NAME);

        return stringCreateTable;
    }

    /**
     * devuelve array con los nombres de las columnas de la tabla
     * @return array con nombres de columnas.
     */
    public static String[] getColumns() {
        String[] columns = {COL_ID, COL_NAME};
        return columns;
    }
}
