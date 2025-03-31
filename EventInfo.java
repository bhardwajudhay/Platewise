package com.example.platewise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class EventInfo extends AppCompatActivity {
    private EditText et1, et2, et3;
    private Button svbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        // Initialize UI components
        et1 = findViewById(R.id.editText);        // Type field
        et2 = findViewById(R.id.editTextText2);   // Guest Number field
        et3 = findViewById(R.id.editTextText3);   // Food Preference field
        svbtn = findViewById(R.id.button);        // Save button

        MydbHelper dbhelper = new MydbHelper(this);  // Initialize database helper

        // Set the click listener for the save button
        svbtn.setOnClickListener(v -> {
            // Get the input from EditText fields
            String type = et1.getText().toString().trim();
            String guest = et2.getText().toString().trim();
            String food = et3.getText().toString().trim();

            // Log the data to check the inputs
            Log.d("EventInfo", "Saving data: Type=" + type + ", Guest No=" + guest + ", Food=" + food);

            // Validate input fields
            if (type.isEmpty() || guest.isEmpty() || food.isEmpty()) {
                Toast.makeText(EventInfo.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Insert data into the database
                boolean result = dbhelper.addEvent(type, guest, food);

                // Log the result of the insertion
                if (result) {
                    Log.d("EventInfo", "Data saved successfully");
                } else {
                    Log.e("EventInfo", "Failed to save data");
                }

                // Show feedback to the user
                if (result) {
                    Toast.makeText(EventInfo.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the fields after successful insertion
                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                } else {
                    Toast.makeText(EventInfo.this, "Failed to Save Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Query the database and log the data when the activity is created
        logDataFromDatabase(dbhelper);
    }

    // Method to query and log data from the database
    private void logDataFromDatabase(MydbHelper dbhelper) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();  // Get the readable database
        Cursor cursor = db.rawQuery("SELECT * FROM Event", null);  // Query the Event table

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // Access column indexes based on their names
                int typeIndex = cursor.getColumnIndex("Type");
                int guestNoIndex = cursor.getColumnIndex("GUEST_NO");
                int foodPrefIndex = cursor.getColumnIndex("FOOD_PREFERENCE");

                // Check if the columns exist (should not be -1)
                if (typeIndex != -1 && guestNoIndex != -1 && foodPrefIndex != -1) {
                    String type = cursor.getString(typeIndex);
                    String guestNo = cursor.getString(guestNoIndex);
                    String foodPref = cursor.getString(foodPrefIndex);

                    // Log the fetched data
                    Log.d("Database", "Type: " + type + ", Guest No: " + guestNo + ", Food Preference: " + foodPref);
                } else {
                    Log.e("Database", "Column not found!");
                }
            }
            cursor.close();  // Close the cursor
        } else {
            Log.d("Database", "No data found or cursor is empty");
        }

        db.close();  // Close the database connection
    }

}
