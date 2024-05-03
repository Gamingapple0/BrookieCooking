package com.example.brookiecooking.RoomDB;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    List<Recipe> getAll();

}

