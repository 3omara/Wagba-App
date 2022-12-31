package com.example.wagba.data.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wagba.data.room.repository.CartItem;
import com.example.wagba.data.room.entities.User;
import com.example.wagba.data.room.dao.UserDao;
import com.example.wagba.data.room.entities.Cart;

@Database(entities = {User.class, Cart.class, CartItem.class}, version = 5, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static UserRoomDatabase INSTANCE;

    static public UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserRoomDatabase.class, "user_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
