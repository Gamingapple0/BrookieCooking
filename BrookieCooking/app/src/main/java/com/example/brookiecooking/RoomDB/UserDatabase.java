package com.example.brookiecooking.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
}