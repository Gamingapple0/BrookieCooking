package com.example.brookiecooking.ui.grocery;

// Import statements

import static com.example.brookiecooking.MainActivity.allGroceryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.brookiecooking.Adapter.MyGroceryAdapter;
import com.example.brookiecooking.Grocery;
import com.example.brookiecooking.databinding.FragmentGroceryBinding;

import java.util.List;

public class GroceryFragment extends Fragment {

    private FragmentGroceryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize ViewModel
        GroceryViewModel groceryViewModel =
                new ViewModelProvider(this).get(GroceryViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentGroceryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up RecyclerView
        MyGroceryAdapter adapter = new MyGroceryAdapter();
        binding.groceryListView.setAdapter(adapter);
        binding.groceryListView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Calculate total cost of groceries
        double aggregateCost = calculateTotalCost(allGroceryList);
        binding.totalCost.setText("Total: $" + aggregateCost);

        return root;
    }

    // Method to calculate total cost of groceries
    private double calculateTotalCost(List<Grocery> groceryList) {
        double totalCost = 0.0;
        for (Grocery grocery : groceryList) {
            totalCost += grocery.getTotal();
        }
        return totalCost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unbind the view
        binding = null;
    }
}
