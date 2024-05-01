package com.example.brookiecooking;

import static androidx.core.content.ContextCompat.getColor;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.brookiecooking.databinding.FragmentRecipeBinding;

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
    ImageView img, backBtn, overlay, scroll, zoomImage;
    TextView txt, ing, time, steps;
    String [] ingList;
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
        steps = binding.stepsBtn;
        scrollView = binding.ingScroll;
        scrollView_step = binding.steps;
        overlay = binding.imageGradient;
        scroll = binding.scroll;

        Glide.with(requireContext()).load(getArguments().getString("img")).into(img);
        // Set recipe title
        txt.setText(getArguments().getString("tittle"));

        // Set recipe ingredients
        ingList = getArguments().getString("ing").split("\n");
        // Set time
        time.setText(ingList[0]);

        for (int i = 1; i<ingList.length; i++){
            ing.setText(ing.getText()+"\uD83D\uDFE2  "+ingList[i]+"\n");
            /*if(ingList[i].startsWith(" ")){
                ing.setText(ing.getText()+"\uD83D\uDFE2  "+ingList[i].trim().replaceAll("\\s{2,}", " ")+"\n");
            }else{

            }*/

        }

        steps.setText(getArguments().getString("des"));
        // steps.setText(Html.fromHtml(getIntent().getStringExtra("des")));

        stepBtn.setBackground(null);

        stepBtn.setOnClickListener(v -> {
            stepBtn.setBackgroundResource(R.drawable.btn_ing);
            stepBtn.setTextColor(getColor(requireContext(), R.color.white));
            ing_btn.setBackground(null);
            stepBtn.setTextColor(getColor(requireContext(), R.color.black));


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


        return binding.getRoot();
    }
}