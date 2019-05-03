package com.hfad.cookmeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class Dessert2Activity extends AppCompatActivity {

    public static final String EXTRA_DESSERT2ID = "dessert2Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        //get the drink from the intent

        int dessert2Id = (Integer) getIntent().getExtras().get(EXTRA_DESSERT2ID); //this is the id of the food user chose
        // Breakfast breakfast = Breakfast.breakfasts[breakfastId];//

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DESSERTS",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id =?",
                    new String[]{Integer.toString(dessert2Id)},
                    null, null, null);

            //Moving to the first record in the Cursor
            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextDessert2 = cursor.getString(0);
                String descriptionTextDessert2 = cursor.getString(1);
                int photoIdDessert2 = cursor.getInt(2);

                //Populating the dessert2 name
                TextView name = findViewById(R.id.nameDessert2);
                name.setText(nameTextDessert2);

                // populate the dessert2 description

                TextView description = findViewById(R.id.descriptionDessert2);
                description.setText(descriptionTextDessert2);

                //Populating the dessert2 image

                ImageView photo = findViewById(R.id.photoDessert2);
                photo.setImageResource(photoIdDessert2);
                photo.setContentDescription(nameTextDessert2);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
