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


public class DessertActivity extends AppCompatActivity {

    public static final String EXTRA_DESSERTID ="dessertId";
    private ShareActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        toolbar.setLogo(R.mipmap.cookme_logo);

        //get the drink from the intent

        int dessertId=(Integer)getIntent().getExtras().get(EXTRA_DESSERTID); //this is the id of the food user chose
        // Breakfast breakfast = Breakfast.breakfasts[breakfastId];//

        //Create a cursor

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DESSERTS",
                    new String[] {"NAME","DESCRIPTION","IMAGE_RESOURCE_ID","FAVORITE","INSTRUCTIONS"},
                    "_id =?",
                    new String[] {Integer.toString(dessertId)},
                    null,null,null);

            //Moving to the first record in the Cursor
            if (cursor.moveToFirst()){

                //getting the drink details from the cursor

                String nameTextDessert = cursor.getString(0);
                String descriptionTextDessert = cursor.getString(1);
                int photoIdDessert = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);
                String instructionsTextDessert = cursor.getString(4);

                //Populating the breakfast name
                TextView name= findViewById(R.id.nameDessert);
                name.setText(nameTextDessert);

                // populate the breakfast description

                TextView description= findViewById(R.id.descriptionDessert);
                description.setText(descriptionTextDessert);

                //Populating the breakfast image

                ImageView photo = findViewById(R.id.photoDessert);
                photo.setImageResource(photoIdDessert);
                photo.setContentDescription(nameTextDessert);

                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                TextView instructions = findViewById(R.id.instructionsDessert);
                instructions.setText(instructionsTextDessert);

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

                if (id ==  R.id.action_favorite){
                    Intent favHome = new Intent(DessertActivity.this, FavoritesActivity.class);
                    DessertActivity.this.startActivity(favHome);
                    return true;
                }

                if (id == R.id.action_home) {
                    Intent navHome = new Intent(DessertActivity.this, ActivityHome.class);
                    DessertActivity.this.startActivity(navHome);
                    return true;
                }
                return false;
            }
        });


    }

    //Update the database when the checkbox is clicked


    public void onFavoriteClicked (View view) {
        int dessertId =(Integer) getIntent().getExtras().get(EXTRA_DESSERTID);

        //get the value of the checkbox

        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues dessertValues = new ContentValues();
        dessertValues.put("FAVORITE", favorite.isChecked());

        //get a reference to the database and update the favorite column

        SQLiteOpenHelper CookmeappDatabaseHelper = new CookmeappDatabaseHelper(this);

        try {
            SQLiteDatabase db = CookmeappDatabaseHelper.getReadableDatabase();

            db.update("DESSERTS",dessertValues, "_id=?",new String[] {Integer.toString(dessertId)});
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
                intent.putExtra(Intent.EXTRA_SUBJECT,"Sharing Url");
                intent.putExtra(Intent.EXTRA_TEXT,"https://www.tasteofhome.com/recipes/five-fruit-pie/");
                try {
                    startActivity(Intent.createChooser(intent, "Select an action"));
                } catch (android.content.ActivityNotFoundException  ex ) {
                    Toast toast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
