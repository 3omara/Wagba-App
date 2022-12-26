package com.example.wagba;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CartWithCartItems {
    @Embedded
    public Cart cart;
    @Relation(
            parentColumn = "cartID",
            entityColumn = "cartID"
    )
    public CartItem cartItem;
}
