package com.example.brookiecooking.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.brookiecooking.R;
/*import com.example.brookiecooking.RecipeActivity;*/
import com.example.brookiecooking.RoomDB.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterPopular extends RecyclerView.Adapter<AdapterPopular.myviewholder>{

    List<Recipe> data;
    Context context;
    String [] ingList;


    public AdapterPopular(List<Recipe> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_list,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        final Recipe temp = data.get(holder.getAdapterPosition());

        // Split the time from ingredients
//        String[] time = data.get(holder.getAdapterPosition()).getIng().split("\n");
        // Set time

        ingList = temp.getIng().split("\n");

//        holder.txt2.setText("\uD83D\uDD50 "+time[0]);

        List<String> filteredList = new ArrayList<>();
        for (String ingredient : ingList) {
            if (!ingredient.isEmpty() && ingredient != null) {
                filteredList.add(ingredient);
            }
        }


        holder.txt2.setText(filteredList.get(1));
        // Load image from link
//        Glide.with(holder.txt2.getContext()).load().into(holder.img);
        String im = data.get(holder.getAdapterPosition()).getImg();
        if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(11).png")){
            holder.img.setImageResource(R.drawable.india_recipe);
        }

        if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(10).png")){
            holder.img.setImageResource(R.drawable.china_recipe);
        }

        if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(12).png")){
            holder.img.setImageResource(R.drawable.italy_recipe);
        }

        if (Objects.equals(im, "https://raw.githubusercontent.com/Gamingapple0/assets/main/Designer%20(9).png")){
            holder.img.setImageResource(R.drawable.nepal_recipe);
        }

        // Set title
        holder.txt.setText(data.get(holder.getAdapterPosition()).getTittle());

        holder.img.setOnClickListener(v ->{
            // Navigate to the RecipeFragment
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.recipe);

            // Pass data to the RecipeFragment
            Bundle bundle = new Bundle();
            bundle.putString("img", temp.getImg());
            bundle.putString("tittle", temp.getTittle());
            bundle.putString("des", temp.getDes());
            bundle.putString("ing", temp.getIng());
            bundle.putString("budget", String.valueOf(temp.budget));
            navController.navigate(R.id.recipe, bundle);
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class myviewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txt, txt2;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.popular_img);
            txt = itemView.findViewById(R.id.popular_txt);
            txt2 = itemView.findViewById(R.id.popular_time);
        }
    }
}
