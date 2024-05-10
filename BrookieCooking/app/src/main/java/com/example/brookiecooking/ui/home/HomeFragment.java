package com.example.brookiecooking.ui.home;

import static com.example.brookiecooking.MainActivity.allAllergies;
import static com.example.brookiecooking.MainActivity.currentUser;
import static com.example.brookiecooking.MainActivity.currentUserIndex;
import static com.example.brookiecooking.MainActivity.database;
import static com.example.brookiecooking.MainActivity.navController;
import static com.example.brookiecooking.MainActivity.recRecipes;
import static com.example.brookiecooking.category.parseIngredientsResponse;
import static com.example.brookiecooking.category.parseInstructionsResponse;

import static java.lang.Math.random;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.brookiecooking.Adapter.AdapterPopular;
import com.example.brookiecooking.Chat;
import com.example.brookiecooking.R;
import com.example.brookiecooking.RoomDB.AppDatabase;
import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.RoomDB.RecipeDao;
import com.example.brookiecooking.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ImageView salad, main, drinks, dessert, menu;
    RecyclerView rcview_home;
    List<Recipe> dataPopular = new ArrayList<>();
    LottieAnimationView lottie;
    String newDishTitle;
    String newRecipeImg;
    String cus;
    Double budg;
    EditText editText;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        salad = binding.nepal;
        main = binding.china;
        drinks = binding.india;
        dessert = binding.italy;
        rcview_home = binding.rcviewPopular;
        lottie = binding.lottie;
        lottie.setRepeatCount(LottieDrawable.INFINITE);
        editText = binding.editText;
        menu = binding.profileButton;

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

//        // Get database
//        AppDatabase db = Room.databaseBuilder(getContext(),
//                        AppDatabase.class, "db_name6").allowMainThreadQueries()
//                /*.createFromAsset("database/recipe.db")*/
//                .fallbackToDestructiveMigration()
//                .build();
//        RecipeDao recipeDao = db.userDao();

        if (recRecipes.size() < 1){
            // Get random cuisine from Recipes
            Random rand = new Random();
            Recipe randRec = currentUser.getAllRecipes().get(rand.nextInt(currentUser.getAllRecipes().size()));
            cus = randRec.category;

            if (Objects.equals(cus, "Nepal")){
                newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(9).png";
            }
            else if (Objects.equals(cus, "China")){
                newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(10).png";
            }
            else if (Objects.equals(cus, "India")){
                newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(11).png";
            }
            else{
                newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(12).png";
            }


            budg = randRec.budget;
            StringBuilder encodedRecipes = new StringBuilder();
            for (Recipe recp : currentUser.getAllRecipes()) {
                // Append each ingredient to the StringBuilder with proper encoding
                try {
                    encodedRecipes.append(URLEncoder.encode(recp.getTittle(), "UTF-8")).append(",");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

            // Remove the trailing comma
            if (encodedRecipes.length() > 0) {
                encodedRecipes.deleteCharAt(encodedRecipes.length() - 1);
            }

            String encodedParams = null;
            try {
                encodedParams = "cuisine=" + URLEncoder.encode(cus, "UTF-8") +
                        "&dishes=" + URLEncoder.encode(String.valueOf(encodedRecipes), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Construct the full URL with encoded parameters
            String baseUrl = "http://10.0.2.2:5000/getRecs?";
            String fullUrl = baseUrl + encodedParams;
            Log.i("AshUrl",fullUrl);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        newDishTitle = response.getString("recommendation");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String encodedParams1 = null;
                    try {
                        encodedParams1 = "cuisine=" + URLEncoder.encode(cus, "UTF-8") +
                                "&budget=" + URLEncoder.encode(String.valueOf(budg), "UTF-8") +
                                "&dish=" + URLEncoder.encode(newDishTitle, "UTF-8") +
                                "&allergies=" + URLEncoder.encode(allAllergies.toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    // Construct the full URL with encoded parameters
                    String baseUrl = "http://10.0.2.2:5000/getRecipe?";
                    String fullUrl = baseUrl + encodedParams1;
                    Log.i("AshUrl",fullUrl);

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
//                                Chat newBotChat = new Chat(parseIngredientsResponse(response),true);
//
//                                allChats.add(newBotChat);
//                                adapter.notifyItemInserted(allChats.size()-1);

                                Recipe newRecipe = new Recipe(newRecipeImg, newDishTitle,parseInstructionsResponse(response), parseIngredientsResponse(response), newDishTitle,budg);



                                recRecipes.add(newRecipe);
                                if (currentUser.getAllRecipes().size() > 0){
                                    recRecipes.add(currentUser.getAllRecipes().get(0));
                                }

                                AdapterPopular adapter = new AdapterPopular(recRecipes, getContext());
                                rcview_home.setAdapter(adapter);

                                Log.i("AshUserCur", String.valueOf(newRecipe.ing));
                                Log.i("AshUserCur", String.valueOf(newRecipe.tittle));
                                lottie.setVisibility(View.GONE);


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Log.i("AshRes", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("AshRes", String.valueOf(error));
                        }
                    });

                    request.setRetryPolicy(new DefaultRetryPolicy(
                            220000, // Timeout in milliseconds (2 minutes)
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    Volley.newRequestQueue(getContext()).add(request);

//                    Bundle bundle = new Bundle();
//                    bundle.putString("img", newRecipeImg);
//                    bundle.putString("tittle", binding.chatInputBox.getText().toString());
//                    bundle.putString("budget", String.valueOf(budget));
//                    bundle.putString("des", parseInstructionsResponse(response));
//                    bundle.putString("ing", parseIngredientsResponse(response));
//
//                    Recipe newRecipe = new Recipe(newRecipeImg, binding.chatInputBox.getText().toString(),parseInstructionsResponse(response), parseIngredientsResponse(response), getArguments().getString("title"),budget);
//                    currentUser.addRecipe(newRecipe);

                    Log.i("AshUserCur", String.valueOf(response));



                    Log.i("AshRes", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("AshRes", String.valueOf(error));
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(
                    220000, // Timeout in milliseconds (2 minutes)
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            Volley.newRequestQueue(getContext()).add(request);



            // Hide progress animation

        }


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

        navController.navigate(R.id.action_navigation_home_to_profile);
/*        Dialog dialog = new Dialog(this);
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