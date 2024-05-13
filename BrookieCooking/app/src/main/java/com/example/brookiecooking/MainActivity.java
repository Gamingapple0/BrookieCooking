package com.example.brookiecooking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.RoomDB.User;
import com.example.brookiecooking.RoomDB.UserDatabase;
import com.example.brookiecooking.RoomDB.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.brookiecooking.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static NavController navController;
    public static UserDatabase database;
    private List<User> users = new ArrayList<>();
    private UserViewModel userViewModel;
    public static List<Recipe> recRecipes = new ArrayList<>();
    public static User currentUser;
    public static List<Chat> allChats = new ArrayList<Chat>();
    public static List<Grocery> allGroceryList = new ArrayList<>();
    public static List<String> allAllergies = new ArrayList<String>() {{
        add("Peanut");
        add("Gluten");
        add("Eggs");
        add("Dairy");
        add("Soy");
    }};

    public void setNavViewVisibility(boolean visible) {
        if (visible) {
            binding.navView.setVisibility(View.VISIBLE);
        } else {
            binding.navView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /*BottomNavigationView navView = findViewById(R.id.nav_view);*/
        binding.navView.setVisibility(View.GONE);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "userDB")
                .fallbackToDestructiveMigration()
                .build();

        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                users = userList;
                Log.d("Ash", users.toString());
            }
        });



    }

/*    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }*/

}