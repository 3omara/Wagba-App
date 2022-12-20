package com.example.wagba;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantModel {
    String name;
    String phone;
    String address;
    String description;
    String image;
    HashMap<String, MealModel> meals;

    public RestaurantModel(){

    }

    public RestaurantModel(String name, String phone, String address, String description, String image, HashMap<String, MealModel> meals) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.image = image;
        this.meals = meals;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public HashMap<String, MealModel> getMeals() {
        return meals;
    }

    public void setMeals(HashMap<String, MealModel> meals) {
        this.meals = meals;
    }
}
