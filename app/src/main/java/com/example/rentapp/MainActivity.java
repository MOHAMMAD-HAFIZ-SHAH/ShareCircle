package com.example.rentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnRentItem, btnMyAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRentItem = findViewById(R.id.btnRentItem);
        btnMyAds = findViewById(R.id.btnMyAds);

        btnRentItem.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RentItemActivity.class);
            startActivity(i);
        });

        btnMyAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyAdsActivity.class);
                startActivity(intent);
            }
        });
    }
}
