package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cartItem_table", primaryKeys = {"userID", "cartID"})
public class CartItem {

    @NonNull
    @ColumnInfo(name = "userID")
    private String userID;

    @ColumnInfo(name = "cartID")
    @NonNull
    private String cartID;

    @ColumnInfo(name = "itemName")
    @NonNull
    private String itemName;

    @ColumnInfo(name = "itemRestaurant")
    @NonNull
    private String itemRestaurant;

    @ColumnInfo(name = "itemNum")
    @NonNull
    private String itemNum;

    @ColumnInfo(name = "itemPrice")
    @NonNull
    private String itemPrice;

    public CartItem(@NonNull String userID, @NonNull String cartID, @NonNull String itemName, @NonNull String itemRestaurant, @NonNull String itemNum, @NonNull String itemPrice) {
        this.userID = userID;
        this.cartID = cartID;
        this.itemName = itemName;
        this.itemRestaurant = itemRestaurant;
        this.itemNum = itemNum;
        this.itemPrice = itemPrice;
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
    public String getItemNum() {
        return itemNum;
    }

    @NonNull
    public String getItemPrice() {
        return itemPrice;
    }

    @NonNull
    public String getCartID() {
        return cartID;
    }
}
