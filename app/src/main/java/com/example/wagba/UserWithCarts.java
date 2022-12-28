package com.example.wagba;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithCarts {
    @Embedded public User user;
    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public List<Cart> carts;

}
