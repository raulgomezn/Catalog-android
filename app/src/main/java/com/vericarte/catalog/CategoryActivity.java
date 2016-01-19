package com.vericarte.catalog;

import android.app.Activity;
import android.os.Bundle;

import com.vericarte.catalog.bussines.Categories;
import com.vericarte.catalog.custom.CategoryAdapter;

import java.util.List;

/**
 * Created by raul.gomez on 19/01/2016.
 */
public class CategoryActivity extends Activity{
    CategoryAdapter adapter;
    Categories cat;
    List<Category> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        cat = new Categories(this);
        list = cat.all();
        //call the custom adapter
        adapter = new CategoryAdapter(list, CategoryActivity.this);
    }
}
