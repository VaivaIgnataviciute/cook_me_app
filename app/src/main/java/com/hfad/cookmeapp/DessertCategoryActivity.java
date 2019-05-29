package com.hfad.cookmeapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class DessertCategoryActivity extends AppCompatActivity {

    //adding private variables to close the database and curson with onDestroy() method.
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert_category);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        ListView list_dessert = findViewById(R.id.list_dessert);
        SQLiteOpenHelper cookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {

            //referencing database
            db = cookmeappDatabaseHelper.getReadableDatabase();

            //creating cursor
            cursor = db.query("DESSERTS",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            Log.d("cursor", String.valueOf(cursor.getCount()));

            //creating cursor adapter and map the contents of the NAME column to the text in the Listview
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);

            //set the adapter to the listview
            list_dessert.setAdapter(listAdapter);

            //display a toast message to the user if there was a problem with database

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            Log.d("DB", e.getMessage());
            toast.show();
        }
        //Creating listerner for clicks
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listDessert, View itemView, int position, long id) {
                //pass the drink the user clicks on

                Intent intent = new Intent(DessertCategoryActivity.this, DessertActivity.class);
                intent.putExtra(DessertActivity.EXTRA_DESSERTID, (int) id);
                startActivity(intent);

                Intent intent2 = new Intent(DessertCategoryActivity.this, Dessert2Activity.class);
                intent2.putExtra(Dessert2Activity.EXTRA_DESSERT2ID, (int) id);
                startActivity(intent);

            }
        };

        //Assign the listener to the list view

        list_dessert.setOnItemClickListener(itemClickListener);

        //creating intents to launch activities for bottom navigation icons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.action_favorite) {
                    Intent favHome = new Intent(DessertCategoryActivity.this, FavoritesActivity.class);
                    DessertCategoryActivity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(DessertCategoryActivity.this, ActivityHome.class);
                    DessertCategoryActivity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        db.close();
    }
}
