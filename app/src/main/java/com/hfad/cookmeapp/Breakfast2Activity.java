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
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id =?",
                    new String[]{Integer.toString(breakfast2Id)},
                    null, null, null);

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextBreakfast2 = cursor.getString(0);
                String descriptionTextBreakfast2 = cursor.getString(1);
                int photoIdBreakfast2 = cursor.getInt(2);

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
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}