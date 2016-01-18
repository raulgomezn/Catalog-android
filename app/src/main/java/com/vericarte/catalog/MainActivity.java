package com.vericarte.catalog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        //call the custom adapter
        adapter = new CustomListAdapter(list, MainActivity.this);
        // set the custom adapter
        listView.setAdapter(adapter);
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
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
