package com.example.wagba;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Insert
    Long insertCart(Cart cart);

    @Insert
    void insertCartItem(CartItem cartItem);

    @Update
    void updateCart(Cart cart);

    @Update
    void updateItem(CartItem cartItem);

    @Delete
    void deleteCart(Cart cart);

    @Delete
    void deleteCartItem(CartItem cartItem);

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID LIKE :userID")
    LiveData<User> getUserByID(String userID);

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID=:userID")
    LiveData<UserWithCarts> getUserCarts(String userID);

    @Transaction
    @Query("SELECT * FROM cart_table WHERE cartID=:cartID")
    LiveData<CartWithCartItems> getCartItems(long cartID);


}
