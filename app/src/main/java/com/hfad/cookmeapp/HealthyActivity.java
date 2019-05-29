package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    private ShareActionProvider shareActionProvider;


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
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE", "INSTRUCTIONS"},
                    "_id =?",
                    new String[]{Integer.toString(healthyId)},
                    null, null, null);

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextHealthy = cursor.getString(0);
                String descriptionTextHealthy = cursor.getString(1);
                int photoIdHealthy = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);
                String instructionsTextHealthy = cursor.getString(4);

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

                TextView instructions = findViewById(R.id.instructionsHealthy);
                instructions.setText(instructionsTextHealthy);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //creating intents to launch activities for bottom navigation icons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.action_favorite) {
                    Intent favHome = new Intent(HealthyActivity.this, FavoritesActivity.class);
                    HealthyActivity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(HealthyActivity.this, ActivityHome.class);
                    HealthyActivity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });


    }

    public void onFavoriteClicked(View view) {
        int healthyId = (Integer) getIntent().getExtras().get(EXTRA_HEALTHYID);

        //get the value of the checkbox

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues healthyValues = new ContentValues();
        healthyValues.put("FAVORITE", favorite.isChecked());

        //get a reference to the database and update the favorite column

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();

            db.update("HEALTHY", healthyValues, "_id=?", new String[]{Integer.toString(healthyId)});
            db.close();

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    //Adding share button from share_menu.xml to toolbar t
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);


        MenuItem item = menu.findItem(R.id.menu_item_share);

        shareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    //Implementing click listerner and share function that redirects to external recipe browser link.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Url");
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.dinneratthezoo.com/mixed-berry-smoothie/");
                try {
                    startActivity(Intent.createChooser(intent, "Select an action"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
