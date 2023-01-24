package com.example.grocerylistkts.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemRVAdapter;
import com.example.grocerylistkts.R;
import com.example.grocerylistkts.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<GroceryItem> groceryItemArrayList = new ArrayList<>();
    RecyclerView rv;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        rv = root.findViewById(R.id.groceryItemRecyclerView);
        setUpGroceryItemArrayList();
        GroceryItemRVAdapter adapter = new GroceryItemRVAdapter(this.getActivity(), groceryItemArrayList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return root;
    }


    private void setUpGroceryItemArrayList() {
        String[] groceryItemIDList = new String[]{"first", "second", "third"};
        String[] groceryItemCountList = new String[]{"1", "2", "3"};
        String[] groceryItemNameList = new String[]{"firstName", "secondName", "thirdName"};

        for(int i = 0 ; i < groceryItemIDList.length; i++) {
            groceryItemArrayList.add(new GroceryItem(Integer.parseInt(groceryItemCountList[i]), groceryItemNameList[i], groceryItemIDList[i]));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}