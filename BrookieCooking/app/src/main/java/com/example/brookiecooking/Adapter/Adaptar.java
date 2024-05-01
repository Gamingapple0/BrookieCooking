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
/*import com.tiodev.vegtummy.R;
import com.tiodev.vegtummy.RecipeActivity;*/
import com.example.brookiecooking.R;
import com.example.brookiecooking.RoomDB.User;


import java.util.List;

public class Adaptar extends  RecyclerView.Adapter<Adaptar.myviewHolder>{

//    List<ResModel> data;
//    Context context;

    List<User> data;
    Context context;
//    ArrayList<String> data = new ArrayList<>();
//
//    public Adaptar(ArrayList<String> data) {
//        this.data = data;
//    }

    public Adaptar(List<User> data , Context context) {

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

        final User temp = data.get(position);

        holder.txt1.setText(data.get(position).getTittle());
        Glide.with(holder.txt1.getContext()).load(data.get(position).getImg()).into(holder.img);

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
        TextView txt1;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            img2 = itemView.findViewById(R.id.card_btn);
            txt1 = itemView.findViewById(R.id.txt1);
        }
    }



}
