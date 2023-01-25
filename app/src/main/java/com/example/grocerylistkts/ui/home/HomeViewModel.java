package com.example.grocerylistkts.ui.home;

import static com.example.grocerylistkts.ui.home.HomeFragment.db;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerylistkts.AppDatabase;
import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemDAO;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private LiveData<List<GroceryItem>> groceryItemList;

    GroceryItemDAO userDao;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void update(GroceryItem groceryItem) {

    }
    public void delete(GroceryItem groceryItem) {

    }
    public void insert(GroceryItem groceryItem) {

    }
    public LiveData<List<GroceryItem>> getAll() {
        userDao = db.groceryItemDAO();
        return userDao.getAll();
    }

}