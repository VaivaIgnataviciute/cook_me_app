package com.hfad.cookmeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class HealthyCategoryActivity extends AppCompatActivity {

    //adding private variables to close the database and curson with onDestroy() method.
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_category);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        //I am creating on click listeners for list_breakfast,
        ListView list_healthy = findViewById(R.id.list_healthy);
        SQLiteOpenHelper cookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            //referencing database
            db = cookmeappDatabaseHelper.getReadableDatabase();

            //creating cursor
            cursor = db.query("HEALTHY",
                    new String[]{"_id","NAME"},
                    null,null,null,null,null);

            Log.d("cursor2", String.valueOf(cursor.getCount()));

            //creating cursor adapter and map the contents of the NAME column to the text in the Listview
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1},
                    0);

            //set the adapter to the listview
            list_healthy.setAdapter(listAdapter);

            //display a toast message to the user if there was a problem with database
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //Creating listerner for clicks
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listHealthy, View itemView, int position, long id) {
                //pass the drink the user clicks on

                Intent intent = new Intent(HealthyCategoryActivity.this, HealthyActivity.class);
                intent.putExtra(HealthyActivity.EXTRA_HEALTHYID, (int) id);
                startActivity(intent);

                Intent intent2 = new Intent(HealthyCategoryActivity.this, Healthy2Activity.class);
                intent2.putExtra(Healthy2Activity.EXTRA_HEALTHY2ID, (int) id);
                startActivity(intent);

            }
        };

        //Assign the listener to the list view

        list_healthy.setOnItemClickListener(itemClickListener);

        }

    @Override
    public void onDestroy (){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
