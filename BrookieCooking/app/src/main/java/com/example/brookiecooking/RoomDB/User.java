package com.example.brookiecooking.RoomDB;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.brookiecooking.RoomDB.Recipe;

import java.util.List;

@Entity(tableName = "users")
public class User implements Parcelable {

    private String username;
    private String password;
    private String phone;
    private String email;
    private List<String> userAllergies;
    private String packageType;
    private List<Recipe> allRecipes;

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public User(String username, String password, String phone, String email, List<String> userAllergies, String packageType, List<Recipe> allRecipes) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.userAllergies = userAllergies;
        this.packageType = packageType;
        this.allRecipes = allRecipes;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        phone = in.readString();
        email = in.readString();
        userAllergies = in.createStringArrayList();
        packageType = in.readString();
        allRecipes = in.createTypedArrayList(Recipe.CREATOR);
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeStringList(userAllergies);
        dest.writeString(packageType);
        dest.writeTypedList(allRecipes);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getUserAllergies() {
        return userAllergies;
    }

    public void setUserAllergies(List<String> userInterests) {
        this.userAllergies = userInterests;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public void setAllRecipes(List<Recipe> allRecipes) {
        this.allRecipes = allRecipes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
