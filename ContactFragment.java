package com.example.platewise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.platewise.databinding.FragmentContactBinding;
import com.google.android.material.snackbar.Snackbar;

public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;
    private EditText searchQueryEditText;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the views using ViewBinding
        searchQueryEditText = binding.searchQuery;
        searchButton = binding.searchButton;

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // To prevent memory leaks
    }
}
