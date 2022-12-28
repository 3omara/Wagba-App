package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart_table")
public class Cart implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cartID")
    @NonNull
    private long cartID;

    @ColumnInfo(name = "userID")
    @NonNull
    private String userID;

    @ColumnInfo(name = "cartName")
    @NonNull
    private String cartName;

    @ColumnInfo(name = "itemCount")
    @NonNull
    private Integer itemCount;

    @ColumnInfo(name = "cartCost")
    @NonNull
    private Float cartCost;

    //    @ColumnInfo(name = "orderID")
    //    private String orderID;

    public Cart(@NonNull long cartID, @NonNull String userID, @NonNull String cartName, @NonNull Integer itemCount, @NonNull Float cartCost) {
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
    public Integer getItemCount() {
        return itemCount;
    }

    @NonNull
    public Float getCartCost() {
        return cartCost;
    }


    @NonNull
    public long getCartID() {
        return cartID;
    }

    public void setItemCount(@NonNull Integer itemCount) {
        this.itemCount = itemCount;
    }

    public void setCartCost(@NonNull Float cartCost) {
        this.cartCost = cartCost;
    }
}
