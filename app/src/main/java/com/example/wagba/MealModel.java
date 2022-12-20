package com.example.wagba;

import java.io.Serializable;

public class MealModel implements Serializable {
    String name;
    String availability;
    String imageURL;
    String price;

    public MealModel() {
    }

    public MealModel(String name, String availability, String imageURL, String price) {
        this.name = name;
        this.availability = availability;
        this.imageURL = imageURL;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public String getAvailability() {
        return availability;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }
}
