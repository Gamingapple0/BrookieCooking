package com.example.brookiecooking;

import static com.example.brookiecooking.Adapter.MyAllergiesViewAdapter.chosenAlergies;

import static com.example.brookiecooking.MainActivity.database;
import static com.example.brookiecooking.MainActivity.allAllergies;
import static com.example.brookiecooking.MainActivity.navController;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brookiecooking.Adapter.Clickable;
import com.example.brookiecooking.Adapter.MyAllergiesViewAdapter;
import com.example.brookiecooking.RoomDB.Recipe;
import com.example.brookiecooking.RoomDB.User;
import com.example.brookiecooking.databinding.FragmentAllergiesBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allergies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allergies extends Fragment implements Clickable {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentAllergiesBinding binding;
    private RecyclerView recyclerView;

    String username = "";
    String email = "";
    String confirmEmail = "";
    String password = "";
    String confirmPassword = "";
    String phone = "";

    public allergies() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allergies.
     */
    // TODO: Rename and change types and number of parameters
    public static allergies newInstance(String param1, String param2) {
        allergies fragment = new allergies();
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

        binding = FragmentAllergiesBinding.inflate(inflater,container,false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            email = bundle.getString("email");
            confirmEmail = bundle.getString("confirmEmail");
            password = bundle.getString("password");
            confirmPassword = bundle.getString("confirmPassword");
            phone = bundle.getString("phone");
            // Do something with the data

            Log.i("AshI", "Username: " + username);
            Log.i("AshI", "Email: " + email);
            Log.i("AshI", "Confirm Email: " + confirmEmail);
            Log.i("AshI", "Password: " + password);
            Log.i("AshI", "Confirm Password: " + confirmPassword);
            Log.i("AshI", "Phone: " + phone);
        }

        recyclerView = binding.getRoot().findViewById(R.id.allAlergiesView);
        recyclerView.setAdapter(new MyAllergiesViewAdapter(allAllergies, this));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // Perform database insertion
                        User newUser = new User(username, password, phone, email, chosenAlergies, "Basic", new ArrayList<Recipe>());
                        database.userDao().insertUser(newUser);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        // Navigate to the next screen after the database operation is complete
                        Navigation.findNavController(v).navigate(R.id.action_allergies_to_login);
                    }
                }.execute();
            }
        });

        binding.backBtn1.setOnClickListener(new View.OnClickListener() {
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