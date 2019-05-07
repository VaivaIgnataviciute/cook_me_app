package com.hfad.cookmeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import  android.view.View;
import  android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;


public class FavoritesActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoritesCursorBreakfast;
    private Cursor favoritesCursorDinner;
    private Cursor favoritesCursorDessert;
    private Cursor favoritesCursorHealthy;

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

                if (id ==R.id.action_home) {
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
            favoritesCursorBreakfast = db.query("BREAKFAST",
                    new String[] {"_id", "NAME"},
                    "FAVORITE=1",
                    null,null,null,null);

           /* favoritesCursorDinner = db.query("DINNER",
                    new String[] {"_id", "NAME"},
                    "FAVORITE=1",
                    null,null,null,null);

            favoritesCursorDessert = db.query("DESSERTS",
                    new String[] {"_id", "NAME"},
                    "FAVORITE=1",
                    null,null,null,null);

            favoritesCursorHealthy = db.query("HEALTHY",
                    new String[] {"_id", "NAME"},
                    "FAVORITE=1",
                    null,null,null,null);
              */




            CursorAdapter favoriteAdapterBreakfast = new SimpleCursorAdapter(FavoritesActivity.this, android.R.layout.simple_list_item_1,
                    favoritesCursorBreakfast,
                    new String[] {"NAME"},
                    new int [] {android.R.id.text1},0);
            listFavorites.setAdapter(favoriteAdapterBreakfast);

           /* CursorAdapter favoriteAdapterDinner = new SimpleCursorAdapter(FavoritesActivity.this, android.R.layout.simple_list_item_1,
                    favoritesCursorDinner,
                    new String[] {"NAME"},
                    new int [] {android.R.id.text1},0);
            listFavorites.setAdapter(favoriteAdapterDinner);

            CursorAdapter favoriteAdapterDesserts = new SimpleCursorAdapter(FavoritesActivity.this, android.R.layout.simple_list_item_1,
                    favoritesCursorDessert,
                    new String[] {"NAME"},
                    new int [] {android.R.id.text1},0);
            listFavorites.setAdapter(favoriteAdapterDesserts);

            CursorAdapter favoriteAdapterHealthy = new SimpleCursorAdapter(FavoritesActivity.this, android.R.layout.simple_list_item_1,
                    favoritesCursorHealthy,
                    new String[] {"NAME"},
                    new int [] {android.R.id.text1},0);
            listFavorites.setAdapter(favoriteAdapterHealthy);
           */


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }


        //navigate to Breakfast activity if a drink is clicked

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentBreakfast= new Intent(FavoritesActivity.this, BreakfastActivity.class);
                intentBreakfast.putExtra(BreakfastActivity.EXTRA_BREAKFASTID,(int)id);
                startActivity(intentBreakfast);

                /*
                Intent intentDinner= new Intent(FavoritesActivity.this, DinnerActivity.class);
                intentDinner.putExtra(DinnerActivity.EXTRA_DINNERID,(int)id);
                startActivity(intentDinner);

                Intent intentDessert= new Intent(FavoritesActivity.this, DessertActivity.class);
                intentDessert.putExtra(DessertActivity.EXTRA_DESSERTID,(int)id);
                startActivity(intentDessert);

                Intent intentHealthy= new Intent(FavoritesActivity.this, HealthyActivity.class);
                intentHealthy.putExtra(HealthyActivity.EXTRA_HEALTHYID,(int)id);
                startActivity(intentHealthy);
                */
            }
        });
    }

    // Close the cursor and database in the Destroy() method

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesCursorBreakfast.close();
        db.close();


        /*
        favoritesCursorDinner.close();
        db.close();

        favoritesCursorDessert.close();
        db.close();


        favoritesCursorHealthy.close();
        db.close();
        */



    }
}
