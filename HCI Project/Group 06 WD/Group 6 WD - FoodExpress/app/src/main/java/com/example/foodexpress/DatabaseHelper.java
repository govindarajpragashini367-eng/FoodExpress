package com.example.foodexpress;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodExpress.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT)");

        db.execSQL("CREATE TABLE orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "foodName TEXT, " +
                "price TEXT, " +
                "orderDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    public boolean registerUser(String name, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);

        return result != -1;
    }

    public boolean checkUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    public boolean addOrder(String foodName, String price, String orderDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("foodName", foodName);
        values.put("price", price);
        values.put("orderDate", orderDate);

        long result = db.insert("orders", null, values);

        return result != -1;
    }

    public Cursor getOrders() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM orders ORDER BY id DESC", null);
    }
}