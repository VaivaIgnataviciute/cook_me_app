package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class Breakfast2Activity extends AppCompatActivity {

    public static final String EXTRA_BREAKFAST2ID = "BREAKFAST2Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        int breakfast2Id = (Integer) getIntent().getExtras().get(EXTRA_BREAKFAST2ID); //this is the id of the food user chose

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("BREAKFAST",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE", "INSTRUCTIONS"},
                    "_id =?",
                    new String[]{Integer.toString(breakfast2Id)},
                    null, null, null);

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextBreakfast2 = cursor.getString(0);
                String descriptionTextBreakfast2 = cursor.getString(1);
                int photoIdBreakfast2 = cursor.getInt(2);
                //if favorite column has a value of 1, this indicates a true value
                boolean isFavorite = (cursor.getInt(3) == 1);
                String instructionsTextBreakfast2 = cursor.getString(4);

                //Populating the breakfast name
                TextView name = findViewById(R.id.nameBreakfast2);
                name.setText(nameTextBreakfast2);

                // populate the breakfast2 description

                TextView description = findViewById(R.id.descriptionBreakfast2);
                description.setText(descriptionTextBreakfast2);

                //Populating the breakfast2 image

                ImageView photo = findViewById(R.id.photoBreakfast2);
                photo.setImageResource(photoIdBreakfast2);
                photo.setContentDescription(nameTextBreakfast2);

                //populate the favorite the favorite checkbox

                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                TextView instructions = findViewById(R.id.instructionsBreakfast2);
                instructions.setText(instructionsTextBreakfast2);
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
                    Intent favHome = new Intent(Breakfast2Activity.this, FavoritesActivity.class);
                    Breakfast2Activity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(Breakfast2Activity.this, ActivityHome.class);
                    Breakfast2Activity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });

    }

    public void onFavoriteClicked(View view) {
        int breakfast2Id = (Integer) getIntent().getExtras().get(EXTRA_BREAKFAST2ID);

        //get the value of the checkbox

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues breakfast2Values = new ContentValues();
        breakfast2Values.put("FAVORITE", favorite.isChecked());

        //get a reference to the database and update the favorite column

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();

            db.update("BREAKFAST", breakfast2Values, "_id=?", new String[]{Integer.toString(breakfast2Id)});
            db.close();

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}