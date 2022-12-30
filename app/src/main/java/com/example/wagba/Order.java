package com.example.wagba;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Order {


    private String restaurantName;
    private String userID;
    private String date;
    private Float price;
    private Integer gate;
    private String timeSlot;
    private String deliveryStatus;
    private String payStatus;
    private HashMap<String, Integer> items;

    public Order(String userID, String restaurantName, Float price, Integer gate, String timeSlot, HashMap<String, Integer> items) {
        this.userID = userID;
        this.restaurantName = restaurantName;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = formatter.format(new Date().getTime());
        this.price = price;
        this.gate = gate;
        this.timeSlot = timeSlot;
        this.deliveryStatus = "Pending";
        this.payStatus = "Payment due";
        this.items = items;
    }

    public Order() {
    }

    public String getDate() {
        return date;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getGate() {
        return gate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getUserID() {
        return userID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
