package com.example.platewise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class leftfood extends AppCompatActivity {
    private LinearLayout dynamicFieldsContainer;
    private ScrollView scrollView;
    private MydbHelper dbHelper;  // Database helper instance

    private static final String TAG = "LeftFoodActivity";  // Log tag for easier filtering in Logcat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftfood);

        // Initialize views
        EditText foodNameEditText = findViewById(R.id.foodName);
        EditText foodDescriptionEditText = findViewById(R.id.foodDescription);
        EditText userNameEditText = findViewById(R.id.userName);  // New EditText for User's name
        Button addFoodButton = findViewById(R.id.addFoodButton);
        Button submitButton = findViewById(R.id.submitButton);

        dynamicFieldsContainer = findViewById(R.id.dynamicFieldsContainer);
        scrollView = findViewById(R.id.scrollView);

        // Initialize the database helper
        dbHelper = new MydbHelper(this);

        // Set up the Add Food button to add dynamic fields
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the food item and description
                String foodName = foodNameEditText.getText().toString();
                String foodDescription = foodDescriptionEditText.getText().toString();

                // Add dynamic fields
                addDynamicFields();
            }
        });

        // Submit button action to save the data to the database
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect data from the input fields
                String foodName = foodNameEditText.getText().toString();
                String foodDescription = foodDescriptionEditText.getText().toString();
                String userName = userNameEditText.getText().toString();  // Get user's name

                // Retrieve dynamic fields (Quantity, Expiry Date, Category)
                EditText quantityEditText = (EditText) dynamicFieldsContainer.getChildAt(1);
                EditText expiryDateEditText = (EditText) dynamicFieldsContainer.getChildAt(3);
                EditText categoryEditText = (EditText) dynamicFieldsContainer.getChildAt(5);

                String quantity = quantityEditText.getText().toString();
                String expiryDate = expiryDateEditText.getText().toString();
                String category = categoryEditText.getText().toString();

                // Validate and save the data to the database
                try {
                    int quantityInt = Integer.parseInt(quantity);

                    // Save the food data to the database
                    boolean result = dbHelper.addFood(foodName, foodDescription, quantityInt, expiryDate, category, userName);

                    // Log the result of the insertion
                    if (result) {
                        Log.d(TAG, "Food saved: " + "Name: " + foodName + ", Description: " + foodDescription
                                + ", Quantity: " + quantityInt + ", Expiry Date: " + expiryDate + ", Category: " + category);

                        // Show feedback to the user
                        Toast.makeText(leftfood.this, "Food added to database!", Toast.LENGTH_SHORT).show();

                        // Optionally, clear the fields after successful insertion
                        foodNameEditText.setText("");
                        foodDescriptionEditText.setText("");
                        userNameEditText.setText("");  // Clear the name field
                        quantityEditText.setText("");
                        expiryDateEditText.setText("");
                        categoryEditText.setText("");

                        // Navigate to CertificateActivity
                        Intent intent = new Intent(leftfood.this, certificate.class);
                        intent.putExtra("USER_NAME", userName);
                        startActivity(intent);  // Start the new activity

                    } else {
                        Toast.makeText(leftfood.this, "Error saving food!", Toast.LENGTH_SHORT).show();
                    }

                    // Log data from the database after insertion
                    logFoodDataFromDatabase();
                } catch (NumberFormatException e) {
                    // Handle invalid quantity input
                    Toast.makeText(leftfood.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addDynamicFields() {
        // Create and add dynamic fields to the ScrollView's LinearLayout container

        // Quantity field
        TextView quantityLabel = new TextView(this);
        quantityLabel.setText("Quantity:");
        dynamicFieldsContainer.addView(quantityLabel);

        EditText quantityEditText = new EditText(this);
        quantityEditText.setHint("Enter quantity");
        dynamicFieldsContainer.addView(quantityEditText);

        // Expiry Date field
        TextView expiryDateLabel = new TextView(this);
        expiryDateLabel.setText("Expiry Date:");
        dynamicFieldsContainer.addView(expiryDateLabel);

        EditText expiryDateEditText = new EditText(this);
        expiryDateEditText.setHint("Enter expiry date");
        dynamicFieldsContainer.addView(expiryDateEditText);

        // Category field
        TextView categoryLabel = new TextView(this);
        categoryLabel.setText("Category:");
        dynamicFieldsContainer.addView(categoryLabel);

        EditText categoryEditText = new EditText(this);
        categoryEditText.setHint("Enter category");
        dynamicFieldsContainer.addView(categoryEditText);

        // Make sure the ScrollView scrolls down to the newly added fields
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    // Method to query and log food data from the database
    // Method to query and log food data from the database
    private void logFoodDataFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MydbHelper.TABLE_FOOD, null);

        if (cursor != null && cursor.getCount() > 0) {
            // Loop through the cursor data
            while (cursor.moveToNext()) {
                // Access column indexes based on their names
                int foodNameIndex = cursor.getColumnIndex("food_name");
                int foodDescriptionIndex = cursor.getColumnIndex("food_description");
                int quantityIndex = cursor.getColumnIndex("quantity");
                int expiryDateIndex = cursor.getColumnIndex("expiry_date");
                int categoryIndex = cursor.getColumnIndex("category");
                int userNameIndex = cursor.getColumnIndex("user_name");

                // Check if the columns exist (should not be -1)
                if (foodNameIndex != -1 && foodDescriptionIndex != -1 && quantityIndex != -1
                        && expiryDateIndex != -1 && categoryIndex != -1 && userNameIndex != -1) {

                    // Retrieve the data
                    String foodName = cursor.getString(foodNameIndex);
                    String foodDescription = cursor.getString(foodDescriptionIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    String expiryDate = cursor.getString(expiryDateIndex);
                    String category = cursor.getString(categoryIndex);
                    String userName = cursor.getString(userNameIndex);

                    // Log the fetched food data
                    Log.d(TAG, "Food: Name=" + foodName + ", Description=" + foodDescription + ", Quantity=" + quantity
                            + ", Expiry Date=" + expiryDate + ", Category=" + category + ", User Name=" + userName);
                } else {
                    // Log error if any column is missing
                    Log.e(TAG, "One or more columns not found!");
                }
            }
            cursor.close();  // Ensure cursor is closed after use
        } else {
            // Log if no data is found
            Log.d(TAG, "No food data found or cursor is empty");
        }

        db.close();  // Close the database connection
    }

}
