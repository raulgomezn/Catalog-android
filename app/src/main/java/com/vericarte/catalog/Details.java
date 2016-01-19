package com.vericarte.catalog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vericarte.catalog.bussines.Aplications;
import com.vericarte.catalog.entities.Aplication;

/**
 * Created by raulgomez on 19/01/16.
 */
public class Details extends Activity {
    Aplications app;
    Aplication entity;
    TextView title;
    TextView summary;
    TextView price;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        title = (TextView) findViewById(R.id.textViewTitle);
        summary = (TextView) findViewById(R.id.textViewSummary);
        price = (TextView) findViewById(R.id.textViewPrice);
        image = (ImageView) findViewById(R.id.imageViewApp);

        app = new Aplications(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            entity = new Aplication();
            int value = extras.getInt("id");
            try {
                entity = app.get(value);
                title.setText(entity.getName());
                summary.setText(entity.getSummary());
                price.setText(entity.getPrice());
                image.setImageBitmap(getImage(entity));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getImage(Aplication aplication){
        Bitmap response;

        Bitmap bmp = BitmapFactory.decodeByteArray(aplication.getImage(), 0, aplication.getImage().length);
        response= bmp;

        return response;
    }
}
