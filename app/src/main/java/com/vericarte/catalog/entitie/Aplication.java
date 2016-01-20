package com.vericarte.catalog.entitie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.vericarte.catalog.interfaces.IJSONDeserializer;
import com.vericarte.catalog.interfaces.IJSONSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase entidad que abstrae las propiedades de Aplicación.
 * Created by raulgomez on 17/01/16.
 */
public class Aplication implements IJSONDeserializer, IJSONSerializer
{
    //region Variables
    private String LOG_TAG = "Aplication";
    private int id;
    private String name;
    private byte[] image;
    private String summary;
    private String price;
    private int categoryId;
    private Date releaseDate;
    //endregion

    //region Getter and Setters
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    //endregion

    //region Overrides
    @Override
    public void convertFrom(JSONObject json) {
        if (json != null) {
            try {
                this.setId(json.getJSONObject("id").getJSONObject("attributes").getInt("im:id"));

                this.setName(json.getJSONObject("im:name").getString("label"));

                JSONArray arrayImage = json.getJSONArray("im:image");
                String url = arrayImage.getJSONObject(0).getString("label");
                byte[] data = getBitmapAsByteArray(getBitmapFromURL(url));
                this.setImage(data);

                this.setSummary(json.getJSONObject("summary").getString("label"));

                this.setCategoryId(json.getJSONObject("category").getJSONObject("attributes").getInt("im:id"));

                this.setPrice("Free");

                this.setReleaseDate(convertStringtoDate(json.getJSONObject("im:releaseDate").getString("label")));

            }catch (Exception ex){
                Log.d(LOG_TAG,
                        "Error al obtener un Aplication.");
                ex.printStackTrace();
            }
            Log.i(LOG_TAG, "Class Aplication");
        }

    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
    //endregion

    //region Metodos privados

    /**
     * Metodo para convertir bajar un recurso (Url) y
     * convertirlo en Bitmap.
     * @param src la url.
     * @return el bitmap de la imagen. O null si falla.
     */
    private Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convierte el Bitmap en un arreglo de Bytes.
     * @param bitmap el bitmap de la imagen.
     * @return El arreglo de Bytes de esa imagen.
     */
    private static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Convierte fecha en texto a fecha tipo Date.
     * @param dateString fecha en String.
     * @return fecha en formato Date
     */
    private Date convertStringtoDate(String dateString)
    {
        //String dateString = "2015-04-20T00:00:00-07:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ssZZZZZ");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString.replace('T',' '));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
    //endregion
}
