package com.vericarte.catalog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vericarte.catalog.webservices.GetListItunes;

import java.net.URISyntaxException;

/**
 * Created by raulgomez on 10/01/16.
 */
public class SplashScreen extends Activity {
    private GetListItunes list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final TextView titulo = (TextView) findViewById(R.id.textViewCatalog);
        final ImageView imagen = (ImageView) findViewById(R.id.imageViewCatalog);

        CargarAnimacion(titulo, imagen);
        list = new GetListItunes();
        Download algo = new Download();
        algo.execute("");
        //CargarMain();
    }

    private void CargarMain() {
        Runnable r1 = new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
            }
        };

        Handler h1 = new Handler();
        h1.postDelayed(r1, 2000);
    }

    private void CargarAnimacion(final TextView titulo, final ImageView imagen) {
        Runnable r = new Runnable() {
            @Override
            public void run(){
                titulo.setVisibility(View.VISIBLE);
                imagen.setVisibility(View.VISIBLE);
                titulo.startAnimation(AnimationUtils.loadAnimation(SplashScreen.this, android.R.anim.slide_in_left));
                imagen.startAnimation(AnimationUtils.loadAnimation(SplashScreen.this, android.R.anim.slide_in_left));
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 1000);
    }

    private class Download extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Hi", "Download Commencing");

            pDialog = new ProgressDialog(SplashScreen.this);
            pDialog.setMessage("Downloading Database...");


            String message= "Executing Process";

            SpannableString ss2 =  new SpannableString(message);
            ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

            pDialog.setMessage(ss2);

            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //INSERT YOUR FUNCTION CALL HERE
            try {
                list.getList();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return "Executed!";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Hi", "Done Downloading.");
            pDialog.dismiss();
            CargarMain();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
