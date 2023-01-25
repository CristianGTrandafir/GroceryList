package com.example.grocerylistkts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GROCERY_ITEM_TABLE")
public class GroceryItem {
    @ColumnInfo(name = "ITEM_COUNT")
    private int itemCount;
    @ColumnInfo(name = "ITEM_NAME")
    private String itemName;
    @PrimaryKey
    @ColumnInfo(name = "ITEM_ID")
    @NonNull
    private String itemID;

    public int getItemCount() {
        return itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemID() {
        return itemID;
    }

    public GroceryItem(int itemCount, String itemName, String itemID) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemID = itemID;
    }
}
