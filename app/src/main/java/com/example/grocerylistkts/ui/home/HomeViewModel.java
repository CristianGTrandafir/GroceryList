package com.example.grocerylistkts.ui.home;

import static com.example.grocerylistkts.ui.home.HomeFragment.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerylistkts.GroceryItem;
import com.example.grocerylistkts.GroceryItemDAO;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private LiveData<List<GroceryItem>> groceryItemList;

    GroceryItemDAO userDao = db.groceryItemDAO();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void update(GroceryItem groceryItem) {
        userDao.updateGroceryItem(groceryItem);
    }
    public void delete(GroceryItem groceryItem) {
        userDao.deleteGroceryItem(groceryItem);
    }
    public long insert(GroceryItem groceryItem) {
        return userDao.insertGroceryItem(groceryItem);
    }
    public LiveData<List<GroceryItem>> getAll() {
        return userDao.getAll();
    }

}