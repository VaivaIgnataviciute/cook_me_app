package com.hfad.cookmeapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

//creating database helper class to help us to create database. OnCreate() and OnUpgrade() methods are mandatory

public class CookmeappDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cookmeapp"; //the name of the database
    private static final int DB_VERSION = 1; //version of the database.Changing the version number to  a larger integer enables upgrade database.

    CookmeappDatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //database is creating once app is was opened.
        updateMyDatabase(db, 0, DB_VERSION); // the code to create any database tables is in updateMyDatabase()
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion); // the code to create any database tables is in updateMyDatabase()

    }

    //To insert multiple values I had to create method insertBreakfast with three parametres that I used to insert data
    private static void insertBreakfast(SQLiteDatabase db, String name, String description, int resourceId, String instructions) {

        ContentValues breakfastValues = new ContentValues();
        breakfastValues.put("NAME", name);
        breakfastValues.put("DESCRIPTION", description);
        breakfastValues.put("IMAGE_RESOURCE_ID", resourceId);
        breakfastValues.put("INSTRUCTIONS", instructions);
        db.insert("BREAKFAST", null, breakfastValues);
    }

    // I am creating insert methods for each meal category
    private static void insertDinner(SQLiteDatabase db, String name, String description, int resourceId, String instructions) {

        ContentValues dinnerValues = new ContentValues();
        dinnerValues.put("NAME", name);
        dinnerValues.put("DESCRIPTION", description);
        dinnerValues.put("IMAGE_RESOURCE_ID", resourceId);
        dinnerValues.put("INSTRUCTIONS", instructions);
        db.insert("DINNER", null, dinnerValues);


    }

    private static void insertDessert(SQLiteDatabase db, String name, String description, int resourceId, String instructions) {

        ContentValues dessertValues = new ContentValues();
        dessertValues.put("NAME", name);
        dessertValues.put("DESCRIPTION", description);
        dessertValues.put("IMAGE_RESOURCE_ID", resourceId);
        dessertValues.put("INSTRUCTIONS", instructions);
        db.insert("DESSERTS", null, dessertValues);


    }

    private static void insertHealthy(SQLiteDatabase db, String name, String description, int resourceId, String instructions) {

        ContentValues healthyValues = new ContentValues();
        healthyValues.put("NAME", name);
        healthyValues.put("DESCRIPTION", description);
        healthyValues.put("IMAGE_RESOURCE_ID", resourceId);
        healthyValues.put("INSTRUCTIONS", instructions);
        db.insert("HEALTHY", null, healthyValues);


    }

    public void resetDB(SQLiteDatabase db) {
        // delete content from the tables

        //insert new start data.

        db.execSQL("delete from " + "BREAKFAST");
        db.execSQL("delete from " + "DINNER");
        db.execSQL("delete from " + "DESSERTS");
        db.execSQL("delete from " + "HEALTHY");


        insertDessert(db, "Five fruit pie ", "Fruit pies may be served with a scoop of ice cream, a style known in North America as pie à la mode.", R.drawable.dessert, "1.Heat oven to 375°F. Toss blueberries, strawberries, raspberries, cornstarch, salt, and remaining 1/4 cup sugar in a bowl. Let sit, tossing occasionally, until juicy, 8 to 10 minutes.\n2.Fill piecrusts with the fruit mixture (about a heaping 1/2 cup each). Place stars on pies as desired. Bake until fruit is bubbling and crust is golden brown, 30 to 35 minutes.");
        insertDessert(db, "Chocolate brownies ", "A chocolate brownie is a square, baked, chocolate dessert that will melt your heart.", R.drawable.dessert_brownie, "1.In a medium bowl, beat together the butter and sugar. Add eggs, and mix well. Combine the flour, cocoa and salt; stir into the sugar mixture. Mix in the vanilla and stir in walnuts if desired. Spread evenly into the prepared pan.\n2.Bake for 25 to 30 minutes in the preheated oven, or until edges are firm. Cool before cutting into squares.");
        insertBreakfast(db, "Crepes", "Soft, buttery, delicious, and infinitely versatile as a sweet or savory option for breakfast, brunch, lunch, dinner or dessert!", R.drawable.breakfast_pancakes, "1.In a blender, combine the milk, eggs, butter, sugar, vanilla, salt and flour. Mix until batter is smooth (about 15-20 seconds).\n2.Spray non-stick cooking spray onto a 8 inch frying pan.\n3.Cook for about 1-2 minutes per side, or until lightly browned.");
        insertBreakfast(db, "French Omellete", "A classic French omelette has a smooth, silky exterior with little to no browning that cradles a tender, moist, soft-scrambled interior", R.drawable.breakfast_pancakes, "1.Beat eggs, water, salt and pepper in small bowl until blended.\n2.Heat butter in 6 to 8-inch nonstick omelet pan or skillet over medium-high heat until hot. TILT pan to coat bottom. POUR IN egg mixture. Mixture should set immediately at edges.\n3.Gently cooked portions from edges toward the center with inverted turner so that uncooked eggs can reach the hot pan surface. Continue cooking, tilting pan and gently moving cooked portions as needed.");
        insertDinner(db, "Beef burger", "Home made delicious beef angus burger with fresh paties", R.drawable.dinner_burger, "1.Place the beef mince, breadcrumbs, egg, onion, garlic, Worcestershire sauce and Tabasco sauce in large bowl. Season with salt and pepper. Mix with your hands until evenly combined.\n2.Prepare favorite vegetables and patties.\n3.Burgers meat grill for 15 min and place it in patties with vegetables");
        insertDinner(db, "Farfalle pasta", "bow tie pasta for its shape, farfalle (the Italian word for butterfly) makes a great cold pasta salad, and can also dress up a warm bowl of meat and veggies", R.drawable.dinner, "1.Place macaroni in a medium saucepan or skillet and add just enough cold water to cover. Add a pinch of salt and bring to a boil over high heat, stirring frequently. Continue to cook, stirring, until water has been almost completely absorbed and macaroni is just shy of al dente, about 6 minutes.\n2.Immediately add evaporated milk and bring to a boil. Add cheese. Reduce heat to low and cook, stirring continuously, until cheese is melted");
        insertHealthy(db, "Berries smoothie", "This simple berry smoothie works as a light breakfast or a delicious snack and is a delightful way to enjoy fresh or frozen berries", R.drawable.healthy_smoothie, "1.Place the apple juice, banana, berries and yogurt in a blender; blend until smooth. If the smoothie seems too thick, add a little more liquid (1/4 cup).\n2.Taste and add honey if desired. Pour into two glasses and garnish with fresh berries and mint sprigs if desired.");
        insertHealthy(db, "Chia  outmeal", "This chia oatmeal breakfast bowl is packed with all sorts of good stuff to keep you full and energized", R.drawable.healthy, "1.Combine almond milk, oats, chia seeds, honey, vanilla and dried fruit in a 250-mL Mason jar.\n2.Stir well, then cover and refrigerate overnight.\n3.Serving, top with banana and almonds.");


    }

    // I am creating tables and inserting values
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {
            //creating table BREAKFAST
            db.execSQL("CREATE TABLE BREAKFAST (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER,"
                    + "FAVORITE NUMERIC,"
                    + "INSTRUCTIONS TEXT);");


            //FOR THE SCREENSHOT CLARITY  I SHOW ONLY BREAKFAST TABLE AND HOW I AM INSERTING VALUES.


            db.execSQL("CREATE TABLE DINNER (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER,"
                    + "FAVORITE NUMERIC,"
                    + "INSTRUCTIONS TEXT);");


            db.execSQL("CREATE TABLE DESSERTS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER,"
                    + "FAVORITE NUMERIC,"
                    + "INSTRUCTIONS TEXT);");


            db.execSQL("CREATE TABLE HEALTHY (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER,"
                    + "FAVORITE NUMERIC,"
                    + "INSTRUCTIONS TEXT);");


            insertDessert(db, "Five fruit pie ", "Fruit pies may be served with a scoop of ice cream, a style known in North America as pie à la mode.", R.drawable.dessert, "1.Heat oven to 375°F. Toss blueberries, strawberries, raspberries, cornstarch, salt, and remaining 1/4 cup sugar in a bowl. Let sit, tossing occasionally, until juicy, 8 to 10 minutes.\n2.Fill piecrusts with the fruit mixture (about a heaping 1/2 cup each). Place stars on pies as desired. Bake until fruit is bubbling and crust is golden brown, 30 to 35 minutes.");
            insertDessert(db, "Chocolate brownies ", "A chocolate brownie is a square, baked, chocolate dessert that will melt your heart.", R.drawable.dessert_brownie, "1.In a medium bowl, beat together the butter and sugar. Add eggs, and mix well. Combine the flour, cocoa and salt; stir into the sugar mixture. Mix in the vanilla and stir in walnuts if desired. Spread evenly into the prepared pan.\n2.Bake for 25 to 30 minutes in the preheated oven, or until edges are firm. Cool before cutting into squares.");
            insertBreakfast(db, "Crepes", "Soft, buttery, delicious, and infinitely versatile as a sweet or savory option for breakfast, brunch, lunch, dinner or dessert!", R.drawable.breakfast_pancakes, "1.In a blender, combine the milk, eggs, butter, sugar, vanilla, salt and flour. Mix until batter is smooth (about 15-20 seconds).\n2.Spray non-stick cooking spray onto a 8 inch frying pan.\n3.Cook for about 1-2 minutes per side, or until lightly browned.");
            insertBreakfast(db, "French Omellete", "A classic French omelette has a smooth, silky exterior with little to no browning that cradles a tender, moist, soft-scrambled interior", R.drawable.breakfast_pancakes, "1.Beat eggs, water, salt and pepper in small bowl until blended.\n2.Heat butter in 6 to 8-inch nonstick omelet pan or skillet over medium-high heat until hot. TILT pan to coat bottom. POUR IN egg mixture. Mixture should set immediately at edges.\n3.Gently cooked portions from edges toward the center with inverted turner so that uncooked eggs can reach the hot pan surface. Continue cooking, tilting pan and gently moving cooked portions as needed.");
            insertDinner(db, "Beef burger", "Home made delicious beef angus burger with fresh paties", R.drawable.dinner_burger, "1.Place the beef mince, breadcrumbs, egg, onion, garlic, Worcestershire sauce and Tabasco sauce in large bowl. Season with salt and pepper. Mix with your hands until evenly combined.\n2.Prepare favorite vegetables and patties.\n3.Burgers meat grill for 15 min and place it in patties with vegetables");
            insertDinner(db, "Farfalle pasta", "bow tie pasta for its shape, farfalle (the Italian word for butterfly) makes a great cold pasta salad, and can also dress up a warm bowl of meat and veggies", R.drawable.dinner, "1.Place macaroni in a medium saucepan or skillet and add just enough cold water to cover. Add a pinch of salt and bring to a boil over high heat, stirring frequently. Continue to cook, stirring, until water has been almost completely absorbed and macaroni is just shy of al dente, about 6 minutes.\n2.Immediately add evaporated milk and bring to a boil. Add cheese. Reduce heat to low and cook, stirring continuously, until cheese is melted");
            insertHealthy(db, "Berries smoothie", "This simple berry smoothie works as a light breakfast or a delicious snack and is a delightful way to enjoy fresh or frozen berries", R.drawable.healthy_smoothie, "1.Place the apple juice, banana, berries and yogurt in a blender; blend until smooth. If the smoothie seems too thick, add a little more liquid (1/4 cup).\n2.Taste and add honey if desired. Pour into two glasses and garnish with fresh berries and mint sprigs if desired.");
            insertHealthy(db, "Chia  outmeal", "This chia oatmeal breakfast bowl is packed with all sorts of good stuff to keep you full and energized", R.drawable.healthy, "1.Combine almond milk, oats, chia seeds, honey, vanilla and dried fruit in a 250-mL Mason jar.\n2.Stir well, then cover and refrigerate overnight.\n3.Serving, top with banana and almonds.");

            resetDB(db);

        }


        if (oldVersion < 2) {
            //for the installed app users add the extra column to the database


        }
//
    }
}
