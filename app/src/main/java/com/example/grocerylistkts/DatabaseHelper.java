package com.example.grocerylistkts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String GROCERY_LIST_TABLE = "GROCERY_LIST_TABLE";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_COUNT = "ITEM_COUNT";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "groceryList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + GROCERY_LIST_TABLE + " (" + COLUMN_ID + " STRING PRIMARY KEY, " + COLUMN_ITEM_NAME + " STRING, " + COLUMN_ITEM_COUNT + " INT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(GroceryItem groceryItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, groceryItem.getItemID());
        cv.put(COLUMN_ITEM_NAME, groceryItem.getItemName());
        cv.put(COLUMN_ITEM_COUNT, groceryItem.getItemCount());

        long insert = db.insert(GROCERY_LIST_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<GroceryItem> selectAll() {
        ArrayList<GroceryItem> returnList = new ArrayList<>();
        String queryString = "SELECT* FROM " + GROCERY_LIST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            do{
                String groceryID = cursor.getString(0);
                String groceryName = cursor.getString(1);
                int groceryCount = cursor.getInt(2);
                GroceryItem groceryItem = new GroceryItem(groceryCount, groceryName, groceryID);
                returnList.add(groceryItem);
            } while(cursor.moveToNext());
        }

        return returnList;
    }

    public boolean deleteOne(GroceryItem groceryItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + GROCERY_LIST_TABLE + " WHERE " + COLUMN_ID + "='" + groceryItem.getItemID() + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }
}
