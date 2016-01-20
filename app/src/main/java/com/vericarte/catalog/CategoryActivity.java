package com.vericarte.catalog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class CategoryActivity extends Activity{
    CategoryAdapter adapter;
    Categories cat;
    List<Category> list;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

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
}
