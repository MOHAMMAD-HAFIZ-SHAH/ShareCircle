package com.example.rentapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyAdsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "RentAppPrefs";
    private static final String ADS_KEY = "ads_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AdItem> adList = loadAds();

        AdsAdapter adapter = new AdsAdapter(adList);
        recyclerView.setAdapter(adapter);
    }

    private List<AdItem> loadAds() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(ADS_KEY, null);

        if (json == null) return new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<AdItem>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
