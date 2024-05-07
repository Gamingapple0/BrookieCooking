package com.example.brookiecooking.RoomDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executors;

public class UserViewModel extends AndroidViewModel {

    private UserDatabase database;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, UserDatabase.class, "userDB")
                .fallbackToDestructiveMigration()
                .build();
        users = database.userDao().getUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    // Method to update a single user
    public void updateUser(User updatedUser) {
        // Perform the update operation asynchronously
        Executors.newSingleThreadExecutor().execute(() -> {
            database.userDao().updateUser(updatedUser); // Update the user in the database
        });
    }
}