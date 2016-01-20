package com.vericarte.catalog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vericarte.catalog.bussines.Categories;
import com.vericarte.catalog.custom.CategoryAdapter;
import com.vericarte.catalog.entitie.Category;

import java.util.List;

/**
 * Created by raul.gomez on 19/01/2016.
 */
public class CategoryActivity extends AppCompatActivity {
    CategoryAdapter adapter;
    Categories cat;
    List<Category> list;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else
        {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        listView = (ListView)findViewById(R.id.listViewCategory);
        cat = new Categories(this);
        list = cat.all();
        //call the custom adapter
        adapter = new CategoryAdapter(list, CategoryActivity.this);

        // Set the custom adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = (int) adapterView.getItemIdAtPosition(i);
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                intent.putExtra("category", id);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.push_right_in, R.animator.push_right_out);
    }
}
