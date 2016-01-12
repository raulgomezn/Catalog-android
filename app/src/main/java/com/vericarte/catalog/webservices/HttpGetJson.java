package com.vericarte.catalog.webservices;

import android.util.Log;

import com.vericarte.catalog.interfaces.IJSONDeserializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * Created by raulgomez on 11/01/16.
 */
public class HttpGetJson {
    private final static String LOG_TAG = "Http Service";

    // Tiempo maximo de espera para establecer conexion, 20 segundos.
    private static final int timeoutConnection = 60000;
    // Tiempo maximo de espera para esperar datos, 20 segundos.
    private static final int timeoutSocket = 60000;
    /**
     * Encapsula la comunicación con unservicio a traves de HTTP
     */
    HttpClient client = null;

    /**
     * Listado de parámetros a enviar al servicio
     */
    JSONObject paramObject;

    public HttpGetJson() {

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        client = new DefaultHttpClient(httpParameters);
        paramObject = new JSONObject();
    }

    public HttpEntity getParamObject() {
        HttpEntity entity = null;
        try {
            entity = new ByteArrayEntity(this.paramObject.toString().getBytes(
                    "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public void send(HttpUriRequest req, HttpResponse res, IJSONDeserializer jsonSer) throws IOException,
            JSONException {
        JSONObject result = new JSONObject();
        try {
            // Lanza la petición al servidor
            Log.i(LOG_TAG, "Peticion Servidor " + req.getRequestLine().toString());
            res = client.execute(req);
        } catch (HttpHostConnectException e) {
            Log.e(LOG_TAG, "Error, conexión al servicio abortadas", e);
        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, "Error en la petición realizada al servicio", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error, conexión al servicio abortada", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error, al hacer la petición", e);
        }

        if (res != null) {
            // Ejecución éxitosa de la petición al servidor
            try {
                // Si se lanza una IOException, esta continua hasta el BR

                HttpEntity resEntity = res.getEntity();

                String resEntityString = EntityUtils.toString(resEntity);

                Log.i(LOG_TAG, "Tamaño respuesta del servidor "
                        + resEntityString.length());
                Log.v(LOG_TAG, "Respuesta del servidor " + resEntityString);

                result = new JSONObject(resEntityString);

            } catch (JSONException e) {
                String msg = "Error el la cadena recuperada a partir del stream asociado a la respuesta del servicio";
                Log.e(LOG_TAG, msg, e);
                e.printStackTrace();

            } catch (Exception e) {
                String msg = "Error el la cadena recuperada a partir del stream asociado a la respuesta del servicio";
                Log.e(LOG_TAG, msg, e);
                e.printStackTrace();
            }

            jsonSer.convertFrom(result);

        }
    }

    public void send(URI uri, IJSONDeserializer jsonSer) throws IOException, JSONException {

        HttpGet req = new HttpGet();
        HttpResponse res = null;
        req.setURI(uri);
        req.addHeader("Content-Type", "application/json; charset=utf-8");
        req.addHeader("Accept-Language", "es,en;q=0.8,es-419;q=0.6");

        send(req, null, jsonSer);
    }

}