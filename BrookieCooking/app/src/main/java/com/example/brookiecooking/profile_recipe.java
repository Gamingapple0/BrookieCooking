package com.example.brookiecooking;

import static com.example.brookiecooking.MainActivity.currentUser;
import static com.example.brookiecooking.MainActivity.navController;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brookiecooking.Adapter.Adaptar;
import com.example.brookiecooking.Adapter.MyChatViewAdapter;
import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.databinding.FragmentProfileRecipeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile_recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_recipe extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentProfileRecipeBinding binding;
    private List<Recipe> currentList = new ArrayList<>();
    private Adaptar adapter;
    MainActivity mainActivity;
    String [] ingList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MainActivity");
        }
    }
    public profile_recipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile_recipe.
     */
    // TODO: Rename and change types and number of parameters
    public static profile_recipe newInstance(String param1, String param2) {
        profile_recipe fragment = new profile_recipe();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileRecipeBinding.inflate(inflater, container, false);

        String cuisine = getArguments().getString("Category");
        switch (Objects.requireNonNull(cuisine)) {
            case "Nepal":
                currentList = currentUser.getAllRecipes()
                        .stream()
                        .filter(x -> "Nepal".equals(x.getCategory()))
                        .collect(Collectors.toList());
                break;
            case "China":
                currentList = currentUser.getAllRecipes()
                        .stream()
                        .filter(x -> "China".equals(x.getCategory()))
                        .collect(Collectors.toList());
                break;
            case "India":
                currentList = currentUser.getAllRecipes()
                        .stream()
                        .filter(x -> "India".equals(x.getCategory()))
                        .collect(Collectors.toList());
                break;
            case "Italy":
                currentList = currentUser.getAllRecipes()
                        .stream()
                        .filter(x -> "Italy".equals(x.getCategory()))
                        .collect(Collectors.toList());
                break;
            default:
                currentList = new ArrayList<>();
        }

        Log.i("AshCurrentList", currentList.toString());
        binding.recview.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new Adaptar(currentList,requireContext());
        binding.recview.setAdapter(adapter);

        binding.tittle.setText(cuisine);

        binding.back.setOnClickListener(new View.OnClickListener() {
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