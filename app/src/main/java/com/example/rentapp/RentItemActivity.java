package com.example.rentapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RentItemActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;
    private Uri selectedImageUri;

    private EditText nameInput, priceInput, descInput;
    private Button submitButton;

    private static final String PREFS_NAME = "RentAppPrefs";
    private static final String ADS_KEY = "ads_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_item);

        selectedImageView = findViewById(R.id.itemImageView);
        nameInput = findViewById(R.id.itemName);
        priceInput = findViewById(R.id.itemPrice);
        descInput = findViewById(R.id.itemDesc);
        submitButton = findViewById(R.id.submitBtn);

        selectedImageView.setOnClickListener(v -> openImagePicker());

        submitButton.setOnClickListener(v -> {
            if (selectedImageUri == null || nameInput.getText().toString().isEmpty()
                    || priceInput.getText().toString().isEmpty()
                    || descInput.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
                return;
            }

            AdItem ad = new AdItem(selectedImageUri,
                    nameInput.getText().toString(),
                    priceInput.getText().toString(),
                    descInput.getText().toString());

            saveAd(ad);

            Toast.makeText(this, "Ad submitted!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to main or MyAds
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            selectedImageView.setImageURI(selectedImageUri);
        }
    }

    private void saveAd(AdItem ad) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString(ADS_KEY, null);
        Type type = new TypeToken<ArrayList<AdItem>>() {}.getType();
        List<AdItem> adList = gson.fromJson(json, type);

        if (adList == null) {
            adList = new ArrayList<>();
        }

        adList.add(ad);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ADS_KEY, gson.toJson(adList));
        editor.apply();
    }
}
