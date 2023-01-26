package com.example.grocerylistkts.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemRVAdapter;
import com.example.grocerylistkts.GroceryItemRVInterface;
import com.example.grocerylistkts.R;
import com.example.grocerylistkts.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment implements GroceryItemRVInterface {

    GroceryItemRVAdapter rvAdapter;
    RecyclerView rv;
    FloatingActionButton fab;

    View popupView;
    PopupWindow popupWindow;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv = root.findViewById(R.id.groceryItemRecyclerView);
        rvAdapter = new GroceryItemRVAdapter(getActivity(), this);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> displayPopupWindow(0,true));

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAll().observe(getViewLifecycleOwner(),
                groceryItems -> {
                    Log.i("Home", groceryItems.toString() + "getAll");
                    rvAdapter.updateGroceryItemsList(groceryItems);
                });

        return root;
    }

    /**Displays a popup menu for the user. Used for both inserting and updating grocery items.**/
    public void displayPopupWindow(int position, boolean isInsert) {
        LayoutInflater popupInflater = getActivity().getLayoutInflater();
        popupView = popupInflater.inflate(R.layout.popup_editable, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, 600);
        popupWindow.setElevation(5.0f);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        popupWindow.showAtLocation(binding.getRoot().getRootView().findViewById(R.id.navigation_home), Gravity.CENTER, 0, 0);

        if(isInsert)
            insertItem();
        else
            updateItem(position);
    }

    /**Helper method for displayPopupMenu. Called when user clicks on FAB to insert new grocery item.**/
    public void insertItem() {
        EditText nameEditText = popupView.findViewById(R.id.editTextName);
        EditText countEditText = popupView.findViewById(R.id.editTextCount);
        EditText idEditText = popupView.findViewById(R.id.editTextID);

        Button insertNewItemButton = popupView.findViewById(R.id.buttonSaveChanges);
        insertNewItemButton.setText("Create New Item");

        insertNewItemButton.setOnClickListener(view -> {
            GroceryItem newGroceryItem = new GroceryItem(
                    Integer.parseInt(countEditText.getText().toString()),
                    nameEditText.getText().toString(),
                    idEditText.getText().toString());
            executor.submit(() -> {
                Log.i("Home", "Insert");
                homeViewModel.insert(newGroceryItem); //This fails if item has duplicate ID.
            });
            popupWindow.dismiss();
        });
    }

    /**Helper method for displayPopupMenu. Called when user clicks RV item to update existing grocery item.**/
    public void updateItem(int position) {
        EditText nameEditText = popupView.findViewById(R.id.editTextName);
        EditText countEditText = popupView.findViewById(R.id.editTextCount);
        EditText idEditText = popupView.findViewById(R.id.editTextID);
        Button updateButton = popupView.findViewById(R.id.buttonSaveChanges);

        nameEditText.setText(rvAdapter.getGroceryItem(position).getItemName());
        countEditText.setText(rvAdapter.getGroceryItem(position).getItemCount() + "");
        idEditText.setText(rvAdapter.getGroceryItem(position).getItemID());
        idEditText.setEnabled(false); //Don't allow user to change ID since it's primary key in database

        updateButton.setOnClickListener(v -> {
            GroceryItem groceryItem = rvAdapter.getGroceryItem(position);
            groceryItem.setItemCount(Integer.parseInt(countEditText.getText().toString()));
            groceryItem.setItemName(nameEditText.getText().toString());
            executor.submit(() -> {
                Log.i("Home", "Update");
                homeViewModel.update(groceryItem);
            });
            popupWindow.dismiss();
        });
    }

    @Override
    public void onItemClick(int position) {
        displayPopupWindow(position, false);
    }

    @Override
    public void onItemLongClick(int position) {
        executor.submit(() -> {
            Log.i("Home", "Delete");
            homeViewModel.delete(rvAdapter.getGroceryItem(position));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}