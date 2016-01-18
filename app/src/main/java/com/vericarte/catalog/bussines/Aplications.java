package com.vericarte.catalog.bussines;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vericarte.catalog.entities.Aplication;
import com.vericarte.catalog.entities.database.AplicationTable;
import com.vericarte.catalog.provider.DataBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase de Negocios para la Aplicación.
 * Created by raulgomez on 17/01/16.
 */
public class Aplications {

    private Context context;

    public Aplications(Context context){
        this.context = context;
    }

    //region Metodos
    private static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public long add(Aplication aplication){
        Aplication old = null;
        long id = -1;
        try {
            old = get(aplication.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (old == null)// No exite en la tabla, crea el nuevo.
        {
            id = makeAdd(aplication);
        }
        else if( old.getId() == aplication.getId())
        {
            id = update(aplication);
        }

        return id;
    }

    /**
     * Persiste la entidad en la BD.
     * @param aplication
     * @return
     */
    private long makeAdd(Aplication aplication){
        long id = -1;
        ContentValues values = new ContentValues();

        values.put(AplicationTable.COL_ID, aplication.getId());
        values.put(AplicationTable.COL_NAME, aplication.getName());
        values.put(AplicationTable.COL_IMAGE, aplication.getImage());
        values.put(AplicationTable.COL_SUMMARY, aplication.getSummary());
        values.put(AplicationTable.COL_PRICE, aplication.getPrice());
        values.put(AplicationTable.COL_DATE, getDateFormat().format(aplication.getReleaseDate()));
        values.put(AplicationTable.COL_CATEGORY, aplication.getCategoryId());

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            //Perform the insert
            id = db.insert(AplicationTable.TABLE_NAME, null, values);

            //Close the Database and the Helper
            db.close();
            dbHelper.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    /**
     * Crear la entidad.
     * @param cursor
     * @return
     */
    private Aplication createAplication(Cursor cursor) {
        Aplication aplication = new Aplication();

        aplication.setId(DataBaseHelper.getInt(cursor, AplicationTable.COL_ID));
        aplication.setName(DataBaseHelper.getString(cursor, AplicationTable.COL_NAME));
        aplication.setCategoryId(DataBaseHelper.getInt(cursor, AplicationTable.COL_CATEGORY));
        String date = DataBaseHelper.getString(cursor, AplicationTable.COL_DATE);
        try {
        aplication.setReleaseDate(getDateFormat().parse(date));
        } catch (ParseException e) {
            Log.d("error",
                    "No fue posible convertir la fecha"
            );
        }
        aplication.setImage(DataBaseHelper.getBlob(cursor, AplicationTable.COL_IMAGE));
        aplication.setPrice(DataBaseHelper.getString(cursor, AplicationTable.COL_PRICE));
        aplication.setSummary(DataBaseHelper.getString(cursor, AplicationTable.COL_SUMMARY));

        return aplication;
    }

    /**
     * Obtener uno por el Id.
     * @param aplicationId
     * @return
     * @throws Exception
     */
    public Aplication get(int aplicationId) throws Exception {
        Aplication aplication = null;

        String selection;
        String[] selArgs;
        Cursor cursor;
        selection = AplicationTable.COL_ID + " == ?";
        selArgs = new String[]{String.valueOf(aplicationId)};

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.query(AplicationTable.TABLE_NAME,AplicationTable.getColumns(), selection, selArgs, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            aplication = createAplication(cursor);
        }

        if (cursor != null)
            cursor.close();

        dbHelper.close();
        return aplication;
    }

    /**
     * Todos los resultados.
     * @return
     */
    public List<Aplication> all() {
        List<Aplication> aplications = new LinkedList<>();

        Cursor cursor = null;


        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            cursor = db.query(AplicationTable.TABLE_NAME,AplicationTable.getColumns(), null, null, null, null, null, "21");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (cursor != null && cursor.moveToNext()) {
            aplications.add(createAplication(cursor));
        }

        if (cursor != null)
            cursor.close();

        dbHelper.close();
        return aplications;
    }

    /**
     * Listar por categoria.
     * @param categoryId
     * @return
     */
    public List<Aplication> filterByCategory(int categoryId) {
        List<Aplication> aplications = new LinkedList<>();

        Cursor cursor = null;
        String selection = String.format("%s == ? ", AplicationTable.COL_CATEGORY);
        String[] args = new String[]{String.valueOf(categoryId)};

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            cursor = db.query(AplicationTable.TABLE_NAME,AplicationTable.getColumns(), selection, args, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (cursor != null && cursor.moveToNext()) {
            aplications.add(createAplication(cursor));
        }

        if (cursor != null)
            cursor.close();

        dbHelper.close();
        return aplications;
    }

    /**
     * Borrar
     * @param id
     * @return
     */
    public int delete(int id) {
        int countRows = 0;
        String selection = String.format("%s == ?", AplicationTable.COL_ID);
        String[] args = new String[]{String.valueOf(id)};

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            countRows = db.delete(AplicationTable.TABLE_NAME, selection, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countRows;
    }

    /**
     * Actualizar
     * @param aplication
     * @return
     */
    public int update(Aplication aplication)
    {
        int countRows = -1;
        String selection = String.format("%s == ?", AplicationTable.COL_ID);
        String[] args = new String[]{String.valueOf(aplication.getId())};
        ContentValues values = new ContentValues();
        try {
            values.put(AplicationTable.COL_ID, aplication.getId());
            values.put(AplicationTable.COL_NAME, aplication.getName());
            values.put(AplicationTable.COL_IMAGE, aplication.getImage());
            values.put(AplicationTable.COL_SUMMARY, aplication.getSummary());
            values.put(AplicationTable.COL_PRICE, aplication.getPrice());
            values.put(AplicationTable.COL_DATE, getDateFormat().format(aplication.getReleaseDate()));
            values.put(AplicationTable.COL_CATEGORY, aplication.getCategoryId());

            DataBaseHelper dbHelper = new DataBaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            countRows =  db.update(AplicationTable.TABLE_NAME, values, selection, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countRows;
    }
    //endregion
}
