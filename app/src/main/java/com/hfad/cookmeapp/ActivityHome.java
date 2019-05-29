package com.hfad.cookmeapp;


import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;


public class ActivityHome extends AppCompatActivity {

    private Object container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


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
                } else if (position == 1) {
                    Intent intent = new Intent(ActivityHome.this, DinnerCategoryActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(ActivityHome.this, DessertCategoryActivity.class);
                    startActivity(intent);

                } else if (position == 3) {
                    Intent intent = new Intent(ActivityHome.this, HealthyCategoryActivity.class);
                    startActivity(intent);

                }


            }
        };

        //adding the listener to the list view

        ListView listView = findViewById(R.id.categories);
        listView.setOnItemClickListener(itemClickListener);

        //Make bottom navigation menu items work and create intents for them

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.action_favorite) {
                    Intent favHome = new Intent(ActivityHome.this, FavoritesActivity.class);
                    ActivityHome.this.startActivity(favHome);
                    return true;
                }
                return false;
            }
        });


    }


}
