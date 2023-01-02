package com.example.wagba.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.wagba.data.room.entities.CartItem;
import com.example.wagba.data.room.entities.CartWithCartItems;
import com.example.wagba.data.room.entities.User;
import com.example.wagba.data.room.entities.UserWithCarts;
import com.example.wagba.data.room.entities.Cart;

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
    @Query("SELECT * FROM user_table WHERE userID LIKE :userID")
    User getUser_NonLive(String userID);

    @Transaction
    @Query("SELECT * FROM user_table WHERE userID=:userID")
    LiveData<UserWithCarts> getUserCarts(String userID);

    @Transaction
    @Query("SELECT * FROM cart_table WHERE cartID=:cartID")
    LiveData<CartWithCartItems> getCartItems(long cartID);

    @Transaction
    @Query("SELECT * FROM cart_table WHERE cartID=:cartID")
    CartWithCartItems getNonLiveCartItems(long cartID);

    @Transaction
    @Query("SELECT * FROM cart_table WHERE userID=:userID AND cartName=:cartName")
    Cart getCartByName(String userID, String cartName);

    @Transaction
    @Query("SELECT * FROM cart_table WHERE userID=:userID")
    LiveData<List<CartWithCartItems>> getAllCartsWithCartItems(String userID);

}
