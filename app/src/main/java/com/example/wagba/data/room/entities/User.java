package com.example.wagba.data.room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey
    @ColumnInfo(name = "userID")
    @NonNull
    private String userID;

    @ColumnInfo(name = "username")
    @NonNull
    private String username;

//    @ColumnInfo(name = "carts")
//    private ArrayList<CartModel> carts;

    public User(@NonNull String userID, @NonNull String username/*, ArrayList<CartModel> carts*/){
        this.userID =  userID;
        this.username = username;
//      this.carts = carts;
    }


    @NonNull
    public String getUserID() {
        return this.userID;
    }

    @NonNull
    public String getUsername() {
        return this.username;
    }

//    public ArrayList<CartModel> getCarts() {
//        return this.carts;
//    }
}
