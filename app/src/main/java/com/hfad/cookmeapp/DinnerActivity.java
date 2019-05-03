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

public class DinnerActivity extends AppCompatActivity {

    public static final String EXTRA_DINNERID = "dinnerId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        int dinnerId = (Integer) getIntent().getExtras().get(EXTRA_DINNERID); //this is the id of the food user chose

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {

            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DINNER",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id =?",
                    new String[]{Integer.toString(dinnerId)},
                    null, null, null);

            //Moving to the first record in the Cursor

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextDinner = cursor.getString(0);
                String descriptionTextDinner = cursor.getString(1);
                int photoIdDinner = cursor.getInt(2);

                //Populating the dinner name
                TextView name = findViewById(R.id.nameDinner);
                name.setText(nameTextDinner);

                // populate the dinner description

                TextView descriptionDinner = findViewById(R.id.descriptionDinner);
                descriptionDinner.setText(descriptionTextDinner);

                //Populating the dinner image

                ImageView photoDinner = findViewById(R.id.photoDinner);
                photoDinner.setImageResource(photoIdDinner);
                photoDinner.setContentDescription(nameTextDinner);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
