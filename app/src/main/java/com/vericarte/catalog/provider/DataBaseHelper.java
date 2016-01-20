package com.vericarte.catalog.provider;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vericarte.catalog.entitie.database.AplicationTable;
import com.vericarte.catalog.entitie.database.CategoryTable;


/**
 * Created by raulgomez on 17/01/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    //Información de la base de datos
    public static final String KEY_ID = "_id";
    /**
     * Nombre de la base de datos.
     */
    protected static final String DATABASE_NAME = "app_db.db";
    /**
     * Cada vez que haya un cambio en la BD se debe aumentar el contador una unidad.
     */
    protected static final int DATABASE_VERSION = 1;
    public static String LOG_TAG;

    /**
     * Constructor de la clase.
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //region Metodos publicos
    /**
     * Delete Database.
     * @param context
     * @return
     */
    public static boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }

    public static byte[] getBlob(Cursor cursor, String colName) {

        byte[] result = null;

        if (colName != null && colName.length() > 0)
            try {
                result = cursor.getBlob(cursor.getColumnIndexOrThrow(colName));
            } catch (IllegalArgumentException illegalE)
            {
                illegalE.printStackTrace();
                Log.e(LOG_TAG, "Al obtener el valor de la columna: " + colName);

            } catch (Exception e)
            {
                e.printStackTrace();
                Log.e(LOG_TAG,
                        "Error desconocido al intentar obtener el valor de la columna: "
                                + colName
                );
            }
        return result;
    }

    /**
     * Get Double del valor de la columna
     * @param cursor  Registro
     * @param colName nombre de la columna de la cual se va obtener el valor
     * @return el valor de la columna
     * @throws IllegalArgumentException , Exception
     */
    public static double getDouble(Cursor cursor, String colName) {

        double result = 0;

        if (colName != null && colName.length() > 0)
            try {
                result = cursor
                        .getDouble(cursor.getColumnIndexOrThrow(colName));
            } catch (IllegalArgumentException illegalE) {
                illegalE.printStackTrace();
                Log.e(LOG_TAG, "Al obtener el valor de la columna: " + colName);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG,
                        "Error desconocido al intentar obtener el valor de la columna: "
                                + colName
                );
            }
        return result;
    }

    /**
     * Get Int de la columna
     * @param cursor  Registro
     * @param colName nombre de la columna de la cual se va obtener el valor
     * @return el valor de la columna, sino puede convertir el valor en int
     * retorna -1
     */
    public static int getInt(Cursor cursor, String colName) {

        int result = -1;

        if (colName != null && colName.length() > 0)
            try {
                result = cursor.getInt(cursor.getColumnIndexOrThrow(colName));
            } catch (IllegalArgumentException illegalE) {
                illegalE.printStackTrace();
                Log.e(LOG_TAG, "Al obtener el valor de la columna: " + colName);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG,
                        "Error desconocido al intentar obtener el valor de la columna: "
                                + colName
                );

            }

        return result;

    }

    /**
     * Get Long de la columna.
     * @param cursor  Registro
     * @param colName nombre de la columna de la cual se va obtener el valor
     * @return el valor de la columna, sino puede convertir el valor en long
     * retorna -1.
     */
    public static long getLong(Cursor cursor, String colName) {
        long result = -1;

        if (colName != null && colName.length() > 0)
            try {
                result = cursor.getLong(cursor.getColumnIndexOrThrow(colName));
            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, "Al obtener el valor de la columna: " + colName);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(LOG_TAG,
                        "Error desconocido al intentar obtener el valor de la columna: "
                                + colName
                );
                e.printStackTrace();
            }
        return result;
    }

    /**
     * Get Float de la columna
     * @param cursor  Registro
     * @param colName nombre de la columna de la cual se va obtener el valor
     * @return el valor de la columna, sino puede convertir el valor en long
     * retorna -1.
     */
    public static float getFloat(Cursor cursor, String colName) {
        float result = -1;

        if (colName != null && colName.length() > 0)
            try {
                result = cursor.getFloat(cursor.getColumnIndexOrThrow(colName));
            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, "Al obtener el valor de la columna: " + colName);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(LOG_TAG,
                        "Error desconocido al intentar obtener el valor de la columna: "
                                + colName
                );
                e.printStackTrace();

            }

        return result;
    }

    /**
     * Get String de la columna.
     * @param cursor  Registro
     * @param colName nombre de la columna de la cual se va obtener el valor
     * @return el valor de la columna
     * @throws IllegalArgumentException , Exception
     */
    public static String getString(Cursor cursor, String colName) {
        String result = "";

        if (colName != null && colName.length() > 0)
            try {
                result = cursor
                        .getString(cursor.getColumnIndexOrThrow(colName));
            } catch (IllegalArgumentException illegalE) {
                illegalE.printStackTrace();
                Log.e(LOG_TAG, "Al obtener el valor de la columna: " + colName);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG,
                        "Error desconocido al intentar obtener el valor de la columna: "
                                + colName
                );
            }
        return result;
    }
    //endregion

    /**
     * Crea la BD.
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(LOG_TAG, "Inicia creación de BD");
        database.execSQL(AplicationTable.createTableStringAplication());
        database.execSQL(CategoryTable.createTableStringCategory());
        Log.i(LOG_TAG, "Termina creación de BD");
    }

    /**
     * Elimina las tablas.
     * @param db base de datos.
     * @param oldVersion versión antigua.
     * @param newVersion versión nueva.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.i(LOG_TAG, "Inicia Upgrade de BD");
            db.execSQL("DROP TABLE IF EXISTS " + AplicationTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.TABLE_NAME);

            onCreate(db);
            Log.i(LOG_TAG, "Termina Upgrade de BD");
        }catch (Exception e){
            e.printStackTrace();
            db.close();
        }
    }
}
