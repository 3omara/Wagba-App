package com.example.wagba.data.room.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CartWithCartItems {
    @Embedded
    public Cart cart;
    @Relation(
            parentColumn = "cartID",
            entityColumn = "cartID"
    )
    public List<CartItem> cartItems;
}
