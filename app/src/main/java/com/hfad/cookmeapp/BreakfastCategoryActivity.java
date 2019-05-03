package com.hfad.cookmeapp;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class BreakfastCategoryActivity extends AppCompatActivity {

    //adding private variables to close the database and curson with onDestroy() method.
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_category);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);



        //I am creating on click listeners for list_breakfast,
        ListView list_breakfast = findViewById(R.id.list_breakfast);
       SQLiteOpenHelper cookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);
       try {

           //referencing database
            db = cookmeappDatabaseHelper.getReadableDatabase();

            //creating cursor
            cursor = db.query("BREAKFAST",
                    new String[]{"_id","NAME"},
                    null,null,null,null,null);

            //creating cursor adapter and map the contents of the NAME column to the text in the Listview
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1},
                    0);

            //set the adapter to the listview
            list_breakfast.setAdapter(listAdapter);

            //display a toast message to the user if there was a problem with database
       } catch (SQLException e) {
           Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
           toast.show();
       }

        //Creating listerner for clicks
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listBreakfast, View itemView, int position, long id) {
                //pass the drink the user clicks on

                Intent intent = new Intent(BreakfastCategoryActivity.this, BreakfastActivity.class);
                intent.putExtra(BreakfastActivity.EXTRA_BREAKFASTID, (int) id);
                startActivity(intent);


                Intent intent2 = new Intent(BreakfastCategoryActivity.this, Breakfast2Activity.class);
                intent2.putExtra(Breakfast2Activity.EXTRA_BREAKFAST2ID, (int) id);
                startActivity(intent);

            }
        };


        //Assign the listener to the list view

        list_breakfast.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy (){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
