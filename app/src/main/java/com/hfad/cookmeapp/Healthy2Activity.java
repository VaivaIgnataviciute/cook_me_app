package com.hfad.cookmeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class Healthy2Activity extends AppCompatActivity {

    public static final String EXTRA_HEALTHY2ID = "healthy2Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        int healthy2Id = (Integer) getIntent().getExtras().get(EXTRA_HEALTHY2ID); //this is the id of the food user chose

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("HEALTHY",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "INSTRUCTIONS"},
                    "_id =?",
                    new String[]{Integer.toString(healthy2Id)},
                    null, null, null);

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextHealthy2 = cursor.getString(0);
                String descriptionTextHealthy2 = cursor.getString(1);
                int photoIdHealthy2 = cursor.getInt(2);
                String instructionsTextHealthy2 = cursor.getString(3);

                //Populating the healthy name
                TextView name = findViewById(R.id.nameHealthy2);
                name.setText(nameTextHealthy2);

                // populate the healthy description

                TextView description = findViewById(R.id.descriptionHealthy2);
                description.setText(descriptionTextHealthy2);

                //Populating the healthy image

                ImageView photo = findViewById(R.id.photoHealthy2);
                photo.setImageResource(photoIdHealthy2);
                photo.setContentDescription(nameTextHealthy2);

                TextView instructions = findViewById(R.id.instructionsHealthy2);
                instructions.setText(instructionsTextHealthy2);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //creating intents to launch activities for bottom navigation icons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.action_favorite) {
                    Intent favHome = new Intent(Healthy2Activity.this, FavoritesActivity.class);
                    Healthy2Activity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(Healthy2Activity.this, ActivityHome.class);
                    Healthy2Activity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });

    }
}
