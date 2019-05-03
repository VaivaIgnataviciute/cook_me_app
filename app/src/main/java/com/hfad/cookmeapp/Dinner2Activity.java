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


public class Dinner2Activity extends AppCompatActivity {

    public static final String EXTRA_DINNER2ID = "dinner2Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        int dinner2Id = (Integer) getIntent().getExtras().get(EXTRA_DINNER2ID); //this is the id of the food user chose
        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DINNER",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id =?",
                    new String[]{Integer.toString(dinner2Id)},
                    null, null, null);

            //Moving to the first record in the Cursor

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextDinner2 = cursor.getString(0);
                String descriptionTextDinner2 = cursor.getString(1);
                int photoIdDinner2 = cursor.getInt(2);

                //Populating the dinner2 name
                TextView name = findViewById(R.id.nameDinner2);
                name.setText(nameTextDinner2);

                // populate the dinner2 description

                TextView description = findViewById(R.id.descriptionDinner2);
                description.setText(descriptionTextDinner2);

                //Populating the dinner image

                ImageView photo = findViewById(R.id.photoDinner2);
                photo.setImageResource(photoIdDinner2);
                photo.setContentDescription(nameTextDinner2);
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
