package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

//creating database helper class to help us to create database. OnCreate() and OnUpgrade() methods are mandatory

  public class   CookmeappDatabaseHelper extends SQLiteOpenHelper {

      private static final String DB_NAME = "cookmeapp"; //the name of the database
      private  static final int DB_VERSION = 13; //version of the database.Changing the version number to  a larger integer enables upgrade database.

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

        if ( oldVersion <13 && newVersion == 13 ) {


            insertBreakfast(db, "French Omellete", "A classic French omelette has a smooth, silky exterior with little to no browning that cradles a tender, moist, soft-scrambled interior.",R.drawable.omellet);






        }
    }
}
