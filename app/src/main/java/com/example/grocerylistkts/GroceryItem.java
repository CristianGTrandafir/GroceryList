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
    @ColumnInfo(name = "ITEM_ID") @NonNull @PrimaryKey
    private String itemID;

    public GroceryItem(int itemCount, String itemName, String itemID) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemID = itemID;
    }

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

    public String getItemID() {
        return itemID;
    }
}
