package com.vericarte.catalog.entities.database;

/**
 * Clase que genera los script de BD..
 * Created by raulgomez on 17/01/16.
 */
public class AplicationTable {
    public static final String TABLE_NAME = "APLICATION";
    public static final String COL_ID = "app_id";
    public static final String COL_NAME = "app_name";
    public static final String COL_IMAGE = "app_image";
    public static final String COL_SUMMARY = "app_summary";
    public static final String COL_PRICE = "app_price";
    public static final String COL_CATEGORY = "app_category";
    public static final String COL_DATE = "app_date";

    public static String createTableStringAplication() {
        String sentence = "create table " + TABLE_NAME + " ("
                + "%s integer primary key," + // id
                "%s blob null," + // image
                "%s text null," + // summary
                "%s text not null," + // price
                "%s text not null," + // answer type id
                "%s integer not null," + // category
                "%s datetime null" + // date
                ")";

        // Asignacion de nombres a la consulta
        String stringCreateTable = String.format(sentence, COL_ID, COL_NAME, COL_IMAGE, COL_SUMMARY, COL_PRICE, COL_CATEGORY, COL_DATE);

        return stringCreateTable;
    }

    /**
     * devuelve array con los nombres de las columnas de la tabla
     * @return array con nombres de columnas.
     */
    public static String[] getColumns() {
        String[] columns = {COL_ID, COL_NAME, COL_IMAGE, COL_SUMMARY, COL_PRICE, COL_CATEGORY, COL_DATE};
        return columns;
    }

}
