package com.example.groceryapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.R;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment implements GetTotalPrice, DeleteCartItem {

    private static final String TAG = "FirstCartFragment";

    private TextView textViewCartExplain;
    private RecyclerView recyclerView;
    private TextView textViewNoItem;
    private TextView textViewSumExplain;
    private TextView textViewSum;
    private Button btnNext;

    private CartRecyclerViewAdapter cartRecyclerViewAdapter;

    private double totalPrice = 0;
    private ArrayList<Integer> items;
    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_first_cart, container, false );

        textViewCartExplain = view.findViewById ( R.id.textViewCartExplain );
        recyclerView = view.findViewById ( R.id.recyclerView );
        textViewNoItem = view.findViewById ( R.id.textViewNoItem );
        textViewSumExplain = view.findViewById ( R.id.textViewSumExplain );
        textViewSum = view.findViewById ( R.id.textViewSum );
        btnNext = view.findViewById ( R.id.btnNext );

        items = new ArrayList<> ( );
        utils = new Utils ( getActivity ( ) );
        initRecyclerView ( );

        btnNext.setOnClickListener ( v -> {
            // TODO: Navigate to next fragment
            Order order = new Order ( );
            order.setTotalPrice ( totalPrice );
            order.setIntegerArrayList ( items );
            Bundle bundle = new Bundle ( );
            bundle.putParcelable ( "order", order );
            SecondCartFragment cartSecondFragment = new SecondCartFragment ( );
            cartSecondFragment.setArguments ( bundle );
            getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( ).setCustomAnimations ( R.anim.in, R.anim.out ).replace ( R.id.fragment_container, cartSecondFragment ).commit ( );
        } );

        return view;
    }

    private void initRecyclerView() {
        Log.d ( TAG, "initRecView: started" );
        cartRecyclerViewAdapter = new CartRecyclerViewAdapter ( this );
        recyclerView.setAdapter ( cartRecyclerViewAdapter );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( getActivity ( ) ) );
        Utils utils = new Utils ( getActivity ( ) );
        ArrayList<Integer> itemsIds = utils.getCartItems ( );
        if (itemsIds != null) {
            ArrayList<GroceryItem> groceryItems = utils.getItemsById ( itemsIds );
            if (groceryItems.size ( ) == 0) {
                btnNext.setVisibility ( View.GONE );
                btnNext.setEnabled ( false );
                recyclerView.setVisibility ( View.GONE );
                textViewNoItem.setVisibility ( View.VISIBLE );
            }
            this.items = itemsIds;
            cartRecyclerViewAdapter.setItems ( groceryItems );
        } else {
            btnNext.setVisibility ( View.GONE );
            btnNext.setEnabled ( false );
            recyclerView.setVisibility ( View.GONE );
            textViewNoItem.setVisibility ( View.VISIBLE );
        }
    }

    @Override
    public void onDeletingResult(GroceryItem item) {
        Log.d ( TAG, "onDeletingResult: attempting to delete " + item.toString ( ) );
        ArrayList<Integer> itemIds = new ArrayList<> ( );
        itemIds.add ( item.getId ( ) );
        ArrayList<GroceryItem> items = utils.getItemsById ( itemIds );
        if (items.size ( ) > 0) {
            ArrayList<Integer> newItemsIds = utils.deleteCartItem ( items.get ( 0 ) );
            if (newItemsIds.size ( ) == 0) {
                btnNext.setVisibility ( View.GONE );
                btnNext.setEnabled ( false );
                recyclerView.setVisibility ( View.GONE );
                textViewNoItem.setVisibility ( View.VISIBLE );
            } else {
                btnNext.setVisibility ( View.VISIBLE );
                btnNext.setEnabled ( true );
                recyclerView.setVisibility ( View.VISIBLE );
                textViewNoItem.setVisibility ( View.GONE );
            }
            ArrayList<GroceryItem> newItems = utils.getItemsById ( newItemsIds );
            this.items = newItemsIds;
            cartRecyclerViewAdapter.setItems ( newItems );
        }
    }

    @Override
    public void onGettingTotalPriceResult(double price) {
        Log.d ( TAG, "onGettingTotalPriceResult: totalPrice: " + price );
        textViewSum.setText ( String.valueOf ( price ) );
        this.totalPrice = price;
    }
}