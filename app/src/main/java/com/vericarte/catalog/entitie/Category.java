package com.vericarte.catalog.entitie;

import android.util.Log;

import com.vericarte.catalog.interfaces.IJSONDeserializer;
import com.vericarte.catalog.interfaces.IJSONSerializer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase entidad que abstrae las propiedades de Categoria.
 * Created by raulgomez on 17/01/16.
 */
public class Category implements IJSONDeserializer, IJSONSerializer
{
    //region Variables
    private String LOG_TAG = "Category";
    private int id;
    private String name;
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    //region Overrides

    /**
     * Convertir de Json a entidad.
     * @param json
     */
    @Override
    public void convertFrom(JSONObject json) {
        if (json != null) {
            try {
                this.setId(json.getJSONObject("category").getJSONObject("attributes").getInt("im:id"));
                this.setName(json.getJSONObject("category").getJSONObject("attributes").getString("label"));

            }catch (Exception ex){
                Log.d(LOG_TAG,
                        "Error al obtener un Category.");
                ex.printStackTrace();
            }
            Log.i(LOG_TAG, "Clase Category");
        }

    }

    /**
     * Convertir de entidad a Json.
     * @return
     * @throws JSONException
     */
    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
    //endregion
}
