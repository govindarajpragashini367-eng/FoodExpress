package com.example.foodexpress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class MealApiActivity extends AppCompatActivity {

    Button btnLoadMeal;
    TextView txtMealResult;
    ImageView imgMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_api);

        btnLoadMeal = findViewById(R.id.btnLoadMeal);
        txtMealResult = findViewById(R.id.txtMealResult);
        imgMeal = findViewById(R.id.imgMeal);

        btnLoadMeal.setOnClickListener(view -> loadMealData());
    }

    private void loadMealData() {

        String url = "https://www.themealdb.com/api/json/v1/1/random.php";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray meals = response.getJSONArray("meals");
                        JSONObject meal = meals.getJSONObject(0);

                        String name = meal.getString("strMeal");
                        String category = meal.getString("strCategory");
                        String area = meal.getString("strArea");
                        String instructions = meal.getString("strInstructions");
                        String imageUrl = meal.getString("strMealThumb");

                        if (instructions.length() > 250) {
                            instructions = instructions.substring(0, 250) + "...";
                        }

                        txtMealResult.setText(
                                "Meal Name: " + name + "\n\n" +
                                        "Category: " + category + "\n\n" +
                                        "Cuisine Area: " + area + "\n\n" +
                                        "Description: " + instructions
                        );

                        loadImage(imageUrl);

                    } catch (Exception e) {
                        Toast.makeText(this, "Data parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "API loading error", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void loadImage(String imageUrl) {
        new Thread(() -> {
            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                runOnUiThread(() -> imgMeal.setImageBitmap(bitmap));

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Image loading error", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}