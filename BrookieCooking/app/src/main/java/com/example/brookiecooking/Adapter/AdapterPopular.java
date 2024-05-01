package com.example.brookiecooking.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.brookiecooking.RoomDB.User;

import java.util.List;

public class AdapterPopular extends RecyclerView.Adapter<AdapterPopular.myviewholder>{

    List<User> data;
    Context context;

    public AdapterPopular(List<User> data, Context context) {
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
        final User temp = data.get(holder.getAdapterPosition());

        // Split the time from ingredients
        String[] time = data.get(holder.getAdapterPosition()).getIng().split("\n");
        // Set time
        holder.txt2.setText("\uD83D\uDD50 "+time[0]);
        // Load image from link
        Glide.with(holder.txt2.getContext()).load(data.get(holder.getAdapterPosition()).getImg()).into(holder.img);
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
