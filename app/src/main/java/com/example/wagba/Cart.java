package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cart {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cartID")
    private String cartID;

    @ColumnInfo(name = "userID")
    @NonNull
    private String userID;

    @ColumnInfo(name = "cartName")
    @NonNull
    private String cartName;

    @ColumnInfo(name = "itemCount")
    @NonNull
    private String itemCount;

    @ColumnInfo(name = "cartCost")
    @NonNull
    private String cartCost;

    //    @ColumnInfo(name = "orderID")
    //    private String orderID;

    public Cart(@NonNull String cartID, @NonNull String userID, @NonNull String cartName, @NonNull String itemCount, @NonNull String cartCost) {
        this.cartID = cartID;
        this.userID = userID;
        this.cartName = cartName;
        this.itemCount = itemCount;
        this.cartCost = cartCost;
    }

    @NonNull
    public String getUserID() {
        return userID;
    }

    @NonNull
    public String getCartName() {
        return cartName;
    }

    @NonNull
    public String getItemCount() {
        return itemCount;
    }

    @NonNull
    public String getCartCost() {
        return cartCost;
    }


    @NonNull
    public String getCartID() {
        return cartID;
    }
}
