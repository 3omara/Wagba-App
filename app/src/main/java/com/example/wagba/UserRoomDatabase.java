package com.example.wagba;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Cart.class, CartItem.class}, version = 5, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static UserRoomDatabase INSTANCE;

    static UserRoomDatabase getDatabase(final Context context) {
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
