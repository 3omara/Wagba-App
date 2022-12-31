package com.example.wagba.data.room.repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cartItem_table", primaryKeys = {"userID", "cartID", "itemName"})
public class CartItem {

    @ColumnInfo(name = "userID")
    @NonNull
    private String userID;

    @ColumnInfo(name = "cartID")
    @NonNull
    private long cartID;

    @ColumnInfo(name = "itemName")
    @NonNull
    private String itemName;

    @ColumnInfo(name = "itemRestaurant")
    @NonNull
    private String itemRestaurant;

    @ColumnInfo(name = "itemNum")
    @NonNull
    private Integer itemNum;

    @ColumnInfo(name = "itemPrice")
    @NonNull
    private Float itemPrice;

    @ColumnInfo(name = "imageURL")
    @NonNull
    private String imageURL;

    public CartItem(@NonNull String userID, @NonNull long cartID, @NonNull String itemName, @NonNull String itemRestaurant, @NonNull Integer itemNum, @NonNull Float itemPrice, @NonNull String imageURL) {
        this.userID = userID;
        this.cartID = cartID;
        this.itemName = itemName;
        this.itemRestaurant = itemRestaurant;
        this.itemNum = itemNum;
        this.itemPrice = itemPrice;
        this.imageURL = imageURL;
    }


    @NonNull
    public String getUserID() {
        return this.userID;
    }


    @NonNull
    public String getItemName() {
        return itemName;
    }

    @NonNull
    public String getItemRestaurant() {
        return itemRestaurant;
    }

    @NonNull
    public Integer getItemNum() {
        return itemNum;
    }

    @NonNull
    public Float getItemPrice() {
        return itemPrice;
    }

    @NonNull
    public long getCartID() {
        return cartID;
    }

    @NonNull
    public String getImageURL() {
        return imageURL;
    }

    public void setItemNum(@NonNull Integer itemNum) {
        this.itemNum = itemNum;
    }
}
