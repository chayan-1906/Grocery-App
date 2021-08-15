package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SelectCategory {

    private static final String TAG = "SearchActivity";

    private EditText editTextSearchBar;
    private ImageView imageViewSearch;
    private TextView textViewFirstCategory;
    private TextView textViewSecondCategory;
    private TextView textViewThirdCategory;
    private TextView textViewAllCategories;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    private Utils utils;
    ArrayList<GroceryItem> groceryItems;
    private GroceryItemAdapter groceryItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_search );

        editTextSearchBar = findViewById ( R.id.editTextSearchBar );
        imageViewSearch = findViewById ( R.id.imageViewSearch );
        textViewFirstCategory = findViewById ( R.id.textViewFirstCategory );
        textViewSecondCategory = findViewById ( R.id.textViewSecondCategory );
        textViewThirdCategory = findViewById ( R.id.textViewThirdCategory );
        textViewAllCategories = findViewById ( R.id.textViewAllCategories );
        recyclerView = findViewById ( R.id.recyclerView );
        bottomNavigationView = findViewById ( R.id.bottomNavigationView );

        utils = new Utils ( this );
        groceryItemAdapter = new GroceryItemAdapter ( this );
        recyclerView.setAdapter ( groceryItemAdapter );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        initBottomNavigation ( );
        initThreeTextViews ( );

        imageViewSearch.setOnClickListener ( view -> initiateSearch ( ) );

        textViewAllCategories.setOnClickListener ( view -> {
            // TODO: show a dialog
            ShowAllCategoriesDialog showAllCategoriesDialog = new ShowAllCategoriesDialog ( );
            showAllCategoriesDialog.show ( getSupportFragmentManager ( ), "all dialog" );
        } );

        editTextSearchBar.addTextChangedListener ( new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<GroceryItem> groceryItems = utils.searchForItem ( String.valueOf ( charSequence ) );
                groceryItemAdapter.setGroceryItems ( groceryItems );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }

    private void initThreeTextViews() {
        Log.i ( TAG, "initThreeTextViews: started" );
        ArrayList<String> categories = utils.getThreeCategories ( );
        switch (categories.size ( )) {
            case 1:
                textViewFirstCategory.setText ( categories.get ( 0 ) );
                textViewSecondCategory.setVisibility ( View.GONE );
                textViewThirdCategory.setVisibility ( View.GONE );
                break;
            case 2:
                textViewFirstCategory.setText ( categories.get ( 0 ) );
                textViewSecondCategory.setText ( categories.get ( 1 ) );
                textViewThirdCategory.setVisibility ( View.GONE );
                break;
            case 3:
                textViewFirstCategory.setText ( categories.get ( 0 ) );
                textViewSecondCategory.setText ( categories.get ( 1 ) );
                textViewThirdCategory.setText ( categories.get ( 2 ) );
                break;
            default:
                break;
        }

        textViewFirstCategory.setOnClickListener ( v -> {
            Intent intent = new Intent ( SearchActivity.this, ShowItemsByCategoryActivity.class );
            intent.putExtra ( "category", textViewFirstCategory.getText ( ).toString ( ) );
            startActivity ( intent );
        } );

        textViewSecondCategory.setOnClickListener ( v -> {
            Intent intent = new Intent ( SearchActivity.this, ShowItemsByCategoryActivity.class );
            intent.putExtra ( "category", textViewSecondCategory.getText ( ).toString ( ) );
            startActivity ( intent );
        } );

        textViewThirdCategory.setOnClickListener ( v -> {
            Intent intent = new Intent ( SearchActivity.this, ShowItemsByCategoryActivity.class );
            intent.putExtra ( "category", textViewThirdCategory.getText ( ).toString ( ) );
            startActivity ( intent );
        } );
    }

    private void initiateSearch() {
        Log.i ( TAG, "initiateSearch: started" );
        String text = editTextSearchBar.getText ( ).toString ( ).trim ( );
        groceryItems = utils.searchForItem ( text );
        for (GroceryItem groceryItem : groceryItems) {
            utils.increaseUserPoint ( groceryItem, 3 );
        }
        groceryItemAdapter.setGroceryItems ( groceryItems );
    }

    @SuppressLint("NonConstantResourceId")
    private void initBottomNavigation() {
        Log.i ( TAG, "initBottomNavigation: started" );
        bottomNavigationView.setSelectedItemId ( R.id.item_search );
        bottomNavigationView.setOnNavigationItemSelectedListener ( item -> {
            switch (item.getItemId ( )) {
                case R.id.item_home:
                    Intent intentMainActivity = new Intent ( SearchActivity.this, MainActivity.class );
                    intentMainActivity.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity ( intentMainActivity );
                    break;
                case R.id.item_search:
                    break;
                case R.id.item_cart:
                    Intent intentCartActivity = new Intent ( SearchActivity.this, CartActivity.class );
                    intentCartActivity.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity ( intentCartActivity );
                    break;
                default:
                    break;
            }
            return false;
        } );
    }

    @Override
    public void onSelectCategoryResult(String category) {
        Log.i ( TAG, "onSelectCategoryResult: started" );
        Intent intentShowItemsByCategoryActivity = new Intent ( this, ShowItemsByCategoryActivity.class );
        intentShowItemsByCategoryActivity.putExtra ( "category", category );
        startActivity ( intentShowItemsByCategoryActivity );
    }
}