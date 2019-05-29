package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class Dinner2Activity extends AppCompatActivity {

    public static final String EXTRA_DINNER2ID = "dinner2Id";

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        int dinner2Id = (Integer) getIntent().getExtras().get(EXTRA_DINNER2ID); //this is the id of the food user chose
        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DINNER",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE", "INSTRUCTIONS"},
                    "_id =?",
                    new String[]{Integer.toString(dinner2Id)},
                    null, null, null);

            //Moving to the first record in the Cursor

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextDinner2 = cursor.getString(0);
                String descriptionTextDinner2 = cursor.getString(1);
                int photoIdDinner2 = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);
                String instructionsTextDinner2 = cursor.getString(4);

                //Populating the dinner2 name
                TextView name = findViewById(R.id.nameDinner2);
                name.setText(nameTextDinner2);

                // populate the dinner2 description

                TextView description = findViewById(R.id.descriptionDinner2);
                description.setText(descriptionTextDinner2);

                //Populating the dinner image

                ImageView photo = findViewById(R.id.photoDinner2);
                photo.setImageResource(photoIdDinner2);
                photo.setContentDescription(nameTextDinner2);

                //populate the favorite checkbox

                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                TextView instructions = findViewById(R.id.instructionsDinner2);
                instructions.setText(instructionsTextDinner2);
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
                    Intent favHome = new Intent(Dinner2Activity.this, FavoritesActivity.class);
                    Dinner2Activity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(Dinner2Activity.this, ActivityHome.class);
                    Dinner2Activity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });

    }

    public void onFavoriteClicked(View view) {
        int dinner2Id = (Integer) getIntent().getExtras().get(EXTRA_DINNER2ID);

        //get the value of the checkbox

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues dinner2Values = new ContentValues();
        dinner2Values.put("FAVORITE", favorite.isChecked());

        //get a reference to the database and update the favorite column

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();

            db.update("DINNER", dinner2Values, "_id=?", new String[]{Integer.toString(dinner2Id)});
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

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }


    //Implementing click listerner and share function that redirects to external recipe browser link.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Url");
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.bbcgoodfood.com/recipes/1514/beef-burgers-learn-to-make");
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
