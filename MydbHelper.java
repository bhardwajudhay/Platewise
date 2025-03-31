package com.example.platewise;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MydbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PlatewiseDB";
    private static final int DATABASE_VERSION = 2; // Updated version to reflect schema change

    // Event table
    private static final String TABLE_EVENT = "Event";
    private static final String KEY_NAME = "Type"; // 1st column
    private static final String KEY_GUEST_NO = "GUEST_NO"; // 2nd column
    private static final String KEY_FOOD_PREF = "FOOD_PREFERENCE"; // 3rd column

    // Food table
    public static final String TABLE_FOOD = "Food";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FOOD_NAME = "food_name";
    private static final String COLUMN_FOOD_DESCRIPTION = "food_description";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_USER_NAME = "user_name";  // New column for user name

    public MydbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database", "Creating tables...");

        // Create Event Table
        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + " ("
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_GUEST_NO + " TEXT NOT NULL, "
                + KEY_FOOD_PREF + " TEXT NOT NULL);";
        db.execSQL(CREATE_EVENT_TABLE);

        // Create Food Table with new user_name column
        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FOOD_NAME + " TEXT, "
                + COLUMN_FOOD_DESCRIPTION + " TEXT, "
                + COLUMN_QUANTITY + " INTEGER, "
                + COLUMN_EXPIRY_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_USER_NAME + " TEXT);"; // Added new column
        db.execSQL(CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    // Add Event data
    public boolean addEvent(String type, String guestNo, String foodPref) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, type);           // Event Type
        values.put(KEY_GUEST_NO, guestNo);    // Number of guests
        values.put(KEY_FOOD_PREF, foodPref);  // Food Preference

        long result = db.insert(TABLE_EVENT, null, values);
        db.close();
        return result != -1;  // Returns true if insertion was successful
    }

    // Add food data, now including user_name
    public boolean addFood(String foodName, String foodDescription, int quantity, String expiryDate, String category, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_FOOD_DESCRIPTION, foodDescription);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_EXPIRY_DATE, expiryDate);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_USER_NAME, userName); // Store the user's name

        long result = db.insert(TABLE_FOOD, null, values);
        db.close();
        return result != -1;
    }
}
