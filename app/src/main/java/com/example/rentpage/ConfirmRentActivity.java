package com.example.rentpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfirmRentActivity extends AppCompatActivity {

    private ImageView rentImage;
    private EditText rentAddress, rentMobile, rentDeposit;
    private Button rentButton;

    private String itemId, imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_rent);

        rentImage = findViewById(R.id.rentImage);
        rentAddress = findViewById(R.id.rentAddress);
        rentMobile = findViewById(R.id.rentMobile);
        rentDeposit = findViewById(R.id.rentDeposit);
        rentButton = findViewById(R.id.rentButton);

        itemId = getIntent().getStringExtra("itemId");
        imageUrl = getIntent().getStringExtra("imageUrl");

        Glide.with(this).load(imageUrl).into(rentImage);

        rentButton.setOnClickListener(v -> {
            String address = rentAddress.getText().toString().trim();
            String mobile = rentMobile.getText().toString().trim();
            String deposit = rentDeposit.getText().toString().trim();

            if (address.isEmpty() || mobile.isEmpty() || deposit.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update Firestore to mark as rented
            FirebaseFirestore.getInstance().collection("items")
                    .document(itemId)
                    .update(
                            "isRented", true,
                            "rentedBy", FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            "renterAddress", address,
                            "renterMobile", mobile,
                            "renterDeposit", deposit
                    )
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Item rented successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ConfirmRentActivity.this, MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show());
        });
    }
}
