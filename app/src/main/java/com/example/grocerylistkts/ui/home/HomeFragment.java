package com.example.grocerylistkts.ui.home;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

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

import java.util.ArrayList;

public class HomeFragment extends Fragment implements GroceryItemRVInterface {

    ArrayList<GroceryItem> groceryItemArrayList = new ArrayList<>();
    GroceryItemRVAdapter rvAdapter;
    RecyclerView rv;
    FloatingActionButton fab;

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
        rvAdapter = new GroceryItemRVAdapter(this.getActivity(), groceryItemArrayList, this);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        registerForContextMenu(rv);
        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v-> {
            LayoutInflater popupInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = popupInflater.inflate(R.layout.popup_editable, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, 600);
            popupWindow.setElevation(5.0f);

            EditText nameEditText = popupView.findViewById(R.id.editTextName);
            EditText countEditText = popupView.findViewById(R.id.editTextCount);
            EditText idEditText = popupView.findViewById(R.id.editTextID);
            Button createNewItemButton = popupView.findViewById(R.id.buttonSaveChanges);
            createNewItemButton.setText("Create New Item");

            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
            popupWindow.showAtLocation(binding.getRoot().getRootView().findViewById(R.id.navigation_home), Gravity.CENTER, 0, 0);

            createNewItemButton.setOnClickListener(view -> {
                groceryItemArrayList.add(new GroceryItem((Integer.parseInt(countEditText.getText().toString())),
                        (nameEditText.getText().toString()),
                        (idEditText.getText().toString())));
                rvAdapter.notifyItemInserted(groceryItemArrayList.size());
                popupWindow.dismiss();
            });
        });
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

    @Override
    public void onItemClick(int position) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_editable, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, 600);
        popupWindow.setElevation(5.0f);

        EditText nameEditText = popupView.findViewById(R.id.editTextName);
        EditText countEditText = popupView.findViewById(R.id.editTextCount);
        EditText idEditText = popupView.findViewById(R.id.editTextID);
        Button saveButton = popupView.findViewById(R.id.buttonSaveChanges);

        nameEditText.setText(groceryItemArrayList.get(position).getItemName());
        countEditText.setText(groceryItemArrayList.get(position).getItemCount()+"");
        idEditText.setText(groceryItemArrayList.get(position).getItemID());

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        popupWindow.showAtLocation(binding.getRoot().getRootView().findViewById(R.id.navigation_home), Gravity.CENTER, 0, 0);

        saveButton.setOnClickListener(v -> {
            GroceryItem groceryItem = groceryItemArrayList.get(position);
            groceryItem.setItemCount(Integer.parseInt(countEditText.getText().toString()));
            groceryItem.setItemName(nameEditText.getText().toString());
            groceryItem.setItemID(idEditText.getText().toString());
            rvAdapter.notifyItemChanged(position);
            popupWindow.dismiss();
        });
    }

    @Override
    public void onItemLongClick(int position) {
        rvAdapter.notifyItemRangeRemoved(0,groceryItemArrayList.size());
        groceryItemArrayList.remove(position);
        rvAdapter.notifyItemRangeChanged(0, groceryItemArrayList.size());
    }

}