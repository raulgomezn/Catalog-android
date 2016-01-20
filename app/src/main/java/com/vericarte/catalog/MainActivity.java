package com.vericarte.catalog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vericarte.catalog.bussines.Aplications;
import com.vericarte.catalog.custom.CustomListAdapter;
import com.vericarte.catalog.entities.Aplication;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;
    Aplications app;
    List<Aplication> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = new Aplications(this);
        listView = (ListView)findViewById(R.id.customlist);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            int value = extras.getInt("category");
            list = app.filterByCategory(value);
        }
        else
        {
            list = app.all();
        }

        // Call the custom adapter
        adapter = new CustomListAdapter(list, MainActivity.this);
        // Set the custom adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = (int) adapterView.getItemIdAtPosition(i);
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
