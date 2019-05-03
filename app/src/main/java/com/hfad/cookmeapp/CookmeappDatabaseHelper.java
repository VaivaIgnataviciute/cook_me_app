package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

//creating database helper class to help us to create database. OnCreate() and OnUpgrade() methods are mandatory

  public class   CookmeappDatabaseHelper extends SQLiteOpenHelper {

      private static final String DB_NAME = "cookmeapp"; //the name of the database
      private  static final int DB_VERSION = 12; //version of the database.Changing the version number to  a larger integer enables upgrade database.

    CookmeappDatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) { //database is creating once app is was opened.

        updateMyDatabase(db, 0, DB_VERSION); // the code to create any database tables is in updateMyDatabase()
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion); // the code to create any database tables is in updateMyDatabase()

    }

    //To insert multiple values I had to create method insertBreakfast with three parametres that I used to insert data
    private static void insertBreakfast (SQLiteDatabase db, String name, String description, int resourceId) {

        ContentValues breakfastValues = new ContentValues();
        breakfastValues.put("NAME",name);
        breakfastValues.put("DESCRIPTION", description);
        breakfastValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("BREAKFAST", null, breakfastValues);
    }

    // I am creating insert methods for each meal category
      private static void insertDinner (SQLiteDatabase db, String name, String description, int resourceId) {

          ContentValues dinnerValues = new ContentValues();
          dinnerValues.put("NAME",name);
          dinnerValues.put("DESCRIPTION", description);
          dinnerValues.put("IMAGE_RESOURCE_ID", resourceId);
          db.insert("DINNER", null, dinnerValues);


      }

      private static void insertDessert (SQLiteDatabase db, String name, String description, int resourceId) {

          ContentValues dessertValues = new ContentValues();
          dessertValues.put("NAME",name);
          dessertValues.put("DESCRIPTION", description);
          dessertValues.put("IMAGE_RESOURCE_ID", resourceId);
          db.insert("DESSERTS", null, dessertValues);


      }

      private static void insertHealthy (SQLiteDatabase db, String name, String description, int resourceId) {

          ContentValues healthyValues = new ContentValues();
          healthyValues.put("NAME",name);
          healthyValues.put("DESCRIPTION", description);
          healthyValues.put("IMAGE_RESOURCE_ID", resourceId);
          db.insert("HEALTHY", null, healthyValues);


      }

      // I am creating tables and inserting values
    private void   updateMyDatabase (SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {

            //creating table BREAKFAST
            db.execSQL("CREATE TABLE BREAKFAST (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");


            //Inserting values to the table



            db.execSQL("CREATE TABLE DINNER (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");

            db.execSQL("CREATE TABLE DESSERTS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");




            db.execSQL("CREATE TABLE HEALTHY (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");



        }


        if (oldVersion <2 ) {
            //for the installed app users add the extra column to the database



        }

        if ( oldVersion <12 && newVersion == 12 ) {

            insertDinner(db, "Farfalle pasta", "bow tie pasta for its shape, farfalle (the Italian word for butterfly) makes a great cold pasta salad, and can also dress up a warm bowl of meat and veggies",R.drawable.dinner);
            insertDessert(db, "Five fruit pie ", "Fruit pies may be served with a scoop of ice cream, a style known in North America as pie à la mode.",R.drawable.dessert);
            insertDessert(db, "Chocolate brownies ", "A chocolate brownie is a square, baked, chocolate dessert that will melt your heart.",R.drawable.dessert_brownie);
            insertHealthy(db, "Berries smoothie", "This simple berry smoothie works as a light breakfast or a delicious snack and is a delightful way to enjoy fresh or frozen berries",R.drawable.healthy_smoothie);
            insertHealthy(db, "Chia  outmeal", "This chia oatmeal breakfast bowl is packed with all sorts of good stuff to keep you full and energized",R.drawable.healthy);
            insertBreakfast(db, "Crepes", "Soft, buttery, delicious, and infinitely versatile as a sweet or savory option for breakfast, brunch, lunch, dinner or dessert!",R.drawable.breakfast_pancakes);
            insertDinner(db, "Beef burger", "Home made delicious beef angus burger with fresh paties",R.drawable.dinner_burger);






        }
    }
}
