package com.vericarte.catalog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by raulgomez on 10/01/16.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final TextView titulo = (TextView) findViewById(R.id.textViewCatalog);
        final ImageView imagen = (ImageView) findViewById(R.id.imageViewCatalog);

        CargarAnimacion(titulo, imagen);

        CargarMain();

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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
