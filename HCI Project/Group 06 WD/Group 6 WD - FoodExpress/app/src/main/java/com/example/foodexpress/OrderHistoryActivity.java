package com.example.foodexpress;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderHistoryActivity extends AppCompatActivity {

    LinearLayout orderContainer;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orderContainer = findViewById(R.id.orderContainer);
        databaseHelper = new DatabaseHelper(this);

        loadOrders();
    }

    private void loadOrders() {

        Cursor cursor = databaseHelper.getOrders();

        if (cursor.getCount() == 0) {

            TextView emptyText = new TextView(this);
            emptyText.setText("No orders found");
            emptyText.setTextSize(20);
            emptyText.setTextColor(Color.BLACK);
            emptyText.setPadding(0, 30, 0, 0);

            orderContainer.addView(emptyText);

        } else {

            while (cursor.moveToNext()) {

                String foodName = cursor.getString(1);
                String price = cursor.getString(2);
                String orderDate = cursor.getString(3);

                LinearLayout card = new LinearLayout(this);
                card.setOrientation(LinearLayout.VERTICAL);
                card.setPadding(20, 20, 20, 20);
                card.setBackgroundResource(R.drawable.card_bg);

                LinearLayout.LayoutParams cardParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                cardParams.setMargins(0, 0, 0, 16);
                card.setLayoutParams(cardParams);

                TextView title = new TextView(this);
                title.setText("Order Details");
                title.setTextSize(22);
                title.setTextColor(Color.parseColor("#FF6B00"));
                title.setTypeface(null, android.graphics.Typeface.BOLD);

                TextView food = new TextView(this);
                food.setText("Food: " + foodName);
                food.setTextSize(17);
                food.setTextColor(Color.BLACK);
                food.setPadding(0, 12, 0, 4);

                TextView total = new TextView(this);
                total.setText("Total: " + price);
                total.setTextSize(17);
                total.setTextColor(Color.BLACK);
                total.setPadding(0, 4, 0, 4);

                TextView date = new TextView(this);
                date.setText("Date: " + orderDate);
                date.setTextSize(15);
                date.setTextColor(Color.GRAY);
                date.setPadding(0, 4, 0, 0);

                card.addView(title);
                card.addView(food);
                card.addView(total);
                card.addView(date);

                orderContainer.addView(card);
            }
        }

        cursor.close();
    }
}