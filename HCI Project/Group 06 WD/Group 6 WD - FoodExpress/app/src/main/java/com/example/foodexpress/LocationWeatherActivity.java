package com.example.foodexpress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LocationWeatherActivity extends AppCompatActivity {

    Button btnOpenMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_weather);

        btnOpenMap = findViewById(R.id.btnOpenMap);

        btnOpenMap.setOnClickListener(view -> {

            Uri uri = Uri.parse("geo:6.9271,79.8612?q=FoodExpress Restaurant Colombo");

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");

            startActivity(intent);
        });
    }
}