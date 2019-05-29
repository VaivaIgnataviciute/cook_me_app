package com.hfad.cookmeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class FavoritesActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setupFavoritesListView();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(FavoritesActivity.this, ActivityHome.class);
                    FavoritesActivity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });

    }

    private void setupFavoritesListView() {
        //Populate the list_favorites ListView from a cursor

        ListView listFavorites = findViewById(R.id.list_favorites);

        try {
            SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);
            db = CookmeappDatabaseHelper.getReadableDatabase();

            ArrayList<String> list = new ArrayList<>();


            //For screenshot clarity I will show only two tables examples

            favoritesCursor = db.query("BREAKFAST",
                    new String[]{"_id", "NAME"},
                    "FAVORITE=1",
                    null, null, null, null);

            while (favoritesCursor.moveToNext()) {
                //get the name
                String name = favoritesCursor.getString(favoritesCursor.getColumnIndex("NAME"));
                list.add(name);

            }
            favoritesCursor.close();

            favoritesCursor = db.query("DINNER",
                    new String[]{"_id", "NAME"},
                    "FAVORITE=1",
                    null, null, null, null);

            while (favoritesCursor.moveToNext()) {
                //get the name
                String name = favoritesCursor.getString(favoritesCursor.getColumnIndex("NAME"));
                list.add(name);
            }

            favoritesCursor = db.query("DESSERTS",
                    new String[]{"_id", "NAME"},
                    "FAVORITE=1",
                    null, null, null, null);

            while (favoritesCursor.moveToNext()) {
                //get the name
                String name = favoritesCursor.getString(favoritesCursor.getColumnIndex("NAME"));
                list.add(name);
            }

            favoritesCursor = db.query("HEALTHY",
                    new String[]{"_id", "NAME"},
                    "FAVORITE=1",
                    null, null, null, null);

            while (favoritesCursor.moveToNext()) {
                //get the name
                String name = favoritesCursor.getString(favoritesCursor.getColumnIndex("NAME"));
                list.add(name);

            }

            ArrayAdapter<String> favoriteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listFavorites.setAdapter(favoriteAdapter);


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }


    // Close the cursor and database in the Destroy() method

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        db.close();


    }
}
