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

public class DinnerActivity extends AppCompatActivity {

    public static final String EXTRA_DINNERID = "dinnerId";
    private ShareActionProvider shareActionProvider;
    ;

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
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE", "INSTRUCTIONS"},
                    "_id =?",
                    new String[]{Integer.toString(dinnerId)},
                    null, null, null);

            //Moving to the first record in the Cursor

            if (cursor.moveToFirst()) {

                //getting the drink details from the cursor

                String nameTextDinner = cursor.getString(0);
                String descriptionTextDinner = cursor.getString(1);
                int photoIdDinner = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);
                String instructionsTextDinner = cursor.getString(4);

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

                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                TextView instructionsDinner = findViewById(R.id.instructionsDinner);
                instructionsDinner.setText(instructionsTextDinner);
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
                    Intent favHome = new Intent(DinnerActivity.this, FavoritesActivity.class);
                    DinnerActivity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(DinnerActivity.this, ActivityHome.class);
                    DinnerActivity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });


    }

    public void onFavoriteClicked(View view) {
        int dinnerId = (Integer) getIntent().getExtras().get(EXTRA_DINNERID);

        //get the value of the checkbox

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues dinnerValues = new ContentValues();
        dinnerValues.put("FAVORITE", favorite.isChecked());

        //get a reference to the database and update the favorite column

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();

            db.update("DINNER", dinnerValues, "_id=?", new String[]{Integer.toString(dinnerId)});
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
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.tasteofhome.com/recipes/harvest-bow-ties/");
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
