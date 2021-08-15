package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.groceryapp.R;

import java.util.ArrayList;

public class ShowItemsByCategoryActivity extends AppCompatActivity {

    private static final String TAG = "ShowItemsByCategoryActivity";

    private TextView textViewCategoryExplained;
    private TextView textViewCategory;
    private RecyclerView recyclerView;

    private GroceryItemAdapter groceryItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_show_items_by_category );

        textViewCategoryExplained = findViewById ( R.id.textViewCategoryExplained );
        textViewCategory = findViewById ( R.id.textViewCategory );
        recyclerView = findViewById ( R.id.recyclerView );

        groceryItemAdapter = new GroceryItemAdapter ( this );
        recyclerView.setAdapter ( groceryItemAdapter );
        recyclerView.setLayoutManager ( new GridLayoutManager ( this, 2 ) );

        try {
            Intent intent = getIntent ( );
            String category = intent.getStringExtra ( "category" );
            Utils utils = new Utils ( this );
            ArrayList<GroceryItem> itemArrayList = utils.getItemsByCategory ( category );
            groceryItemAdapter.setGroceryItems ( itemArrayList );
            textViewCategory.setText ( category );
        } catch (NullPointerException e) {
            e.printStackTrace ( );
        }
    }
}