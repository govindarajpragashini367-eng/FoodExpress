package com.example.foodexpress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    LinearLayout cartContainer;
    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    int total = 0;
    String orderNames = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartContainer = findViewById(R.id.cartContainer);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        loadCartItems();
    }

    private void loadCartItems() {

        cartContainer.removeAllViews();

        TextView title = new TextView(this);
        title.setText("Your Cart");
        title.setTextSize(32);
        title.setTextColor(getResources().getColor(android.R.color.black));
        title.setPadding(0, 0, 0, 25);
        cartContainer.addView(title);

        total = 0;
        orderNames = "";

        if (CartManager.getCartItems().isEmpty()) {

            TextView emptyText = new TextView(this);
            emptyText.setText("Your cart is empty");
            emptyText.setTextSize(20);
            emptyText.setTextColor(getResources().getColor(android.R.color.black));
            emptyText.setPadding(0, 20, 0, 30);
            cartContainer.addView(emptyText);

        } else {

            for (CartItem item : CartManager.getCartItems()) {

                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                itemLayout.setPadding(20, 20, 20, 20);
                itemLayout.setBackgroundColor(getResources().getColor(android.R.color.white));

                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                params.setMargins(0, 0, 0, 25);
                itemLayout.setLayoutParams(params);

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(item.getImage());

                imageView.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                300
                        )
                );

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                TextView nameText = new TextView(this);
                nameText.setText(item.getName());
                nameText.setTextSize(22);
                nameText.setPadding(0, 15, 0, 5);

                TextView priceText = new TextView(this);
                priceText.setText(item.getPrice());
                priceText.setTextSize(18);
                priceText.setPadding(0, 5, 0, 15);

                Button btnRemove = new Button(this);
                btnRemove.setText("Remove");
                btnRemove.setTextColor(android.graphics.Color.WHITE);
                btnRemove.setBackgroundResource(R.drawable.cart_button_bg);

                btnRemove.setOnClickListener(view -> {
                    CartManager.removeItem(item);

                    Toast.makeText(
                            this,
                            "Item removed",
                            Toast.LENGTH_SHORT
                    ).show();

                    loadCartItems();
                });

                itemLayout.addView(imageView);
                itemLayout.addView(nameText);
                itemLayout.addView(priceText);
                itemLayout.addView(btnRemove);

                cartContainer.addView(itemLayout);

                String numberOnly = item.getPrice()
                        .replace("Rs.", "")
                        .trim();

                int price = Integer.parseInt(numberOnly);

                total += price;

                orderNames += item.getName() + ", ";
            }
        }

        TextView txtTotal = new TextView(this);
        txtTotal.setText("Total: Rs. " + total);
        txtTotal.setTextSize(24);
        txtTotal.setTextColor(android.graphics.Color.parseColor("#FF6B00"));
        txtTotal.setPadding(0, 30, 0, 25);
        cartContainer.addView(txtTotal);

        Button btnPlaceOrder = new Button(this);
        btnPlaceOrder.setText("Place Order");
        btnPlaceOrder.setTextColor(android.graphics.Color.WHITE);
        btnPlaceOrder.setTextSize(18);
        btnPlaceOrder.setBackgroundResource(R.drawable.cart_button_bg);
        cartContainer.addView(btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(view -> {

            if (!sessionManager.isLoggedIn()) {

                Toast.makeText(
                        this,
                        "Please login to place order",
                        Toast.LENGTH_SHORT
                ).show();

                startActivity(new Intent(
                        CartActivity.this,
                        LoginActivity.class
                ));

                return;
            }

            if (CartManager.getCartItems().isEmpty()) {

                Toast.makeText(
                        this,
                        "Cart is empty",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            String currentDate = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
            ).format(new Date());

            boolean inserted = databaseHelper.addOrder(
                    orderNames,
                    "Rs. " + total,
                    currentDate
            );

            if (inserted) {

                CartManager.clearCart();

                Toast.makeText(
                        this,
                        "Order Placed Successfully",
                        Toast.LENGTH_SHORT
                ).show();

                startActivity(new Intent(
                        CartActivity.this,
                        OrderSuccessActivity.class
                ));

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Order failed",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}