package com.example.brookiecooking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.core.content.ContextCompat.getColor;
import static com.example.brookiecooking.MainActivity.allGroceryList;
import static com.example.brookiecooking.MainActivity.navController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recipe extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView img, backBtn, overlay, scroll, back2;
    private TextView txt, ing, time, steps;
    private String[] ingList;
    private MainActivity mainActivity;
    private String[] stepsList;
    private Button stepBtn, ingBtn;
    private boolean isImgCrop = false;
    private ScrollView scrollView, scrollViewStep;
    private FragmentRecipeBinding binding;

    public recipe() {
        // Required empty public constructor
    }

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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
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

    private String parseGroceryResponse(JSONObject response) throws JSONException {
        return response.getString("groceries");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecipeBinding.inflate(inflater, container, false);

        img = binding.recipeImg;
        txt = binding.tittle;
        ing = binding.ing;
        time = binding.time;
        stepBtn = binding.stepsBtn;
        ingBtn = binding.ingBtn;
        backBtn = binding.backBtn;
        steps = binding.stepsTxt;
        scrollView = binding.ingScroll;
        scrollViewStep = binding.steps;
        overlay = binding.imageGradient;
        scroll = binding.scroll;

        if (getArguments() != null) {
            String im = getArguments().getString("img");

            if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(11).png")) {
                img.setImageResource(R.drawable.india_recipe);
            }

            if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(10).png")) {
                img.setImageResource(R.drawable.china_recipe);
            }

            if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(12).png")) {
                img.setImageResource(R.drawable.italy_recipe);
            }

            if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(9).png")) {
                img.setImageResource(R.drawable.nepal_recipe);
            }

            txt.setText(getArguments().getString("tittle"));
            String a = getArguments().getString("tittle");

            ingList = getArguments().getString("ing").split("\n");
            stepsList = getArguments().getString("des").split("\n");

            time.setText(ingList[1]);

            List<String> ingredientList = new ArrayList<>();

            for (int i = 2; i < ingList.length; i++) {
                ing.setText(ing.getText() + "\uD83D\uDFE2  " + ingList[i] + "\n");
                ingredientList.add(ingList[i].split("\\(")[0]);
            }

            List<String> filteredInstructionList = new ArrayList<>();
            for (String instruction : stepsList) {
                if (!instruction.isEmpty()) {
                    steps.setText(steps.getText() + "\uD83D\uDFE2  " + instruction + "\n");
                }
            }

            List<String> ingredientsExceptLast = ingredientList.subList(0, ingredientList.size() - 1);

            binding.addToGroceries.setOnClickListener(v -> addToGroceries(ingredientsExceptLast));
            setupButtonListeners();
        }
        return binding.getRoot();
    }

    private void addToGroceries(List<String> ingredients) {
        String baseUrl = "http://10.0.2.2:5000/getPrices?";
        String encodedParams = encodeParams(ingredients);
        String fullUrl = baseUrl + encodedParams;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
                response -> {
                    try {
                        String groceryText = parseGroceryResponse(response);
                        extractGroceryCost(groceryText);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Log.i("AshRes", String.valueOf(error)));

        request.setRetryPolicy(new DefaultRetryPolicy(
                120000, // Timeout in milliseconds (2 minutes)
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(request);
    }

    private String encodeParams(List<String> ingredients) {
        StringBuilder encodedIngredients = new StringBuilder();
        try {
            for (String ingredient : ingredients) {
                encodedIngredients.append(URLEncoder.encode(ingredient, "UTF-8")).append(",");
            }
            if (encodedIngredients.length() > 0) {
                encodedIngredients.deleteCharAt(encodedIngredients.length() - 1);
            }
            return "&budget=" + URLEncoder.encode(getArguments().getString("budget"), "UTF-8") +
                    "&dish=" + URLEncoder.encode(getArguments().getString("tittle"), "UTF-8") +
                    "&ingredients=" + URLEncoder.encode(encodedIngredients.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractGroceryCost(String groceryText) {
        Pattern pattern = Pattern.compile("\\$[0-9]+\\.?[0-9]*");
        Matcher matcher = pattern.matcher(groceryText);

        String cost = "0";
        while (matcher.find()) {
            cost = matcher.group();
            Log.i("AshRes", "Cost: " + cost);
        }

        String numericCostString = cost.substring(1);
        Double groceryCost = Double.valueOf(numericCostString);

        Grocery newGrocery = new Grocery(groceryText, groceryCost);
        allGroceryList.add(newGrocery);
    }

    private void setupButtonListeners() {
        stepBtn.setBackground(null);
        stepBtn.setOnClickListener(v -> {
            stepBtn.setBackgroundResource(R.drawable.btn_ing);
            stepBtn.setTextColor(getColor(requireContext(), R.color.white));
            ingBtn.setBackground(null);
            stepBtn.setTextColor(getColor(requireContext(), R.color.white));
            ingBtn.setTextColor(getColor(requireContext(), R.color.black));

            scrollView.setVisibility(View.GONE);
            scrollViewStep.setVisibility(View.VISIBLE);
        });

        ingBtn.setOnClickListener(v -> {
            ingBtn.setBackgroundResource(R.drawable.btn_ing);
            ingBtn.setTextColor(getColor(requireContext(), R.color.white));
            stepBtn.setBackground(null);
            stepBtn.setTextColor(getColor(requireContext(), R.color.black));

            scrollView.setVisibility(View.VISIBLE);
            scrollViewStep.setVisibility(View.GONE);
        });

        backBtn.setOnClickListener(v -> {
            if (!navController.popBackStack()) {
                requireActivity().onBackPressed();
            }
            if (!navController.popBackStack()) {
                requireActivity().onBackPressed();
            }
            mainActivity.setNavViewVisibility(true);
        });
    }
}
