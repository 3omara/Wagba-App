package com.example.wagba.data.room.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.wagba.data.room.entities.CartWithCartItems;
import com.example.wagba.data.room.entities.User;
import com.example.wagba.data.room.dao.UserDao;
import com.example.wagba.data.room.database.UserRoomDatabase;
import com.example.wagba.data.room.entities.UserWithCarts;
import com.example.wagba.data.room.entities.Cart;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private UserDao mUserDao;
    private List<User> mAllUsers;

    public UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
//        mAllUsers = mUserDao.getAllUsers();
    }

    public List<User> getmAllUsers() {
        return mAllUsers;
    }

    public void insertUser (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }
    public Long insertCart (Cart cart) throws ExecutionException, InterruptedException {
       insertCartAsyncTask task = new insertCartAsyncTask(mUserDao);
        Log.i("ROOM", "insertCart: 32");
       return task.execute(cart).get();
    }
    public void insertCartItem (CartItem cartItem) {
        new insertItemAsyncTask(mUserDao).execute(cartItem);
    }

    public void deleteCart (Cart cart){ new deleteCartAsyncTask(mUserDao).execute(cart);}
    public void deleteCartItem (CartItem cartItem){ new deleteItemAsyncTask(mUserDao).execute(cartItem);}

    public void updateCart (Cart cart) {new updateCartAsyncTask(mUserDao).execute(cart);}
    public void updateItem (CartItem cartItem) {new updateItemAsyncTask(mUserDao).execute(cartItem);}

    public LiveData<User> getUserByID (String userID) { return mUserDao.getUserByID(userID); }
    public LiveData<UserWithCarts> getUserCarts (String userID) { return mUserDao.getUserCarts(userID); }
    public LiveData<CartWithCartItems> getCartItems (long cartID) { return mUserDao.getCartItems(cartID); }
    public LiveData<List<CartWithCartItems>> getAllCartsWithCartItems(String userID){return mUserDao.getAllCartsWithCartItems(userID);}
    public CartWithCartItems getNonLiveCartItems(long cartID) throws ExecutionException, InterruptedException {
        getNonLiveCartItems task = new getNonLiveCartItems(mUserDao);
        return task.execute(cartID).get();
    }
    public Cart getCartByName(String userID, String cartName) throws ExecutionException, InterruptedException {
        getCartByName task = new getCartByName(mUserDao);
        return task.execute(userID, cartName).get();
    }


    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insertUser(params[0]);
            return null;
        }
    }

    private static class insertCartAsyncTask extends AsyncTask<Cart, Void, Long> {

        private UserDao mAsyncTaskDao;
        private long cartID;


        insertCartAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Cart... params) {
            Log.i("ROOM", "insertCart: 70");
            cartID = mAsyncTaskDao.insertCart(params[0]);
            Log.i("ROOM", "insertCart: 72");
            return cartID;
        }
    }

    private static class insertItemAsyncTask extends AsyncTask<CartItem, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertItemAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItem... params) {
            mAsyncTaskDao.insertCartItem(params[0]);
            return null;
        }
    }

    private static class updateCartAsyncTask extends AsyncTask<Cart, Void, Void> {

        private UserDao mAsyncTaskDao;

        updateCartAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cart... params) {
            mAsyncTaskDao.updateCart(params[0]);
            return null;
        }
    }

    private static class updateItemAsyncTask extends AsyncTask<CartItem, Void, Void> {

        private UserDao mAsyncTaskDao;

        updateItemAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItem... params) {
            mAsyncTaskDao.updateItem(params[0]);
            return null;
        }
    }

    private static class deleteItemAsyncTask extends AsyncTask<CartItem, Void, Void> {

        private UserDao mAsyncTaskDao;

        deleteItemAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItem... params) {
            mAsyncTaskDao.deleteCartItem(params[0]);
            return null;
        }
    }
    private static class deleteCartAsyncTask extends AsyncTask<Cart, Void, Void> {

        private UserDao mAsyncTaskDao;

        deleteCartAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cart... params) {
            mAsyncTaskDao.deleteCart(params[0]);
            return null;
        }
    }

    private static class getNonLiveCartItems extends AsyncTask<Long, Void, CartWithCartItems> {

        private UserDao mAsyncTaskDao;

        getNonLiveCartItems(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected CartWithCartItems doInBackground(final Long... params) {
            return mAsyncTaskDao.getNonLiveCartItems(params[0]);
        }
    }

    private static class getCartByName extends AsyncTask<String, Void, Cart> {

        private UserDao mAsyncTaskDao;

        getCartByName(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Cart doInBackground(final String... params) {
            return mAsyncTaskDao.getCartByName(params[0], params[1]);
        }
    }

}