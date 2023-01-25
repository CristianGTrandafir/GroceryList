package com.example.grocerylistkts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GroceryItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertGroceryItem(GroceryItem groceryItem);

    @Update
    void updateGroceryItem(GroceryItem groceryItem);

    @Query("DELETE FROM GROCERY_ITEM_TABLE WHERE ITEM_ID = :itemID")
    void deleteGroceryItem(String itemID);

    @Query("SELECT* FROM GROCERY_ITEM_TABLE")
    List<GroceryItem> getAll();
}
