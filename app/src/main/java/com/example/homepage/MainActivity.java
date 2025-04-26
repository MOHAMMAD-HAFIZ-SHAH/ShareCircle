package com.example.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout categoriesGrid;
    private RecyclerView recommendationsRecyclerView;
    private RecyclerView moreItemsRecyclerView;
    private RecommendationAdapter recommendationAdapter;
    private RecommendationAdapter moreItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize views
        categoriesGrid = findViewById(R.id.categoriesGrid);
        recommendationsRecyclerView = findViewById(R.id.recommendationsRecyclerView);
        moreItemsRecyclerView = findViewById(R.id.moreItemsRecyclerView);

        // Set up categories
        setupCategories();

        // Set up recommendations
        setupRecommendations();

        // Set up more items
        setupMoreItems();

        // Set up click listeners for bottom navigation
        setupBottomNavigation();

        // Set up click listeners for top bar
        setupTopBar();
    }

    private void setupCategories() {
        // Clear existing views
        categoriesGrid.removeAllViews();

        // Define category data
        String[] categoryNames = {
                "Electronics", "Furniture", "Tools", "Books", "Gardening",
                "Outdoor Tools", "Sports", "Home Appliances", "Outdoor Equipments", "Event Supplies"
        };

        int[] categoryIcons = {
            R.drawable.electronics, R.drawable.furniture, R.drawable.tools,
            R.drawable.books, R.drawable.gardening, R.drawable.outdoor_tools,
            R.drawable.sports, R.drawable.home_appliances, R.drawable.outdoor_equipment,
            R.drawable.event_supplies
        };

        // Add category items to grid
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < categoryNames.length; i++) {
            View categoryView = inflater.inflate(R.layout.category_item, categoriesGrid, false);

            ImageView categoryIcon = categoryView.findViewById(R.id.categoryIcon);
            TextView categoryName = categoryView.findViewById(R.id.categoryName);

            categoryIcon.setImageResource(categoryIcons[i]);
            categoryName.setText(categoryNames[i]);

            final int position = i;
            categoryView.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this,
                        "Selected category: " + categoryNames[position],
                        Toast.LENGTH_SHORT).show();
                // Add your navigation logic here
            });

            categoriesGrid.addView(categoryView);
        }
    }

    private void setupRecommendations() {
        List<RecommendationItem> recommendationItems = new ArrayList<>();

        // Add recommendation items
        recommendationItems.add(new RecommendationItem("Sofa 2-seater", "200/Day", R.drawable.sofa));
        recommendationItems.add(new RecommendationItem("Lawn mower", "100/Day", R.drawable.lawn_mower));
        recommendationItems.add(new RecommendationItem("Play station 5", "100/Day", R.drawable.playstation));

        // Set up adapter
        recommendationAdapter = new RecommendationAdapter(recommendationItems);
        recommendationsRecyclerView.setAdapter(recommendationAdapter);
    }

    private void setupMoreItems() {
        List<RecommendationItem> moreItems = new ArrayList<>();

        // Add more items
        moreItems.add(new RecommendationItem("Bosch 550w drill", "50/Day", R.drawable.drill));
        moreItems.add(new RecommendationItem("Nova V&G Hair Dryer", "150/Day", R.drawable.hair_dryer));
        moreItems.add(new RecommendationItem("4 person trekking tent", "400/Day", R.drawable.tent));

        // Set up adapter
        moreItemsAdapter = new RecommendationAdapter(moreItems);
        moreItemsRecyclerView.setAdapter(moreItemsAdapter);
    }

    private void setupBottomNavigation() {
        // Set up click listeners for bottom navigation items
        findViewById(R.id.homeNav).setOnClickListener(v ->
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show());

        findViewById(R.id.chatsNav).setOnClickListener(v ->
                Toast.makeText(this, "Chats clicked", Toast.LENGTH_SHORT).show());

        findViewById(R.id.rentNav).setOnClickListener(v ->
                Toast.makeText(this, "Rent clicked", Toast.LENGTH_SHORT).show());

        findViewById(R.id.adsNav).setOnClickListener(v ->
                Toast.makeText(this, "My ADS clicked", Toast.LENGTH_SHORT).show());

        findViewById(R.id.profileNav).setOnClickListener(v ->
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show());
    }

    private void setupTopBar() {
        // Set up click listeners for top bar items
        ImageView wishlistIcon = findViewById(R.id.wishlistIcon);
        ImageView notificationIcon = findViewById(R.id.notificationIcon);
        TextView locationText = findViewById(R.id.locationText);

        wishlistIcon.setOnClickListener(v ->
                Toast.makeText(this, "Wishlist clicked", Toast.LENGTH_SHORT).show());

        notificationIcon.setOnClickListener(v ->
                Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show());

        locationText.setOnClickListener(v ->
                Toast.makeText(this, "Change location", Toast.LENGTH_SHORT).show());
    }
}