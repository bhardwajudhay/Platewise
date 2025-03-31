package com.example.platewise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.example.platewise.databinding.ActivityRecipeBinding;

public class Recipe extends AppCompatActivity {
    private EditText searchQueryEditText;
    private Button searchButton;
    private AppBarConfiguration appBarConfiguration;
    private ActivityRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize your views using ViewBinding
        searchQueryEditText = binding.searchQuery;
        searchButton = binding.searchButton;

        // Set up the toolbar with ViewBinding
        setSupportActionBar(binding.toolbar);

        // Set up navigation controller (if you are using navigation)
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recipe);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set the FloatingActionButton click listener
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display Snackbar (optional)
                Snackbar.make(view, "Search for recipe", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();

                // Perform recipe search
                String searchQuery = searchQueryEditText.getText().toString();
                if (!searchQuery.isEmpty()) {
                    searchForRecipe(searchQuery);
                }
            }
        });

        // Set the search button click listener (optional)
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchQueryEditText.getText().toString();
                if (!searchQuery.isEmpty()) {
                    searchForRecipe(searchQuery);
                }
            }
        });
    }

    // Method to perform the recipe search
    private void searchForRecipe(String query) {
        // Format the query to make it URL friendly
        String formattedQuery = query.replace(" ", "+");
        String url = "https://www.google.com/search?q=recipe+" + formattedQuery;

        // Create an intent to open a web browser with the search URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recipe);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
