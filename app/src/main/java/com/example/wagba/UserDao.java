package com.example.wagba;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Insert
    void insertCart(Cart cart);

    @Insert
    void insertCartItem(CartItem cartItem);

//    @Query("DELETE FROM user_table")
//    void deleteAll();

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID=:userID")
    LiveData<User> getUserByID(String userID);


//    @Query("DELETE FROM cart_table")
//    void deleteAllCartItems();

    @Transaction
    @Query("DELETE FROM cart_table WHERE cartID=:cartID")
    void deleteCart(String cartID);

//    @Transaction
//    @Query("SELECT * FROM user_table WHERE orderID=:orderID")
//    LiveData<List<UserWithCarts>> getOrderItems(String orderID);

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID=:userID")
    LiveData<List<UserWithCarts>> getUserCarts(String userID);

    @Transaction
    @Query("SELECT * FROM cart_table WHERE cartID=:cartID")
    LiveData<List<CartWithCartItems>> getCartItems(String cartID);


}
