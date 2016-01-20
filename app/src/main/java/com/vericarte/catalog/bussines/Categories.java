package com.vericarte.catalog.bussines;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vericarte.catalog.entitie.Category;
import com.vericarte.catalog.entitie.database.CategoryTable;
import com.vericarte.catalog.provider.DataBaseHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase de negocios para las Categorias.
 * Created by raulgomez on 17/01/16.
 */
public class Categories {
    private Context context;

    public Categories(Context context){
        this.context = context;
    }

    //region Metodos

    public long add(Category category){
        Category old = null;
        long id = -1;
        try {
            old = get(category.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (old == null)// No exite en la tabla, crea el nuevo.
        {
            id = makeAdd(category);
        }
        else if( old.getId() == category.getId())
        {
            id = update(category);
        }

        return id;
    }

    /**
     * Persiste la entidad en la BD.
     * @param category
     * @return
     */
    private long makeAdd(Category category){
        long id = -1;
        ContentValues values = new ContentValues();

        values.put(CategoryTable.COL_ID, category.getId());
        values.put(CategoryTable.COL_NAME, category.getName());

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            //Perform the insert
            id = db.insert(CategoryTable.TABLE_NAME, null, values);

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
    private Category createCategory(Cursor cursor) {
        Category category = new Category();

        category.setId(DataBaseHelper.getInt(cursor, CategoryTable.COL_ID));
        category.setName(DataBaseHelper.getString(cursor, CategoryTable.COL_NAME));

        return category;
    }

    /**
     * Obtener uno por el Id.
     * @param categoryId
     * @return
     * @throws Exception
     */
    public Category get(int categoryId) throws Exception {
        Category aplication = null;

        String selection;
        String[] selArgs;
        Cursor cursor;
        selection = CategoryTable.COL_ID + " == ?";
        selArgs = new String[]{String.valueOf(categoryId)};

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.query(CategoryTable.TABLE_NAME, CategoryTable.getColumns(), selection, selArgs, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            aplication = createCategory(cursor);
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
    public List<Category> all() {
        List<Category> categories = new LinkedList<>();

        Cursor cursor = null;


        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            cursor = db.query(CategoryTable.TABLE_NAME, CategoryTable.getColumns(), null, null, null, null, null, "21");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (cursor != null && cursor.moveToNext()) {
            categories.add(createCategory(cursor));
        }

        if (cursor != null)
            cursor.close();

        dbHelper.close();
        return categories;
    }

    /**
     * Borrar
     * @param id
     * @return
     */
    public int delete(int id) {
        int countRows = 0;
        String selection = String.format("%s == ?", CategoryTable.COL_ID);
        String[] args = new String[]{String.valueOf(id)};

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            countRows = db.delete(CategoryTable.TABLE_NAME, selection, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countRows;
    }

    /**
     * Actualizar
     * @param category
     * @return
     */
    public int update(Category category)
    {
        int countRows = -1;
        String selection = String.format("%s == ?", CategoryTable.COL_ID);
        String[] args = new String[]{String.valueOf(category.getId())};
        ContentValues values = new ContentValues();
        try {
            values.put(CategoryTable.COL_ID, category.getId());
            values.put(CategoryTable.COL_NAME, category.getName());

            DataBaseHelper dbHelper = new DataBaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            countRows =  db.update(CategoryTable.TABLE_NAME, values, selection, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countRows;
    }
    //endregion
}
