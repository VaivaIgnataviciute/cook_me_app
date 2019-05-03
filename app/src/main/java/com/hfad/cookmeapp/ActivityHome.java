package com.hfad.cookmeapp;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ActivityHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.cookme_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        */

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {

                if (position == 0) {
                    Intent intent = new Intent(ActivityHome.this, BreakfastCategoryActivity.class);
                    startActivity(intent);
                }

                else if (position == 1) {
                    Intent intent = new Intent(ActivityHome.this, DinnerCategoryActivity.class);
                    startActivity(intent);
                }

                else if (position == 2) {
                    Intent intent = new Intent(ActivityHome.this, DessertCategoryActivity.class);
                    startActivity(intent);

                }

                else if (position == 3) {
                    Intent intent = new Intent(ActivityHome.this, HealthyCategoryActivity.class);
                    startActivity(intent);

                }


            }
        };

      //adding the listener to the list view

        ListView listView = findViewById(R.id.categories);
        listView.setOnItemClickListener(itemClickListener);


    }
}
