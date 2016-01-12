package com.vericarte.catalog.webservices;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.vericarte.catalog.interfaces.IJSONDeserializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by raulgomez on 11/01/16.
 */
public class GetListItunes {

    private final String LOG_TAG = "GetListItunes";
    private URI uriMapper;
    private HttpGetJson request;
    //private entidad quickAnswers;


    public GetListItunes(URI uriMapper, HttpGetJson request) {
        this.uriMapper = uriMapper;
        this.request = request;
    }

    public void getList()
    {
        try {
            this.uriMapper = new URI("https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json");
            this.request.send(uriMapper,
                    new IJSONDeserializer() {
                        @Override
                        public void convertFrom(JSONObject json) {
                            try {

                                JSONArray array = json.getJSONArray("ListResult");
                                if(array != null){
                                    int size = array.length();
                                    for (int i = 0; i < size; i++) {
                                        QuickAnswer quickAnswer = new QuickAnswer();
                                        try {
                                            if (!array.isNull(i)) {
                                                quickAnswer.convertFrom(array.getJSONObject(i));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        quickAnswers.add(quickAnswer);
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
            Log.d(LOG_TAG, "No fue posible quick Answer ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
