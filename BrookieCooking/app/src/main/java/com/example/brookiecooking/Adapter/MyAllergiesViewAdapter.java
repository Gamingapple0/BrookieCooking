package com.example.brookiecooking.Adapter;

import static com.example.brookiecooking.MainActivity.allAllergies;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brookiecooking.R;
import com.example.brookiecooking.databinding.AllergyBinding;

import java.util.ArrayList;
import java.util.List;

public class MyAllergiesViewAdapter extends RecyclerView.Adapter<MyAllergiesViewAdapter.ViewHolder> {

    private Clickable clickListener;
    private int selectedItem = -1; // Initially no item is selected
    public static List<String> chosenAlergies = new ArrayList<>();



    public MyAllergiesViewAdapter(List<String> allAllergiesList, Clickable listener) {
        allAllergies = allAllergiesList;
        this.clickListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AllergyBinding binding = AllergyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String selAlergy = allAllergies.get(position);
        if (selAlergy != null) {
            holder.bindCard(selAlergy);
        } else {
            holder.bindCard("selAlergy");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the clicked item's position to the listener
                selectedItem = holder.getAdapterPosition();
                // Notify adapter that the data set has changed to refresh the RecyclerView
                notifyDataSetChanged();
                clickListener.onClick(selAlergy);
                Log.i("Ash3",holder.itemView.findViewById(R.id.editInterest).toString());
                holder.itemView.setBackgroundColor(Color.GREEN);

            }

        });

        // Change background color based on the selected item
        if (position == selectedItem) {
            Log.i("Ash3",holder.itemView.toString());
            holder.itemView.setBackgroundColor(Color.GREEN); // Set selected item background color to green
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Set other item background color to transparent
        }

    }

    @Override
    public int getItemCount() {
        return allAllergies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AllergyBinding binding;
        private Clickable clickListener;

        public ViewHolder(@NonNull AllergyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void bindCard(String allergy) {

//            if (background instanceof GradientDrawable) {
//                binding.editInterest.setBackgroundResource(R.drawable.ic_launcher_background);
//            } else {
//                binding.editInterest.setBackgroundResource(R.drawable.gradient_with_stroke);
//            }

//            binding.editInterest.setBackgroundResource(R.drawable.ic_launcher_background);

            binding.editInterest.setText(allergy);

            binding.editInterest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable background = binding.editInterest.getBackground();
                    Log.i("Ash4",chosenAlergies.toString());
                    if (background instanceof GradientDrawable) {
                        if (chosenAlergies.size() > 2){
                            Toast.makeText(itemView.getContext(), "Please Select Only Up to 10", Toast.LENGTH_LONG).show();
                            return;
                        }
                        binding.editInterest.setBackgroundResource(R.drawable.lime_selected);
                        chosenAlergies.add(allergy);
                        int whiteColor = ContextCompat.getColor(itemView.getContext(), R.color.white);
                        binding.editInterest.setTextColor(whiteColor);
                    } else {
                        binding.editInterest.setBackgroundResource(R.drawable.gradient);
                        chosenAlergies.remove(allergy);
                        int darkColor = ContextCompat.getColor(itemView.getContext(), R.color.dark);
                        binding.editInterest.setTextColor(darkColor);
                    }
                }
            });
        }
    }
}
