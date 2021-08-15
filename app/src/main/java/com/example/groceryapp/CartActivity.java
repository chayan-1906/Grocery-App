package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_cart );

        bottomNavigationView = findViewById ( R.id.bottomNavigationView );
        initBottomNavigationView ( );

        FragmentTransaction fragmentTransaction = getSupportFragmentManager ( ).beginTransaction ( );
        fragmentTransaction.replace ( R.id.fragment_container, new FirstCartFragment ( ) );
        fragmentTransaction.commit ( );
    }

    @SuppressLint("NonConstantResourceId")
    private void initBottomNavigationView() {
        Log.i ( TAG, "initBottomNavigationView: started" );
        bottomNavigationView.setSelectedItemId ( R.id.item_cart );
        bottomNavigationView.setOnNavigationItemSelectedListener ( menuItem -> {
            switch (menuItem.getItemId ( )) {
                case R.id.item_search:
                    Intent intentSearchActivity = new Intent ( CartActivity.this, SearchActivity.class );
                    intentSearchActivity.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity ( intentSearchActivity );
                    break;
                case R.id.item_home:
                    Intent intentHomeActivity = new Intent ( CartActivity.this, MainActivity.class );
                    intentHomeActivity.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity ( intentHomeActivity );
                    break;
                default:
                    break;
            }
            return false;
        } );
    }
}