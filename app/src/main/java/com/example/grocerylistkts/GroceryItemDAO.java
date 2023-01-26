package com.example.grocerylistkts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroceryItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGroceryItem(GroceryItem groceryItem);

    @Update
    void updateGroceryItem(GroceryItem groceryItem);

    @Delete
    void deleteGroceryItem(GroceryItem groceryItem);

    @Query("SELECT* FROM GROCERY_ITEM_TABLE ORDER BY ITEM_NAME DESC")
    LiveData<List<GroceryItem>> getAll();
}
