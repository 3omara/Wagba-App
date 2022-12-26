package com.example.wagba;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UserWithCarts {
    @Embedded public User user;
    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public Cart cart;

}
