package com.example.brookiecooking;

import static com.example.brookiecooking.MainActivity.navController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.databinding.FragmentCategoryBinding;

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
    RecyclerView recview;
    boolean connected = false;
    List<Recipe> dataFinal = new ArrayList<>();
    ImageView back;
    TextView tittle;
    private FragmentCategoryBinding binding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false);

        // Find views
        back = binding.imageView2;
        tittle = binding.tittle;
        recview = binding.recview;

        // Set layout manager to recyclerView
        recview.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Set recipe category title
        tittle.setText(getArguments().getString("tittle"));

        // Get database
        /*AppDatabase db = Room.databaseBuilder(requireContext(),
                        AppDatabase.class, "db_name3").allowMainThreadQueries()
                .createFromAsset("database/recipe.db")
                .build();*/
/*        UserDao userDao = db.userDao();

        // Get all recipes from database
        List<User> recipes = userDao.getAll();

        // Filter category from recipes
        for(int i = 0; i<recipes.size(); i++){
            if(recipes.get(i).getCategory().contains(getArguments().getString("Category"))){
                dataFinal.add(recipes.get(i));
            }
        }

        // Set category list to adapter
        Adaptar adapter = new Adaptar(dataFinal, requireContext());
        recview.setAdapter(adapter)*/;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!navController.popBackStack()) {
                    requireActivity().onBackPressed();
                }
            }
        });

        return binding.getRoot();
    }
}