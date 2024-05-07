package com.example.brookiecooking;

import static androidx.core.content.ContextCompat.getColor;

import static com.example.brookiecooking.MainActivity.allAllergies;
import static com.example.brookiecooking.MainActivity.allGroceryList;
import static com.example.brookiecooking.MainActivity.navController;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.brookiecooking.databinding.FragmentRecipeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recipe extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView img, backBtn, overlay, scroll, back2;
    TextView txt, ing, time, steps;
    String [] ingList;
    String [] stepsList;
    Button stepBtn, ing_btn;
    boolean isImgCrop = false;
    ScrollView scrollView, scrollView_step;
    FragmentRecipeBinding binding;
    public recipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recepie.
     */
    // TODO: Rename and change types and number of parameters
    public static recipe newInstance(String param1, String param2) {
        recipe fragment = new recipe();
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

    private String parseGroceryResponse(JSONObject response) throws JSONException {
        return response.getString("groceries");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRecipeBinding.inflate(inflater,container,false);

        img = binding.recipeImg;
        txt = binding.tittle;
        ing = binding.ing;
        time = binding.time;
        stepBtn = binding.stepsBtn;
        ing_btn = binding.ingBtn;
        backBtn = binding.backBtn;
        steps = binding.stepsTxt;
        scrollView = binding.ingScroll;
        scrollView_step = binding.steps;
        overlay = binding.imageGradient;
        scroll = binding.scroll;

        if (getArguments() != null){
//            Glide.with(requireContext()).load(getArguments().getString("img")).into(img);
            // Set recipe title
            txt.setText(getArguments().getString("tittle"));
            String a = getArguments().getString("tittle");

            // Set recipe ingredients



            ingList = getArguments().getString("ing").split("\n");
            stepsList = getArguments().getString("des").split("\n");

//            steps.setText(getArguments().getString("des"));
            List<String> filteredList = new ArrayList<>();
            for (String ingredient : ingList) {
                if (!ingredient.isEmpty() && ingredient != null) {
                    filteredList.add(ingredient);
                }
            }

            List<String> filteredInstructionList = new ArrayList<>();
            for (String instruction1 : stepsList) {
                if (!instruction1.isEmpty() && instruction1 != null) {
                    filteredInstructionList.add(instruction1);
                }
            }

            Log.i("AshIng", Arrays.toString(stepsList));
            // Set time
            time.setText(filteredList.get(1));
            List<String> ingredientList = new ArrayList<>();

            for (int i = 2; i<filteredList.size(); i++) {
                ing.setText(ing.getText() + "\uD83D\uDFE2  " + filteredList.get(i) + "\n");
                ingredientList.add(filteredList.get(i).split("\\(")[0]);
                if (ingList[i].startsWith(" ")) {
                    ing.setText(ing.getText() + "\uD83D\uDFE2  " + ingList[i].trim().replaceAll("\\s{2,}", " ") + "\n");
                } else {

                }
            }

            List<String> ingredientsExceptLast = ingredientList.subList(0, ingredientList.size() - 1);
            Log.i("AshIngLis", ingredientsExceptLast.toString());

            for (int j = 1; j<filteredInstructionList.size(); j++){
                steps.setText(steps.getText()+"\uD83D\uDFE2  "+ filteredInstructionList.get(j) +"\n");
                if(filteredInstructionList.get(j).startsWith(" ")){
                    steps.setText(steps.getText()+"\uD83D\uDFE2  "+ filteredInstructionList.get(j).trim().replaceAll("\\s{2,}", " ")+"\n");
                }

            }


        binding.addToGroceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Encode question and answer parameters
                String encodedParams = null;
                StringBuilder encodedIngredients = new StringBuilder();
                for (String ingredient : ingredientsExceptLast) {
                    // Append each ingredient to the StringBuilder with proper encoding
                    try {
                        encodedIngredients.append(URLEncoder.encode(ingredient, "UTF-8")).append(",");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Remove the trailing comma
                if (encodedIngredients.length() > 0) {
                    encodedIngredients.deleteCharAt(encodedIngredients.length() - 1);
                }

                try {
                    encodedParams = "&budget=" + URLEncoder.encode(getArguments().getString("budget"), "UTF-8") +
                            "&dish=" + URLEncoder.encode(getArguments().getString("tittle"), "UTF-8") +
                            "&ingredients=" + URLEncoder.encode(encodedIngredients.toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // Construct the full URL with encoded parameters
                String baseUrl = "http://10.0.2.2:5000/getPrices?";
                String fullUrl = baseUrl + encodedParams;
                Log.i("AshUrl",fullUrl);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                                Chat newBotChat = new Chat(parseIngredientsResponse(response),true);
//
//                                allChats.add(newBotChat);
//                                adapter.notifyItemInserted(allChats.size()-1);

                            String groceryText = parseGroceryResponse(response);
                            Pattern pattern = Pattern.compile("\\$[0-9]+\\.?[0-9]*");
                            Matcher matcher = pattern.matcher(groceryText);

                            String cost = "0";
                            while (matcher.find()) {
                                cost = matcher.group(); // Extract the cost substring
                                System.out.println("Cost: " + cost);
                            }

                            Double groceryCost = Double.valueOf(cost);

                            Grocery newGrocery = new Grocery(groceryText, groceryCost);

                            allGroceryList.add(newGrocery);

                            Log.i("AshResponseGen", parseGroceryResponse(response));


//                            navController.navigate(R.id.recipe, bundle);


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
                        120000, // Timeout in milliseconds (2 minutes)
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                Volley.newRequestQueue(getContext()).add(request);
            }
        });


        // steps.setText(Html.fromHtml(getIntent().getStringExtra("des")));

        stepBtn.setBackground(null);

        stepBtn.setOnClickListener(v -> {
            stepBtn.setBackgroundResource(R.drawable.btn_ing);
            stepBtn.setTextColor(getColor(requireContext(), R.color.white));
            ing_btn.setBackground(null);
            stepBtn.setTextColor(getColor(requireContext(), R.color.white));
            ing_btn.setTextColor(getColor(requireContext(), R.color.black));


            scrollView.setVisibility(View.GONE);
            scrollView_step.setVisibility(View.VISIBLE);



//            ing.setText(getIntent().getStringExtra("des"));


        });

        ing_btn.setOnClickListener(v -> {
            ing_btn.setBackgroundResource(R.drawable.btn_ing);
            ing_btn.setTextColor(getColor(requireContext(),R.color.white));
            stepBtn.setBackground(null);
            stepBtn.setTextColor(getColor(requireContext(),R.color.black));

            scrollView.setVisibility(View.VISIBLE);
            scrollView_step.setVisibility(View.GONE);

        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!navController.popBackStack()) {
                    requireActivity().onBackPressed();
                }
                if (!navController.popBackStack()) {
                    requireActivity().onBackPressed();
                }
            }
        });



    }
        return binding.getRoot();
    }
}