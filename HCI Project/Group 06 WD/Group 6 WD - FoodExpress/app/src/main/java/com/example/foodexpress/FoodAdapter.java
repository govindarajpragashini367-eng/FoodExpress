package com.example.foodexpress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    Context context;
    ArrayList<FoodModel> foodList;

    public FoodAdapter(Context context, ArrayList<FoodModel> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodModel food = foodList.get(position);

        holder.txtFoodName.setText(food.getName());
        holder.txtFoodPrice.setText(food.getPrice());
        holder.imgFood.setImageResource(food.getImage());

        holder.btnAddCart.setOnClickListener(view -> {
            CartManager.addToCart(new CartItem(
                    food.getName(),
                    food.getPrice(),
                    food.getImage()
            ));

            Toast.makeText(context, food.getName() + " added to cart", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, CartActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView txtFoodName, txtFoodPrice;
        Button btnAddCart;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
        }
    }
}