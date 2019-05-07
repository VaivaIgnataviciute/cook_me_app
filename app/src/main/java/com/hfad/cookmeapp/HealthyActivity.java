package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class HealthyActivity extends AppCompatActivity {

    public static final String EXTRA_HEALTHYID = "healthyId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        int healthyId = (Integer) getIntent().getExtras().get(EXTRA_HEALTHYID); //this is the id of the food user chose

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {

            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("HEALTHY",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID","FAVORITE"},
                    "_id =?",
                    new String[]{Integer.toString(healthyId)},
                    null, null, null);

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextHealthy = cursor.getString(0);
                String descriptionTextHealthy = cursor.getString(1);
                int photoIdHealthy = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                //Populating the healthy name
                TextView name = findViewById(R.id.nameHealthy);
                name.setText(nameTextHealthy);

                // populate the healthy description

                TextView description = findViewById(R.id.descriptionHealthy);
                description.setText(descriptionTextHealthy);

                //Populating the healthy image

                ImageView photo = findViewById(R.id.photoHealthy);
                photo.setImageResource(photoIdHealthy);
                photo.setContentDescription(nameTextHealthy);
                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onFavoriteClicked (View view) {
        int healthyId =(Integer) getIntent().getExtras().get(EXTRA_HEALTHYID);

        //get the value of the checkbox

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues healthyValues = new ContentValues();
        healthyValues.put("FAVORITE", favorite.isChecked());

        //get a reference to the database and update the favorite column

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();

            db.update("HEALTHY",healthyValues, "_id=?",new String[] {Integer.toString(healthyId)});
            db.close();

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
