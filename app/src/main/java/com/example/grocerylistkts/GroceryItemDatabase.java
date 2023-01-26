package com.example.grocerylistkts;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GroceryItem.class}, version = 1)
public abstract class GroceryItemDatabase extends RoomDatabase {
    private static GroceryItemDatabase instance;
    public abstract GroceryItemDAO groceryItemDAO();
    public static synchronized GroceryItemDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, GroceryItemDatabase.class, "GROCERY_ITEM_DATABASE").build();
        }
        return instance;
    }
}
