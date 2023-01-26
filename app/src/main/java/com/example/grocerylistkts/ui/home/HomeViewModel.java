package com.example.grocerylistkts.ui.home;

import static com.example.grocerylistkts.ui.home.HomeFragment.db;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemDAO;
import com.example.grocerylistkts.GroceryItemDatabase;
import com.example.grocerylistkts.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private LiveData<List<GroceryItem>> groceryItemList;
    private FragmentHomeBinding fragmentHomeBinding;
    GroceryItemDAO groceryDao;

    public HomeViewModel(Application app) {
        super(app);
        GroceryItemDatabase groceryItemDatabase = GroceryItemDatabase.getInstance(app);
        groceryDao = groceryItemDatabase.groceryItemDAO();
        groceryItemList = groceryDao.getAll();
    }

    public void update(GroceryItem groceryItem) {
        groceryDao.updateGroceryItem(groceryItem);
    }
    public void delete(GroceryItem groceryItem) {
        groceryDao.deleteGroceryItem(groceryItem);
    }
    public long insert(GroceryItem groceryItem) {
        return groceryDao.insertGroceryItem(groceryItem);
    }
    public LiveData<List<GroceryItem>> getAll() {
        return groceryDao.getAll();
    }

}