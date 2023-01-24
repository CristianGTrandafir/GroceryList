package com.example.grocerylistkts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryItemRVAdapter extends RecyclerView.Adapter<GroceryItemRVAdapter.MyViewHolder>{
    private final GroceryItemRVInterface rvInterface;
    Context context;
    ArrayList<GroceryItem> groceryItemArrayList;

    public GroceryItemRVAdapter(Context context, ArrayList<GroceryItem> groceryItemArrayList,
                                GroceryItemRVInterface rvInterface) {
        this.context = context;
        this.groceryItemArrayList = groceryItemArrayList;
        this.rvInterface = rvInterface;
    }
    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grocery_recyclerview_list_item, parent, false);
        return new GroceryItemRVAdapter.MyViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryItemRVAdapter.MyViewHolder holder, int position) {
        holder.groceryItemCountTextView.setText(groceryItemArrayList.get(position).getItemCount()+"");
        holder.groceryItemNameTextView.setText(groceryItemArrayList.get(position).getItemName());
        holder.groceryItemImageView.setImageResource(R.drawable.ic_dashboard_black_24dp);
        holder.itemView.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        return groceryItemArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView groceryItemImageView;
        TextView groceryItemNameTextView;
        TextView groceryItemCountTextView;
        public MyViewHolder(@NonNull View itemView, GroceryItemRVInterface rvInterface) {
            //Grab views from RVlayout file
            super(itemView);
            groceryItemImageView = itemView.findViewById(R.id.imageViewGroceryItem);
            groceryItemNameTextView = itemView.findViewById(R.id.textViewGroceryName);
            groceryItemCountTextView = itemView.findViewById(R.id.textViewGroceryCount);
            itemView.setOnClickListener(v -> {
                if(rvInterface != null) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        rvInterface.onItemClick(pos);
                    }
                }
            });
            itemView.setOnLongClickListener(v->{
                if(rvInterface != null) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        rvInterface.onItemLongClick(pos);
                    }
                }
                return false;
            });
        }
    }
}
