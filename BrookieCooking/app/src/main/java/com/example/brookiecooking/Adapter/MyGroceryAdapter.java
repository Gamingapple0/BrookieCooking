package com.example.brookiecooking.Adapter;

import static com.example.brookiecooking.MainActivity.allAllergies;
import static com.example.brookiecooking.MainActivity.allGroceryList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brookiecooking.Grocery;
import com.example.brookiecooking.R;

import org.w3c.dom.Text;

import java.util.List;

public class MyGroceryAdapter extends RecyclerView.Adapter<MyGroceryAdapter.ViewHolder> {

    public MyGroceryAdapter() {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery currentGrocery = allGroceryList.get(position);

        holder.cardText.setText(currentGrocery.getBody());
        holder.totalAmount.setText(currentGrocery.getTotal().toString());
    }


    @Override
    public int getItemCount() {
        return allGroceryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView totalAmount;
        TextView cardText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            totalAmount = itemView.findViewById(R.id.totalAmount);
            cardText = itemView.findViewById(R.id.groceryItem);
        }
    }
}
