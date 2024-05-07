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
    public static User currentUser;
    public static int currentUserIndex;
    public static List<Recipe> generatedRecipes = new ArrayList<>();
    public static List<Chat> allChats = new ArrayList<Chat>();
    public static List<Grocery> allGroceryList = new ArrayList<>();
    public static boolean displayChatValue = false;
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
/*        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
        NavigationUI.setupWithNavController(binding.navView, navController);

        database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "userDB")
                .fallbackToDestructiveMigration()
                .build();

        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);

        String groceryText = "Sure, here is the grocery list for the Momo recipe with a total cost of less than $50:\n" +
                "                                                                                                    \n" +
                "                                                                                                    * Rice flour (1 cup) - $1.50\n" +
                "                                                                                                    * Water (1/2 cup) - $0.00 (since it's a free resource)\n" +
                "                                                                                                    * Vegetable oil (1/4 cup) - $2.00\n" +
                "                                                                                                    * Onion (1/4 cup, chopped) - $0.75\n" +
                "                                                                                                    * Cabbage (1/4 cup, chopped) - $0.75\n" +
                "                                                                                                    * Carrots (1/4 cup, chopped) - $0.75\n" +
                "                                                                                                    * Spinach (1/4 cup, chopped) - $0.75\n" +
                "                                                                                                    * Herbs (1/4 cup, chopped) - $0.75\n" +
                "                                                                                                    * Salt - $0.25\n" +
                "                                                                                                    * Pepper - $0.25\n" +
                "                                                                                                    \n" +
                "                                                                                                    Total cost: $50.00\n" +
                "                                                                                                    \n" +
                "                                                                                                    Note: The cost of the ingredients may vary depending on the location and availability of the items. These prices are just an estimate.";
        Pattern pattern = Pattern.compile("\\$[0-9]+\\.?[0-9]*");
        Matcher matcher = pattern.matcher(groceryText);

        String cost = "0";
        while (matcher.find()) {
            cost = matcher.group(); // Extract the cost substring
            System.out.println("Cost: " + cost);
        }

        String numericCostString = cost.substring(1); // Remove the dollar sign
        Double groceryCost = Double.valueOf(numericCostString);
        Grocery newGrocery = new Grocery(groceryText, groceryCost);

        allGroceryList.add(newGrocery);

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