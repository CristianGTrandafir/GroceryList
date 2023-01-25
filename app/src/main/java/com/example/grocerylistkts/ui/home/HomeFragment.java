package com.example.grocerylistkts.ui.home;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.room.Room;

import com.example.grocerylistkts.AppDatabase;
import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemDAO;
import com.example.grocerylistkts.GroceryItemRVAdapter;
import com.example.grocerylistkts.GroceryItemRVInterface;
import com.example.grocerylistkts.R;
import com.example.grocerylistkts.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.lifecycle.Observer;

public class HomeFragment extends Fragment implements GroceryItemRVInterface {

    ArrayList<GroceryItem> groceryItemArrayList = new ArrayList<>();

    GroceryItemRVAdapter rvAdapter;
    RecyclerView rv;
    FloatingActionButton fab;

    Handler handler = new Handler(Looper.getMainLooper());
    static AppDatabase db;
    GroceryItemDAO userDao;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    Observer<List<GroceryItem>> groceryItemUpdateObserver = new Observer<List<GroceryItem>>() {
        @Override
        public void onChanged(List<GroceryItem> groceryItems) {
            Log.i("Model", groceryItems.toString() +"Hello");
            groceryItemArrayList = (ArrayList<GroceryItem>) groceryItems;
            rvAdapter.updateGroceryItemsList(groceryItems);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "database-name").build();
        userDao = db.groceryItemDAO();
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        rv = root.findViewById(R.id.groceryItemRecyclerView);
        //setUpGroceryItemArrayList(this);
        rvAdapter = new GroceryItemRVAdapter(getActivity(), groceryItemArrayList, this);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAll().observe(getViewLifecycleOwner(), groceryItemUpdateObserver);

        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> setUpPopupMenu());
        return root;
    }



    private void setUpGroceryItemArrayList(GroceryItemRVInterface inter) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                //groceryItemArrayList = (ArrayList<GroceryItem>) userDao.getAll();
                Log.i("SelectAll", "Selected");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rvAdapter = new GroceryItemRVAdapter(getActivity(), groceryItemArrayList, inter);
                        rv.setAdapter(rvAdapter);
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        setUpPopupMenu2(position);
    }

    @Override
    public void onItemLongClick(int position) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                userDao.deleteGroceryItem(groceryItemArrayList.get(position).getItemID());
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rvAdapter.notifyItemRangeRemoved(0, groceryItemArrayList.size());
                        groceryItemArrayList.remove(position);
                        rvAdapter.notifyItemRangeChanged(0, groceryItemArrayList.size());
                    }
                });
            }
        });
    }

    public void setUpPopupMenu2(int position) {
        LayoutInflater popupInflater = getActivity().getLayoutInflater();
        final View popupView = popupInflater.inflate(R.layout.popup_editable, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, 600);
        popupWindow.setElevation(5.0f);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        popupWindow.showAtLocation(binding.getRoot().getRootView().findViewById(R.id.navigation_home), Gravity.CENTER, 0, 0);

        EditText nameEditText = popupView.findViewById(R.id.editTextName);
        EditText countEditText = popupView.findViewById(R.id.editTextCount);
        EditText idEditText = popupView.findViewById(R.id.editTextID);
        Button updateButton = popupView.findViewById(R.id.buttonSaveChanges);

        nameEditText.setText(groceryItemArrayList.get(position).getItemName());
        countEditText.setText(groceryItemArrayList.get(position).getItemCount() + "");
        idEditText.setText(groceryItemArrayList.get(position).getItemID());

        updateButton.setOnClickListener(v -> {
            GroceryItem groceryItem = groceryItemArrayList.get(position);
            groceryItem.setItemCount(Integer.parseInt(countEditText.getText().toString()));
            groceryItem.setItemName(nameEditText.getText().toString());
            groceryItem.setItemID(idEditText.getText().toString());
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    userDao.updateGroceryItem(groceryItem);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            rvAdapter.notifyItemChanged(position);
                        }
                    });
                }
            });
            popupWindow.dismiss();
        });
    }


    public void setUpPopupMenu() {
        LayoutInflater popupInflater = getActivity().getLayoutInflater();
        final View popupView = popupInflater.inflate(R.layout.popup_editable, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, 600);
        popupWindow.setElevation(5.0f);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        popupWindow.showAtLocation(binding.getRoot().getRootView().findViewById(R.id.navigation_home), Gravity.CENTER, 0, 0);

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
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    long success = userDao.insertGroceryItem(newGroceryItem);
                    if (success != -1) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                groceryItemArrayList.add(newGroceryItem);
                                Log.i("Insert", "Inserted");
                                rvAdapter.notifyItemInserted(groceryItemArrayList.size() - 1);
                            }
                        });
                    }
                }
            });
            popupWindow.dismiss();
        });
    }

    public void insertNewItem(View popupView) {

    }

}