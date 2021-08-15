package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.groceryapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FrameLayout fragment_container;
    private NavigationView navigation_drawer_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        drawer = findViewById ( R.id.drawer );
        toolbar = findViewById ( R.id.toolbar );
        fragment_container = findViewById ( R.id.fragment_container );
        navigation_drawer_menu = findViewById ( R.id.navigation_drawer_menu );

        setSupportActionBar ( toolbar );
        Objects.requireNonNull ( getSupportActionBar ( ) ).setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ( ).setDisplayShowHomeEnabled ( true );
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.drawer_open, R.string.drawer_closed );
        drawer.addDrawerListener ( actionBarDrawerToggle );
        actionBarDrawerToggle.syncState ( );
        navigation_drawer_menu.setNavigationItemSelectedListener ( this );

        FragmentTransaction fragmentTransaction = getSupportFragmentManager ( ).beginTransaction ( );
        fragmentTransaction.replace ( R.id.fragment_container, new MainFragment ( ) );
        fragmentTransaction.commit ( );
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ( )) {
            case R.id.item_cart:
                // TODO: right the proper logic for different situations
                Intent intentCartActivity = new Intent ( MainActivity.this, CartActivity.class );
                intentCartActivity.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity ( intentCartActivity );
                break;
            case R.id.item_categories:
                ShowAllCategoriesDialog showAllCategoriesDialog = new ShowAllCategoriesDialog ( );
                showAllCategoriesDialog.show ( getSupportFragmentManager ( ), "all categories" );
                break;
            case R.id.item_about_us:
                AlertDialog.Builder builder = new AlertDialog.Builder ( MainActivity.this );
                builder.setTitle ( "About" )
                        .setMessage ( "Instructed and developed by Padmanabha" )
                        .setNegativeButton ( "OK", (dialogInterface, i) -> {
                            // TODO: show the url
                        } );
                builder.create ( ).show ( );
                break;
            case R.id.item_terms:
                AlertDialog.Builder termsBuilder = new AlertDialog.Builder ( MainActivity.this );
                termsBuilder.setTitle ( "Terms of use" )
                        .setMessage ( "No extra terms\n enjoy" )
                        .setPositiveButton ( "OK", (dialogInterface, i) -> {

                        } );
                termsBuilder.create ( ).show ( );
                break;
            case R.id.item_licenses:
                DialogLicenses dialogLicenses = new DialogLicenses ( );
                dialogLicenses.show ( getSupportFragmentManager ( ), "licenses dialog" );
                break;
            default:
                break;
        }
        return false;
    }
}