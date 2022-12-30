package com.example.wagba;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private MutableLiveData<List<CartItem>> cartItems = new MutableLiveData();
    private List<User> mAllUsers;

    public void getLiveCartItems(Long cartID){
        cartItems.setValue(mRepository.getCartItems(cartID).getValue().cartItems);
    }

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getmAllUsers();
    }

//    List<User> getAllUsers() { return mAllUsers; }
    LiveData<List<CartItem>> observeCartItems(){
        return this.cartItems;
    }

    public void insertUser(User user) { mRepository.insertUser(user); }
    public Long insertCart(Cart cart) throws ExecutionException, InterruptedException { return mRepository.insertCart(cart); }
    public void insertCartItem(CartItem item) { mRepository.insertCartItem(item); }

    public void updateCart(Cart cart) {mRepository.updateCart(cart);}
    public void updateItem(CartItem cartItem) {mRepository.updateItem(cartItem);}

    public void deleteCart(Cart cart) {mRepository.deleteCart(cart);}
    public void deleteCartItem(CartItem cartItem) {mRepository.deleteCartItem(cartItem);}

    public LiveData<User> getUserByID(String userID) { return mRepository.getUserByID(userID);}
    public LiveData<UserWithCarts> getUserCarts(String userID) { return mRepository.getUserCarts(userID);}
    public LiveData<CartWithCartItems> getCartItems(long cartID) { return mRepository.getCartItems(cartID);}
    public LiveData<List<CartWithCartItems>> getAllCartsWithCartItems(String userID){return mRepository.getAllCartsWithCartItems(userID);}
    CartWithCartItems getNonLiveCartItems(long cartID) throws ExecutionException, InterruptedException {return mRepository.getNonLiveCartItems(cartID);}
}
