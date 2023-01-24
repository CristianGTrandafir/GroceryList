package com.example.grocerylistkts;

public class GroceryItem {
    private int itemCount;
    private String itemName;
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
