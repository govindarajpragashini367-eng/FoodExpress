package com.example.foodexpress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Button btnGoLogin;
    Button btnGoRegister;
    Button btnOrderHistory;
    Button btnLocation;
    Button btnMealApi;
    Button btnSignOut;
    Button btnCart;

    TextView txtWelcomeUser;

    RecyclerView foodRecyclerView;

    ArrayList<FoodModel> foodList;

    FoodAdapter foodAdapter;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);

        btnGoLogin = findViewById(R.id.btnGoLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnLocation = findViewById(R.id.btnLocation);
        btnMealApi = findViewById(R.id.btnMealApi);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnCart = findViewById(R.id.btnCart);

        txtWelcomeUser = findViewById(R.id.txtWelcomeUser);

        foodRecyclerView = findViewById(R.id.foodRecyclerView);

        String userEmail = sessionManager.getUserEmail();

        txtWelcomeUser.setText("Welcome, " + userEmail);

        if (sessionManager.isLoggedIn()) {

            btnGoLogin.setVisibility(Button.GONE);
            btnGoRegister.setVisibility(Button.GONE);

            btnSignOut.setVisibility(Button.VISIBLE);

        } else {

            btnGoLogin.setVisibility(Button.VISIBLE);
            btnGoRegister.setVisibility(Button.VISIBLE);

            btnSignOut.setVisibility(Button.GONE);
        }

        foodList = new ArrayList<>();

        foodList.add(new FoodModel(
                "Chicken Burger",
                "Rs. 850",
                R.drawable.burger
        ));

        foodList.add(new FoodModel(
                "Cheese Pizza",
                "Rs. 1200",
                R.drawable.pizza
        ));

        foodList.add(new FoodModel(
                "Fried Rice",
                "Rs. 700",
                R.drawable.rice
        ));

        foodList.add(new FoodModel(
                "Chicken Kottu",
                "Rs. 900",
                R.drawable.kottu
        ));

        foodList.add(new FoodModel(
                "Fresh Juice",
                "Rs. 300",
                R.drawable.juice
        ));

        foodAdapter = new FoodAdapter(this, foodList);

        foodRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        foodRecyclerView.setAdapter(foodAdapter);

        btnGoLogin.setOnClickListener(view -> {

            startActivity(new Intent(
                    HomeActivity.this,
                    LoginActivity.class
            ));
        });

        btnGoRegister.setOnClickListener(view -> {

            startActivity(new Intent(
                    HomeActivity.this,
                    RegisterActivity.class
            ));
        });

        btnOrderHistory.setOnClickListener(view -> {

            if (sessionManager.isLoggedIn()) {

                startActivity(new Intent(
                        HomeActivity.this,
                        OrderHistoryActivity.class
                ));

            } else {

                Toast.makeText(
                        HomeActivity.this,
                        "Please login first",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        btnLocation.setOnClickListener(view -> {

            startActivity(new Intent(
                    HomeActivity.this,
                    LocationWeatherActivity.class
            ));
        });

        btnMealApi.setOnClickListener(view -> {

            startActivity(new Intent(
                    HomeActivity.this,
                    MealApiActivity.class
            ));
        });

        btnCart.setOnClickListener(view -> {

            startActivity(new Intent(
                    HomeActivity.this,
                    CartActivity.class
            ));
        });

        btnSignOut.setOnClickListener(view -> {

            sessionManager.logout();

            Toast.makeText(
                    HomeActivity.this,
                    "Logged Out",
                    Toast.LENGTH_SHORT
            ).show();

            startActivity(new Intent(
                    HomeActivity.this,
                    HomeActivity.class
            ));

            finish();
        });
    }
}