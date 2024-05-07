package com.example.brookiecooking;

import static com.example.brookiecooking.MainActivity.navController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brookiecooking.databinding.FragmentSignupBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentSignupBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signup() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
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

        binding = FragmentSignupBinding.inflate(inflater, container, false);

        binding.allergiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", binding.editUsername.getText().toString());
                bundle.putString("email", binding.editEmail.getText().toString());
                bundle.putString("password", binding.editPassword.getText().toString());
                bundle.putString("confirmPassword", binding.editConfirmPassword.getText().toString());
                bundle.putString("phone", binding.editPhone.getText().toString());

                // Navigate to the next fragment with the bundle
                Navigation.findNavController(v).navigate(R.id.action_signup_to_allergies, bundle);
            }
        });

        binding.backBtn2.setOnClickListener(new View.OnClickListener() {
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