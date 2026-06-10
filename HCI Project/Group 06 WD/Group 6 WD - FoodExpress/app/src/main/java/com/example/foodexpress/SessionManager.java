package com.example.foodexpress;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {

        sharedPreferences = context.getSharedPreferences(
                "FoodExpressSession",
                Context.MODE_PRIVATE
        );

        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {

        return sharedPreferences.getBoolean(
                "isLoggedIn",
                false
        );
    }

    public void saveUserEmail(String email) {

        editor.putString("userEmail", email);
        editor.apply();
    }

    public String getUserEmail() {

        return sharedPreferences.getString(
                "userEmail",
                "Guest"
        );
    }

    public void logout() {

        editor.clear();
        editor.apply();
    }
}