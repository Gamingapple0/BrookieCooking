package com.example.brookiecooking;

import static com.example.brookiecooking.Adapter.MyAllergiesViewAdapter.chosenAlergies;
import static com.example.brookiecooking.MainActivity.allAllergies;
import static com.example.brookiecooking.MainActivity.allChats;
import static com.example.brookiecooking.MainActivity.currentUser;
import static com.example.brookiecooking.MainActivity.navController;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.brookiecooking.Adapter.MyChatViewAdapter;
import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.RoomDB.User;
import com.example.brookiecooking.databinding.FragmentCategoryBinding;

import org.json.JSONException;
import org.json.JSONObject;
import static com.example.brookiecooking.MainActivity.database;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class category extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView chatRecyclerView;
    boolean connected = false;
    List<Recipe> dataFinal = new ArrayList<>();
    ImageView back;
    TextView tittle;
    String dish;
    Double budget = 0.0;

    private MyChatViewAdapter adapter;

    private FragmentCategoryBinding binding;
    private String newRecipeImg = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MainActivity mainActivity;

    public category() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment category.
     */
    // TODO: Rename and change types and number of parameters
    public static category newInstance(String param1, String param2) {
        category fragment = new category();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MainActivity");
        }
    }

    public static String parseIngredientsResponse(JSONObject response) throws JSONException {
        return response.getString("ingredients");
    }

    public static String parseInstructionsResponse(JSONObject response) throws JSONException {
        return response.getString("instructions");
    }

    private String parseGroceriesResponse(JSONObject response) throws JSONException {
        return response.getString("groceries");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        allChats = new ArrayList<>();
        mainActivity.setNavViewVisibility(false);
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        adapter = new MyChatViewAdapter();
        binding.chatRecyclerView.setAdapter(adapter);

        String videoPath = "android.resource://" + mainActivity.getPackageName() + "/" + R.raw.download;
        Uri uri = Uri.parse(videoPath);

        binding.videoView.setVideoURI(uri);

        binding.videoView.setOnCompletionListener(mp -> binding.videoView.start());

        // Start playing the video
        binding.videoView.start();

        // Find views
        back = binding.imageView2;
        tittle = binding.tittle;
        chatRecyclerView = binding.chatRecyclerView;

        Chat newChat;
        assert getArguments() != null;
        if (Objects.equals(getArguments().getString("title"), "Nepal")){
            newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(9).png";
            newChat = new Chat("Everest your taste buds for a culinary adventure? Let's explore the delicious depths of Nepali cuisine! What's your budget Brokie?",true, getArguments().getString("title"));
        }
        else if (Objects.equals(getArguments().getString("title"), "China")){
            newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(10).png";
            newChat = new Chat("The Great Wall of flavor awaits! Let's build a delicious meal together, brick by delicious brick! What's your budget Brokie?",true, getArguments().getString("title"));
        }
        else if (Objects.equals(getArguments().getString("title"), "India")){
            newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(11).png";
            newChat = new Chat("Feeling naan-stop hungry? We can whip up a feast that's tandoori-fic! What's your budget Brokie?",true, getArguments().getString("title"));
        }
        else{
            newRecipeImg = "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(12).png";
            newChat = new Chat("Mama mia, looka here Brookie! It's-a me, your friendly AI sous chef! Let's-a cook up a- somethin' spicy or a- somethin' cheesy Wahooo! What's your budget Brokie?",true, getArguments().getString("title"));
        }


        allChats.add(newChat);

        // Set layout manager to recyclerView
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat newChat = new Chat(binding.chatInputBox.getText().toString(),false, getArguments().getString("title"));
                allChats.add(newChat);
                adapter.notifyItemInserted(allChats.size()-1);

                if(allChats.size() > 3){
                    // Encode question and answer parameters
                    binding.videoLayout.setVisibility(View.VISIBLE);

                    String encodedParams = null;
                    try {
                    encodedParams = "cuisine=" + URLEncoder.encode(tittle.getText().toString(), "UTF-8") +
                            "&budget=" + URLEncoder.encode(String.valueOf(budget), "UTF-8") +
                            "&dish=" + URLEncoder.encode(binding.chatInputBox.getText().toString(), "UTF-8") +
                            "&allergies=" + URLEncoder.encode(allAllergies.toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    // Construct the full URL with encoded parameters
                    String baseUrl = "http://10.0.2.2:5000/getRecipe?";
                    String fullUrl = baseUrl + encodedParams;
                    Log.i("AshUrl",fullUrl);

                  JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Bundle bundle = new Bundle();
                                bundle.putString("img", newRecipeImg);
                                bundle.putString("tittle", binding.chatInputBox.getText().toString());
                                bundle.putString("budget", String.valueOf(budget));
                                bundle.putString("des", parseInstructionsResponse(response));
                                bundle.putString("ing", parseIngredientsResponse(response));

                                Recipe newRecipe = new Recipe(newRecipeImg, binding.chatInputBox.getText().toString(),parseInstructionsResponse(response), parseIngredientsResponse(response), getArguments().getString("title"),budget);
                                currentUser.addRecipe(newRecipe);

                                Log.i("AshUserCur", String.valueOf(currentUser));

                                new AsyncTask<Void, Void, Void>() {
                                    @SuppressLint("StaticFieldLeak")
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        // Perform database insertion
                                        database.userDao().updateUser(currentUser);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        // Navigate to the next screen after the database operation is complete
                                        binding.videoLayout.setVisibility(View.VISIBLE);
                                        navController.navigate(R.id.recipe, bundle);
                                    }
                                }.execute();


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
                            250000, // Timeout in milliseconds (2 minutes)
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    Volley.newRequestQueue(getContext()).add(request);


                    adapter.notifyItemInserted(allChats.size()-1);

                }
                else{
                    budget = Double.valueOf(binding.chatInputBox.getText().toString());
                    Chat newChat1 = new Chat("Understood! What dish would you like the recipe of?",true, getArguments().getString("title"));
                    allChats.add(newChat1);
                    adapter.notifyItemInserted(allChats.size()-1);
                }


            }
        });

        // Set recipe category title
        assert getArguments() != null;
        tittle.setText(getArguments().getString("title"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setNavViewVisibility(true);
                if (!navController.popBackStack()) {
                    requireActivity().onBackPressed();
                }
            }
        });

        return binding.getRoot();
    }
}