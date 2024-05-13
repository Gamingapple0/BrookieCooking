package com.example.brookiecooking.RoomDB;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value){
        if (value != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(value);
            return calendar;
        }
        return null;
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar calendar){
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public static List<String> fromString(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<Recipe> fromRecipeString(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Recipe>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRecipeList(List<Recipe> list) {
        return new Gson().toJson(list);
    }

}
