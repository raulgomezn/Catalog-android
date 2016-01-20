package com.vericarte.catalog.webservices;

import android.content.Context;
import android.util.Log;

import com.vericarte.catalog.bussines.Aplications;
import com.vericarte.catalog.bussines.Categories;
import com.vericarte.catalog.entitie.Aplication;
import com.vericarte.catalog.entitie.Category;
import com.vericarte.catalog.interfaces.IJSONDeserializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Clase Webservices para obtener el listado de itunes por GET.
 * Created by raulgomez on 11/01/16.
 */
public class GetListItunes {

    //region Variables
    private final String LOG_TAG = "GetListItunes";
    private URI uriMapper;
    private HttpGetJson request;
    private Categories categoryBR;
    private Aplications appBR;
    //endregion

    //region Constructor
    /**
     * Constructor
     */
    public GetListItunes(Context context) {
        this.uriMapper = null;
        this.request = new HttpGetJson();
        this.categoryBR = new Categories(context);
        this.appBR = new Aplications(context);
    }
    //endregion

    //region Metodos
    public void getList() throws URISyntaxException {
        try {
            this.uriMapper = new URI("https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json");
            this.request.send(uriMapper,
                    new IJSONDeserializer() {
                        @Override
                        public void convertFrom(JSONObject json) {
                            try {
                                JSONArray array = json.getJSONObject("feed").getJSONArray("entry");

                                if(array != null){
                                    int size = array.length();
                                    for (int i = 0; i < size; i++)
                                    {
                                        Category cat = new Category();
                                        Aplication app = new Aplication();
                                        try {
                                            if (!array.isNull(i)) {
                                                app.convertFrom(array.getJSONObject(i));
                                                cat.convertFrom(array.getJSONObject(i));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        categoryBR.add(cat);
                                        appBR.add(app);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (NumberFormatException numEx) {
                                numEx.printStackTrace();
                            }
                        }
                    }
            );
        } catch (IOException e) {
            Log.d(LOG_TAG, "No fue posible obtener la lista.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
