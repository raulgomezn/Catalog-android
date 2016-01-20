package com.vericarte.catalog;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.vericarte.catalog.bussines.Aplications;
import com.vericarte.catalog.custom.CustomListAdapter;
import com.vericarte.catalog.entitie.Aplication;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    GridView gridLayout;
    CustomListAdapter adapter;
    Aplications app;
    List<Aplication> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = new Aplications(this);

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
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else
        {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        adapter = new CustomListAdapter(list, MainActivity.this);

        if (tabletSize) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            gridLayout =(GridView)findViewById(R.id.customGrid);
            gridLayout.setAdapter(adapter);
            gridLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int id = (int) adapterView.getItemIdAtPosition(i);
                    Intent intent = new Intent(MainActivity.this, Details.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            });
        }
        else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            listView = (ListView)findViewById(R.id.customlist);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int id = (int) adapterView.getItemIdAtPosition(i);
                    Intent intent = new Intent(MainActivity.this, Details.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(R.animator.push_right_in, R.animator.push_right_out);
                }
            });
        }


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
                overridePendingTransition(R.animator.push_right_in, R.animator.push_right_out);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
