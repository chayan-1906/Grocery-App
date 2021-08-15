package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    // Android UI...
    private TextView textViewDetailsNewItem;
    private RecyclerView recyclerViewNewItem;
    private RecyclerView recyclerViewPopularItems;
    private RecyclerView recyclerViewSuggestedItems;
    private TextView textViewDetailsPopular;
    private TextView textViewDetailsSuggested;
    private BottomNavigationView bottomNavigationView;

    private GroceryItemAdapter newItemsAdapter;
    private GroceryItemAdapter popularItemsAdapter;
    private GroceryItemAdapter suggestedItemsAdapter;

    private Utils utils;

    @SuppressLint("NonConstantResourceId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_main, container, false );
        // Android UI...
        textViewDetailsNewItem = view.findViewById ( R.id.textViewDetailsNewItem );
        recyclerViewNewItem = view.findViewById ( R.id.recyclerViewNewItem );
        textViewDetailsPopular = view.findViewById ( R.id.textViewDetailsPopular );
        recyclerViewPopularItems = view.findViewById ( R.id.recyclerViewPopularItems );
        textViewDetailsSuggested = view.findViewById ( R.id.textViewDetailsSuggested );
        recyclerViewSuggestedItems = view.findViewById ( R.id.recyclerViewSuggestedItems );
        bottomNavigationView = view.findViewById ( R.id.bottomNavigationView );

        newItemsAdapter = new GroceryItemAdapter ( getActivity ( ) );
        popularItemsAdapter = new GroceryItemAdapter ( getActivity ( ) );
        suggestedItemsAdapter = new GroceryItemAdapter ( getActivity ( ) );

        recyclerViewNewItem.setAdapter ( newItemsAdapter );
        recyclerViewPopularItems.setAdapter ( popularItemsAdapter );
        recyclerViewSuggestedItems.setAdapter ( suggestedItemsAdapter );

        recyclerViewNewItem.setLayoutManager ( new LinearLayoutManager ( getActivity ( ), RecyclerView.HORIZONTAL, false ) );
        recyclerViewPopularItems.setLayoutManager ( new LinearLayoutManager ( getActivity ( ), RecyclerView.HORIZONTAL, false ) );
        recyclerViewSuggestedItems.setLayoutManager ( new LinearLayoutManager ( getActivity ( ), RecyclerView.HORIZONTAL, false ) );

        // make home button default...
        bottomNavigationView.setSelectedItemId ( R.id.item_home );
        bottomNavigationView.setOnNavigationItemSelectedListener ( item -> {
            switch (item.getItemId ( )) {
                case R.id.item_home:
                    Toast.makeText ( getActivity ( ), "Home selected", Toast.LENGTH_SHORT ).show ( );
                    break;
                case R.id.item_search:
                    Toast.makeText ( getActivity ( ), "Search selected", Toast.LENGTH_SHORT ).show ( );
                    Intent intentSearchActivity = new Intent ( getActivity ( ), SearchActivity.class );
                    intentSearchActivity.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity ( intentSearchActivity );
                    break;
                case R.id.item_cart:
                    Toast.makeText ( getActivity ( ), "Cart selected", Toast.LENGTH_SHORT ).show ( );
                    Intent intentCartActivity = new Intent ( getActivity ( ), CartActivity.class );
                    intentCartActivity.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity ( intentCartActivity );
                    break;
            }
            return false;
        } );
        utils = new Utils ( getContext ( ) );
        utils.initDatabase ( );
        updateRecyclerViews ( );
        return view;
    }

    private void updateRecyclerViews() {
        Log.i ( TAG, "updateRecyclerViews: started" );
        // for new items...
        ArrayList<GroceryItem> newItems = utils.getAllItems ( );
        Comparator<GroceryItem> newItemComparator = (o1, o2) -> o1.getId ( ) - o2.getId ( );
        Comparator<GroceryItem> reverseNewItemComparator = Collections.reverseOrder ( newItemComparator );
        Collections.sort ( newItems, reverseNewItemComparator );
        if (newItems != null) {
            newItemsAdapter.setGroceryItems ( newItems );
        }

        // for popular items...
        ArrayList<GroceryItem> popularItems = utils.getAllItems ( );
        Comparator<GroceryItem> popularityComparator = (o1, o2) -> compareByPopularity ( o1, o2 );
        Comparator<GroceryItem> reversePopularityComparator = Collections.reverseOrder ( popularityComparator );
        Collections.sort ( Objects.requireNonNull ( popularItems ), reversePopularityComparator );
        popularItemsAdapter.setGroceryItems ( popularItems );

        // for suggested items...
        ArrayList<GroceryItem> suggestedItems = utils.getAllItems ( );
        Comparator<GroceryItem> suggestedComparator = Comparator.comparingInt ( GroceryItem::getUserPoint );
        Comparator<GroceryItem> reverseSuggestedComparator = Collections.reverseOrder ( suggestedComparator );
        Collections.sort ( Objects.requireNonNull ( suggestedItems ), reverseSuggestedComparator );
        suggestedItemsAdapter.setGroceryItems ( suggestedItems );
    }

    @Override
    public void onResume() {
        updateRecyclerViews ( );
        super.onResume ( );
    }

    private int compareByPopularity(GroceryItem groceryItem1, GroceryItem groceryItem2) {
        Log.d ( TAG, "compareByPopularity: started" );
        return Integer.compare ( groceryItem1.getPopularityPoint ( ), groceryItem2.getPopularityPoint ( ) );
    }
}
