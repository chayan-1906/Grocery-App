package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.groceryapp.R;

import java.util.ArrayList;

public class SuccessfulPaymentFragment extends Fragment {

    private static final String TAG = "SuccessfulPaymentFragment";

    private Button btnGoBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_success_payment, container, false );
        Utils utils = new Utils ( getActivity ( ) );
        utils.removeCartItems ( );
        Bundle bundle = getArguments ( );
        try {
            Order order = bundle.getParcelable ( "order" );
            ArrayList<Integer> itemIds = order.getIntegerArrayList ( );
            utils.addPopularityPoint ( itemIds );
            ArrayList<GroceryItem> items = utils.getItemsById ( itemIds );
            for (GroceryItem item : items) {
                ArrayList<GroceryItem> sameCategory;
                sameCategory = utils.getItemsByCategory ( item.getCategory ( ) );
                for (GroceryItem j : sameCategory) {
                    utils.increaseUserPoint ( j, 4 );
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace ( );
        }

        btnGoBack = (Button) view.findViewById ( R.id.btnGoBack );

        btnGoBack.setOnClickListener ( v -> {
            Intent intent = new Intent ( getActivity ( ), MainActivity.class );
            intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity ( intent );
        } );

        return view;
    }
}