package com.example.brookiecooking.RoomDB;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @NonNull
    public String img;

    @NonNull
    public String tittle;

    @NonNull
    public String des;

    @NonNull
    public String ing;

    @NonNull
    public String category;

    public Recipe(String img, String tittle, String des, String ing, String category) {
        this.img = img;
        this.tittle = tittle;
        this.des = des;
        this.ing = ing;
        this.category = category;
    }

    protected Recipe(Parcel in) {
        uid = in.readInt();
        img = in.readString();
        tittle = in.readString();
        des = in.readString();
        ing = in.readString();
        category = in.readString();
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(img);
        dest.writeString(tittle);
        dest.writeString(des);
        dest.writeString(ing);
        dest.writeString(category);
    }

}

