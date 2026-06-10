package com.example.foodexpress;

public class CartItem {

    String name;
    String price;
    int image;

    public CartItem(String name, String price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}