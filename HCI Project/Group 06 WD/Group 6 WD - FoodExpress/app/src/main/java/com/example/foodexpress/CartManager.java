package com.example.foodexpress;

import java.util.ArrayList;

public class CartManager {

    public static ArrayList<CartItem> cartList = new ArrayList<>();

    public static void addToCart(CartItem item) {
        cartList.add(item);
    }

    public static ArrayList<CartItem> getCartItems() {
        return cartList;
    }

    public static void removeItem(CartItem item) {
        cartList.remove(item);
    }

    public static void clearCart() {
        cartList.clear();
    }
}