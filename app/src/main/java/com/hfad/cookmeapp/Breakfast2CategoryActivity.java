/*package com.hfad.cookmeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteException;
import  android.database.sqlite.SQLiteOpenHelper;
import  android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Breakfast2CategoryActivity extends AppCompatActivity {

    //adding private variables to close the database and curson with onDestroy() method.
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast2_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

//I am creating on click listeners for list_breakfast2,
        ListView list_breakfast2 = findViewById(R.id.list_breakfast2);
        SQLiteOpenHelper cookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            //referencing database
            db = cookmeappDatabaseHelper.getReadableDatabase();

            //creating cursor note I might need to identify which name to look
            cursor = db.query("BREAKFAST",
                    new String[]{"_id","NAME"},
                    "NAME=?",
                    new String[] {"Crepes"},null,null,null);

            //creating cursor adapter and map the contents of the NAME column to the text in the Listview
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1},
                    0);

            //set the adapter to the listview
            list_breakfast2.setAdapter(listAdapter);

            //display a toast message to the user if there was a problem with database

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
*/
