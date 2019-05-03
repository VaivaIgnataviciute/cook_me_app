package com.hfad.cookmeapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class BreakfastActivity extends AppCompatActivity {

    public static final String EXTRA_BREAKFASTID ="breakfastId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        //get the drink from the intent

        int breakfastId=(Integer)getIntent().getExtras().get(EXTRA_BREAKFASTID); //this is the id of the food user chose
       // Breakfast breakfast = Breakfast.breakfasts[breakfastId];//

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {

            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("BREAKFAST",
                    new String[] {"NAME","DESCRIPTION","IMAGE_RESOURCE_ID"},
                    "_id =?",
                    new String[] {Integer.toString(breakfastId)},
                    null,null,null);

            //Moving to the first record in the Cursor

            if (cursor.moveToFirst()){

                //getting the drink details from the cursor

                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                //Populating the breakfast name
                TextView name= findViewById(R.id.name);
                name.setText(nameText);

                // populate the breakfast description

                TextView description= findViewById(R.id.description);
                description.setText(descriptionText);

                //Populating the breakfast image

                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText( this, "Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }




    }


}
