package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryapp.R;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {

    private static final String TAG = "GroceryItemAdapter";

    private Context context;
    private ArrayList<GroceryItem> groceryItems = new ArrayList<> (  );

    public GroceryItemAdapter() {
    }

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.grocery_rec_view_list_item, parent, false );
        ViewHolder viewHolder = new ViewHolder ( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d ( TAG, "onBindViewHolder: called" );
        holder.textViewItemName.setText ( groceryItems.get ( position ).getName () );
        holder.textViewPrice.setText ( String.valueOf ( groceryItems.get ( position ).getPrice () ) );
        Glide.with ( context ).asBitmap ().load ( groceryItems.get ( position ).getImageUrl () ).into ( holder.imageViewItem );
        holder.parent.setOnClickListener ( v -> {
            // TODO: navigate to another activity
            Intent intentGroceryItemActivity = new Intent ( context, GroceryItemActivity.class );
            intentGroceryItemActivity.putExtra ( "item", groceryItems.get ( position ) );
            context.startActivity ( intentGroceryItemActivity );
        } );
    }

    @Override
    public int getItemCount() {
        return groceryItems.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView parent;
        private final ImageView imageViewItem;
        private final TextView textViewPrice;
        private final TextView textViewItemName;

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );

            parent = itemView.findViewById ( R.id.parent );
            imageViewItem = itemView.findViewById ( R.id.imageViewItem );
            textViewPrice = itemView.findViewById ( R.id.textViewPrice );
            textViewItemName = itemView.findViewById ( R.id.textViewItemName );
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setGroceryItems(ArrayList<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
        notifyDataSetChanged ();
    }
}
