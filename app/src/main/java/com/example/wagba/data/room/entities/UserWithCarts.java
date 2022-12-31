package com.example.wagba.data.room.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.wagba.data.room.entities.Cart;
import com.example.wagba.data.room.entities.User;

import java.util.List;

public class UserWithCarts {
    @Embedded public User user;
    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public List<Cart> carts;

}
