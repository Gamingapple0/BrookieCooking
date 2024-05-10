package com.example.brookiecooking.ui.notifications;

import static com.example.brookiecooking.MainActivity.allAllergies;
import static com.example.brookiecooking.MainActivity.allGroceryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brookiecooking.Adapter.MyAllergiesViewAdapter;
import com.example.brookiecooking.Adapter.MyGroceryAdapter;
import com.example.brookiecooking.Grocery;
import com.example.brookiecooking.R;
import com.example.brookiecooking.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.groceryListView.setAdapter(new MyGroceryAdapter());
        binding.groceryListView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        Double aggregateCost = 0.0;
        for (Grocery card: allGroceryList) {
            aggregateCost += card.getTotal();
        }
        binding.totalCost.setText("Total: $" + aggregateCost);
/*        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}