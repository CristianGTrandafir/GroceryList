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
import java.util.List;

public class GroceryItemRVAdapter extends RecyclerView.Adapter<GroceryItemRVAdapter.MyViewHolder>{

    private final GroceryItemRVInterface rvInterface;       //Needed for click and long click callbacks
    Context context;                                        //Needed for click and long click callbacks
    ArrayList<GroceryItem> groceryItemArrayList = new ArrayList<>();    //Data array for views

    public GroceryItemRVAdapter(Context context, GroceryItemRVInterface rvInterface) {
        this.context = context;
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
        holder.groceryItemNameTextView.setText(groceryItemArrayList.get(position).getItemName() +
                " (" + groceryItemArrayList.get(position).getItemID() + ")");
        holder.groceryItemImageView.setImageResource(R.drawable.ic_dashboard_black_24dp);
        holder.itemView.setLongClickable(true);
    }

    public int getItemCount() {
        return groceryItemArrayList.size();
    }

    /**Helper method that updates groceryItemsArrayList with groceryItemsList when LiveData changes**/
    public void updateGroceryItemsList(List<GroceryItem> groceryItemsList) {
        groceryItemArrayList = (ArrayList<GroceryItem>) groceryItemsList;
        notifyDataSetChanged();
    }

    /**Helper method that accesses the groceryItemsArrayList with a position and returns a GroceryItem object**/
    public GroceryItem getGroceryItem(int position) {
        return groceryItemArrayList.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView groceryItemImageView;
        TextView groceryItemNameTextView;
        TextView groceryItemCountTextView;

        //Grab views from grocery_recyclerview_list_item.xml and sets up click and long click callbacks
        public MyViewHolder(@NonNull View itemView, GroceryItemRVInterface rvInterface) {
            super(itemView);
            groceryItemImageView = itemView.findViewById(R.id.imageViewGroceryItem);
            groceryItemNameTextView = itemView.findViewById(R.id.textViewGroceryName);
            groceryItemCountTextView = itemView.findViewById(R.id.textViewGroceryCount);
            itemView.setOnClickListener(v -> {
                if(rvInterface != null)
                    if(getAdapterPosition() != RecyclerView.NO_POSITION)
                        rvInterface.onItemClick(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v->{
                if(rvInterface != null)
                    if(getAdapterPosition() != RecyclerView.NO_POSITION)
                        rvInterface.onItemLongClick(getAdapterPosition());
                return true; //This has no semantic value yet
            });
        }
    }
}
