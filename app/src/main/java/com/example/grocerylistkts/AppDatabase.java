package com.example.grocerylistkts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GroceryItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroceryItemDAO groceryItemDAO();
}
