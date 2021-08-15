package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryapp.R;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReview {

    private static final String TAG = "GroceryItemActivity";

    private Button btnAddToCart;
    private NestedScrollView nestedScrollViewGroceryItem;
    private TextView textViewName;
    private TextView textViewPrice;
    private ImageView imageViewItem;
    private TextView textViewAvailability;
    private TextView textViewDescription;
    private RelativeLayout relativeLayoutRating;
    private RelativeLayout firstStarRelativeLayout;
    private ImageView imageViewFirstEmptyStar;
    private ImageView imageViewFirstFilledStar;
    private ImageView imageViewSecondEmptyStar;
    private ImageView imageViewSecondFilledStar;
    private ImageView imageViewThirdEmptyStar;
    private ImageView imageViewThirdFilledStar;
    private TextView textViewReviewExplained;
    private RelativeLayout addReviewRelativeLayout;
    private ImageView imageViewAddReview;
    private TextView textViewAddReview;
    private RecyclerView recyclerViewReviews;

    private GroceryItem incomingGroceryItem;
    private ReviewsAdapter reviewsAdapter;
    private Utils utils;

    private TrackUserTime service;
    private boolean isBound = false;

    private int currentRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_grocery_item );

        utils = new Utils ( this );

        btnAddToCart = findViewById ( R.id.btnAddToCart );
        nestedScrollViewGroceryItem = findViewById ( R.id.nestedScrollViewGroceryItem );
        textViewName = findViewById ( R.id.textViewName );
        textViewPrice = findViewById ( R.id.textViewPrice );
        imageViewItem = findViewById ( R.id.imageViewItem );
        textViewAvailability = findViewById ( R.id.textViewAvailability );
        textViewDescription = findViewById ( R.id.textViewDescription );
        relativeLayoutRating = findViewById ( R.id.relativeLayoutRating );
        firstStarRelativeLayout = findViewById ( R.id.firstStarRelativeLayout );
        imageViewFirstEmptyStar = findViewById ( R.id.imageViewFirstEmptyStar );
        imageViewFirstFilledStar = findViewById ( R.id.imageViewFirstFilledStar );
        imageViewSecondEmptyStar = findViewById ( R.id.imageViewSecondEmptyStar );
        imageViewSecondFilledStar = findViewById ( R.id.imageViewSecondFilledStar );
        imageViewThirdEmptyStar = findViewById ( R.id.imageViewThirdEmptyStar );
        imageViewThirdFilledStar = findViewById ( R.id.imageViewThirdFilledStar );
        textViewReviewExplained = findViewById ( R.id.textViewReviewExplained );
        addReviewRelativeLayout = findViewById ( R.id.addReviewRelativeLayout );
        imageViewAddReview = findViewById ( R.id.imageViewAddReview );
        textViewAddReview = findViewById ( R.id.textViewAddReview );
        recyclerViewReviews = findViewById ( R.id.recyclerViewReviews );

        Intent intent = getIntent ( );
        try {
            incomingGroceryItem = intent.getParcelableExtra ( "item" );
            this.currentRate = incomingGroceryItem.getRate ( );
            changeVisibility ( currentRate );
            setViewsValues ( );
            utils.increaseUserPoint ( incomingGroceryItem, 1 );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }

    /**
     * responsible for setting the initial values for views
     */
    @SuppressLint("SetTextI18n")
    private void setViewsValues() {
        textViewName.setText ( incomingGroceryItem.getName ( ) );
        textViewPrice.setText ( incomingGroceryItem.getPrice ( ) + "₹" );
        textViewAvailability.setText ( incomingGroceryItem.getAvailableAmount ( ) + " number(s) available" );
        textViewDescription.setText ( incomingGroceryItem.getDescription ( ) );
        Glide.with ( this ).asBitmap ( ).load ( incomingGroceryItem.getImageUrl ( ) ).into ( imageViewItem );
        btnAddToCart.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                // TODO: Add item to cart
                utils.addItemToCart ( incomingGroceryItem.getId ( ) );
                Intent intentCartActivity = new Intent ( GroceryItemActivity.this, CartActivity.class );
                intentCartActivity.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity ( intentCartActivity );
            }
        } );

        // TODO: handle the star situation
        handleStarSituation ( );
        reviewsAdapter = new ReviewsAdapter ( );
        recyclerViewReviews.setAdapter ( reviewsAdapter );
        recyclerViewReviews.setLayoutManager ( new LinearLayoutManager ( this ) );

        ArrayList<Review> reviewArrayList = utils.getReviewForItem ( incomingGroceryItem.getId ( ) );
        if (reviewArrayList != null) {
            reviewsAdapter.setReviewArrayList ( reviewArrayList );
        }

        addReviewRelativeLayout.setOnClickListener ( v -> {
            // TODO: show dialog
            AddReviewDialog addReviewDialog = new AddReviewDialog ( );
            Bundle bundle = new Bundle ( );
            bundle.putParcelable ( "item", incomingGroceryItem );
            addReviewDialog.setArguments ( bundle );
            addReviewDialog.show ( getSupportFragmentManager ( ), "add review dialog" );
        } );

    }

    private void handleStarSituation() {
        Log.i ( TAG, "handleStarSituation: started" );

        imageViewFirstEmptyStar.setOnClickListener ( view -> {
            if (checkIfRateHasChanged ( 1 )) {
                updateDatabase ( 1 );
                changeVisibility ( 1 );
                changeUserPoint ( 1 );
            }
        } );

        imageViewSecondEmptyStar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (checkIfRateHasChanged ( 2 )) {
                    updateDatabase ( 2 );
                    changeVisibility ( 2 );
                    changeUserPoint ( 2 );
                }
            }
        } );

        imageViewThirdEmptyStar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (checkIfRateHasChanged ( 3 )) {
                    updateDatabase ( 3 );
                    changeVisibility ( 3 );
                    changeUserPoint ( 3 );
                }
            }
        } );

        imageViewFirstFilledStar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (checkIfRateHasChanged ( 1 )) {
                    updateDatabase ( 1 );
                    changeVisibility ( 1 );
                    changeUserPoint ( 1 );
                }
            }
        } );

        imageViewSecondFilledStar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (checkIfRateHasChanged ( 2 )) {
                    updateDatabase ( 2 );
                    changeVisibility ( 2 );
                    changeUserPoint ( 2 );
                }
            }
        } );

        imageViewThirdFilledStar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (checkIfRateHasChanged ( 3 )) {
                    updateDatabase ( 3 );
                    changeVisibility ( 3 );
                    changeUserPoint ( 3 );
                }
            }
        } );
    }

    private void changeUserPoint(int stars) {
        Log.d ( TAG, "changeUserPoint: started" );
        utils.increaseUserPoint ( incomingGroceryItem, (stars - currentRate) * 2 );
    }

    private void changeVisibility(int newRate) {
        Log.i ( TAG, "changeVisibility: started" );
        switch (newRate) {
            case 0:
                imageViewFirstFilledStar.setVisibility ( View.GONE );
                imageViewSecondFilledStar.setVisibility ( View.GONE );
                imageViewThirdFilledStar.setVisibility ( View.GONE );
                imageViewFirstEmptyStar.setVisibility ( View.VISIBLE );
                imageViewSecondEmptyStar.setVisibility ( View.VISIBLE );
                imageViewThirdEmptyStar.setVisibility ( View.VISIBLE );
                break;
            case 1:
                imageViewFirstFilledStar.setVisibility ( View.VISIBLE );
                imageViewSecondFilledStar.setVisibility ( View.GONE );
                imageViewThirdFilledStar.setVisibility ( View.GONE );
                imageViewFirstEmptyStar.setVisibility ( View.GONE );
                imageViewSecondEmptyStar.setVisibility ( View.VISIBLE );
                imageViewThirdEmptyStar.setVisibility ( View.VISIBLE );
                break;
            case 2:
                imageViewFirstFilledStar.setVisibility ( View.VISIBLE );
                imageViewSecondFilledStar.setVisibility ( View.VISIBLE );
                imageViewThirdFilledStar.setVisibility ( View.GONE );
                imageViewFirstEmptyStar.setVisibility ( View.GONE );
                imageViewSecondEmptyStar.setVisibility ( View.GONE );
                imageViewThirdEmptyStar.setVisibility ( View.VISIBLE );
                break;
            case 3:
                imageViewFirstFilledStar.setVisibility ( View.VISIBLE );
                imageViewSecondFilledStar.setVisibility ( View.VISIBLE );
                imageViewThirdFilledStar.setVisibility ( View.VISIBLE );
                imageViewFirstEmptyStar.setVisibility ( View.GONE );
                imageViewSecondEmptyStar.setVisibility ( View.GONE );
                imageViewThirdEmptyStar.setVisibility ( View.GONE );
                break;
            default:
                imageViewFirstFilledStar.setVisibility ( View.GONE );
                imageViewSecondFilledStar.setVisibility ( View.GONE );
                imageViewThirdFilledStar.setVisibility ( View.GONE );
                imageViewFirstEmptyStar.setVisibility ( View.VISIBLE );
                imageViewSecondEmptyStar.setVisibility ( View.VISIBLE );
                imageViewThirdEmptyStar.setVisibility ( View.VISIBLE );
        }
    }

    private void updateDatabase(int newRate) {
        Log.i ( TAG, "updateDatabase: started" );
        utils.updateRate ( incomingGroceryItem, newRate );
    }

    private boolean checkIfRateHasChanged(int newRate) {
        Log.i ( TAG, "checkIfRateHasChanged: started" );
        if (newRate == currentRate)
            return false;
        else
            return true;
    }

    @Override
    public void onAddReviewResult(Review review) {
        utils.addReview ( review );
        utils.increaseUserPoint ( incomingGroceryItem, 3 );
        ArrayList<Review> reviewArrayList = utils.getReviewForItem ( review.getGroceryItemId ( ) );
        if (reviewArrayList != null) {
            reviewsAdapter.setReviewArrayList ( reviewArrayList );
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection ( ) {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TrackUserTime.LocalBinder localBinder = (TrackUserTime.LocalBinder) iBinder;
            service = localBinder.getService ( );
            isBound = true;
            service.setGroceryItem ( incomingGroceryItem );
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart ( );
        Intent intentTrackUserTime = new Intent ( GroceryItemActivity.this, TrackUserTime.class );
        bindService ( intentTrackUserTime, serviceConnection, BIND_AUTO_CREATE );
    }

    @Override
    protected void onStop() {
        super.onStop ( );
        if (isBound) {
            unbindService ( serviceConnection );
        }
    }
}