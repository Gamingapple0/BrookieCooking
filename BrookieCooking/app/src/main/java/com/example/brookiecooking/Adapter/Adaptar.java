package com.example.brookiecooking.Adapter;

import static android.text.TextUtils.split;

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
/*import com.tiodev.vegtummy.R;
import com.tiodev.vegtummy.RecipeActivity;*/
import com.example.brookiecooking.R;
import com.example.brookiecooking.RoomDB.Recipe;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Adaptar extends  RecyclerView.Adapter<Adaptar.myviewHolder>{

//    List<ResModel> data;
//    Context context;

    List<Recipe> data;
    Context context;
    String [] ingList;

//    ArrayList<String> data = new ArrayList<>();
//
//    public Adaptar(ArrayList<String> data) {
//        this.data = data;
//    }

    public Adaptar(List<Recipe> data , Context context) {

        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_design,parent,false);
         return new myviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {

        final Recipe temp = data.get(position);

        holder.txt1.setText(data.get(position).getTittle());
        String im = data.get(position).getImg();



//        Glide.with(holder.txt1.getContext()).load(im).into(holder.img);

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

        ingList = temp.getIng().split("\n");

//            steps.setText(getArguments().getString("des"));
        List<String> filteredList = new ArrayList<>();
        for (String ingredient : ingList) {
            if (!ingredient.isEmpty() && ingredient != null) {
                filteredList.add(ingredient);
            }
        }





        holder.time.setText(filteredList.get(1));


        holder.img2.setOnClickListener(v -> {

            // Navigate to the RecipeFragment
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.recipe);

            // Pass data to the RecipeFragment
            Bundle bundle = new Bundle();
            bundle.putString("img", temp.getImg());
            bundle.putString("tittle", temp.getTittle());
            bundle.putString("des", temp.getDes());
            bundle.putString("ing", temp.getIng());
            navController.navigate(R.id.recipe, bundle);


        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class myviewHolder extends RecyclerView.ViewHolder{
        ImageView img , img2;
        TextView txt1, time;


        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCountry);
            img2 = itemView.findViewById(R.id.card_btn);
            txt1 = itemView.findViewById(R.id.txt1);
            time = itemView.findViewById(R.id.time);
        }
    }



}
