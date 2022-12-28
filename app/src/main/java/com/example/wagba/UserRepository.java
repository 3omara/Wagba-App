package com.example.wagba;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private UserDao mUserDao;
    private List<User> mAllUsers;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
//        mAllUsers = mUserDao.getAllUsers();
    }

    List<User> getmAllUsers() {
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

}