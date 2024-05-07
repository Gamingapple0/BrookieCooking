package com.example.brookiecooking.ui.home;

import static com.example.brookiecooking.MainActivity.navController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.airbnb.lottie.LottieAnimationView;
import com.example.brookiecooking.Adapter.AdapterPopular;
import com.example.brookiecooking.R;
import com.example.brookiecooking.RoomDB.AppDatabase;
import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.RoomDB.RecipeDao;
import com.example.brookiecooking.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ImageView salad, main, drinks, dessert, menu;
    RecyclerView rcview_home;
    List<Recipe> dataPopular = new ArrayList<>();
    LottieAnimationView lottie;
    EditText editText;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        salad = binding.salad;
        main = binding.MainDish;
        drinks = binding.Drinks;
        dessert = binding.Desserts;
        rcview_home = binding.rcviewPopular;
        lottie = binding.lottie;
        editText = binding.editText;
        menu = binding.imageView;

        rcview_home.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        setPopularList();

        salad.setOnClickListener(v -> start("Salad","Nepal"));
        main.setOnClickListener(v -> start("Dish", "China"));
        drinks.setOnClickListener(v -> start("Drinks", "India"));
        dessert.setOnClickListener(v -> start("Desserts", "Italy"));


        // Menu button
        menu.setOnClickListener(v -> showBottomSheet());


        return root;
    }

    public void setPopularList() {

        // Get database
        AppDatabase db = Room.databaseBuilder(getContext(),
                        AppDatabase.class, "db_name6").allowMainThreadQueries()
                /*.createFromAsset("database/recipe.db")*/
                .fallbackToDestructiveMigration()
                .build();
        RecipeDao recipeDao = db.userDao();

        // Get all recipes from database
        List<Recipe> recipes = recipeDao.getAll();

        // Filter Popular category from all recipes
        for(int i = 0; i<recipes.size(); i++){
            if(recipes.get(i).getCategory().contains("Popular")){
                dataPopular.add(recipes.get(i));
            }
        }

        // Set popular list to adapter
        AdapterPopular adapter = new AdapterPopular(dataPopular, getContext());
        rcview_home.setAdapter(adapter);

        // Hide progress animation
        lottie.setVisibility(View.GONE);

    }

    // Start MainActivity(Recipe list) with intent message

    public void start(String p, String title){
        // Assuming navController is initialized in your HomeActivity
        Bundle bundle = new Bundle();
        bundle.putString("Category", p);
        bundle.putString("title", title);
        navController.navigate(R.id.action_navigation_home_to_category, bundle);
    }



    // Create a bottom dialog for privacy policy and about
    private void showBottomSheet() {
/*
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);

        LinearLayout privayPolicy = dialog.findViewById(R.id.privacy_policy);
        LinearLayout abtDev = dialog.findViewById(R.id.about_dev);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);*/
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}