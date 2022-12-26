package com.example.wagba;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private List<User> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getmAllUsers();
    }

//    List<User> getAllUsers() { return mAllUsers; }

    public void insert(User user) { mRepository.insert(user); }
}
