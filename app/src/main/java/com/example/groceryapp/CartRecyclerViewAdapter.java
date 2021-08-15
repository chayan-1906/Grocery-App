package com.example.groceryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.R;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CartRecyclerViewAdapter";

    private static ArrayList<GroceryItem> groceryItems = new ArrayList<> ( );
    private GetTotalPrice getTotalPrice;
    private DeleteCartItem deleteCartItem;
    private Fragment fragment;

    public CartRecyclerViewAdapter() {
    }

    public CartRecyclerViewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i ( TAG, "onCreateViewHolder: started" );
        View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.cart_list_item, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i ( TAG, "onBindViewHolder: started" );
        holder.textViewName.setText ( groceryItems.get ( position ).getName ( ) );
        holder.textViewPrice.setText ( String.valueOf ( groceryItems.get ( position ).getPrice ( ) ) );
        deleteCartItem = (DeleteCartItem) fragment;
        holder.btnDelete.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                // TODO: Delete the item here
                AlertDialog.Builder builder = new AlertDialog.Builder ( fragment.getActivity ( ) );
                builder.setTitle ( "Delete Item" )
                        .setMessage ( "" )
                        .setPositiveButton ( "Yes", new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteCartItem.onDeletingResult ( groceryItems.get ( position ) );
                            }
                        } )
                        .setNeutralButton ( "No", new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        } );
                builder.show ( );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return groceryItems.size ( );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final Button btnDelete;
        private final TextView textViewPrice;

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );

            textViewName = itemView.findViewById ( R.id.textViewName );
            btnDelete = itemView.findViewById ( R.id.btnDelete );
            textViewPrice = itemView.findViewById ( R.id.textViewPrice );
        }
    }

    public void setItems(ArrayList<GroceryItem> groceryItems) {
        Log.i ( TAG, "setItems: started" );
        CartRecyclerViewAdapter.groceryItems = groceryItems;
        calculatePrice ( );
        notifyDataSetChanged ( );
    }

    private void calculatePrice() {
        Log.d ( TAG, "calculatePrice: started" );
        try {
            getTotalPrice = (GetTotalPrice) fragment;
        } catch (ClassCastException e) {
            e.printStackTrace ( );
        }
        double totalPrice = 0;
        for (GroceryItem item : groceryItems) {
            totalPrice += item.getPrice ( );
        }
        getTotalPrice.onGettingTotalPriceResult ( totalPrice );
    }
}