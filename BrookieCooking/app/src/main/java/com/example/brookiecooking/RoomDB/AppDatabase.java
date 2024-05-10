package com.example.brookiecooking.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class}, exportSchema = false, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao userDao();
}
