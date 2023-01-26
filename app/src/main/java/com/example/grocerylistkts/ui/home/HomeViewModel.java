package com.example.grocerylistkts.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemDAO;
import com.example.grocerylistkts.GroceryItemDatabase;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    GroceryItemDAO groceryDao;

    public HomeViewModel(Application app) {
        super(app);
        GroceryItemDatabase groceryItemDatabase = GroceryItemDatabase.getInstance(app);
        groceryDao = groceryItemDatabase.groceryItemDAO();
    }

    public void update(GroceryItem groceryItem) {
        groceryDao.updateGroceryItem(groceryItem);
    }

    public void delete(GroceryItem groceryItem) {
        groceryDao.deleteGroceryItem(groceryItem);
    }

    public void insert(GroceryItem groceryItem) {
        groceryDao.insertGroceryItem(groceryItem);
    }

    public LiveData<List<GroceryItem>> getAll() {
        return groceryDao.getAll();
    }

}