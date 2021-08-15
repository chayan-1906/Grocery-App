package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.groceryapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddReviewDialog extends DialogFragment {

    private static final String TAG = "AddReviewDialog";

    // Android UI...
    private TextView textViewReviewExplain;
    private TextView textViewItemName;
    private TextInputLayout textInputLayoutUserName;
    private TextInputLayout textInputLayoutReview;
    private TextView textViewWarning;
    private Button btnAddReview;

    private int itemId = 0;

    private AddReview addReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity ( ).getLayoutInflater ( ).inflate ( R.layout.dialog_add_review, null );
        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity ( ) );
        builder.setTitle ( "Add Review" );
        builder.setView ( view );

        textViewReviewExplain = view.findViewById ( R.id.textViewReviewExplain );
        textViewItemName = view.findViewById ( R.id.textViewItemName );
        textInputLayoutUserName = view.findViewById ( R.id.textInputLayoutUserName );
        textInputLayoutReview = view.findViewById ( R.id.textInputLayoutReview );
        textViewWarning = view.findViewById ( R.id.textViewWarning );
        btnAddReview = view.findViewById ( R.id.btnAddReview );

        Bundle bundle = getArguments ( );
        try {
            GroceryItem groceryItem = Objects.requireNonNull ( bundle ).getParcelable ( "item" );
            textViewItemName.setText ( groceryItem.getName ( ) );
            this.itemId = groceryItem.getId ( );
        } catch (Exception e) {
            e.printStackTrace ( );
        }

        btnAddReview.setOnClickListener ( v -> addReview ( ) );
        return builder.create ( );
    }

    private boolean validateData() {
        Log.d ( TAG, "validateData: started" );
        if (textInputLayoutUserName.getEditText ( ).getText ( ).toString ( ).equals ( "" )) {
            return false;
        } else if (textViewReviewExplain.getText ( ).toString ( ).equals ( "" )) {
            return false;
        }
        return true;
    }

    private void addReview() {
        if (validateData ( )) {
            String userName = Objects.requireNonNull ( textInputLayoutUserName.getEditText ( ) ).getText ( ).toString ( ).trim ( );
            String reviewText = Objects.requireNonNull ( textInputLayoutReview.getEditText ( ) ).getText ( ).toString ( ).trim ( );
            String date = getCurrentDate ( );
            Review review = new Review ( itemId, userName, date, reviewText );
            try {
                addReview = (AddReview) getActivity ( );
                Objects.requireNonNull ( addReview ).onAddReviewResult ( review );
                dismiss ( );
            } catch (ClassCastException e) {
                e.printStackTrace ( );
            }
        } else {
            textViewWarning.setVisibility ( View.VISIBLE );
        }
    }

    private String getCurrentDate() {
        Date date = Calendar.getInstance ( ).getTime ( );
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "dd-MM-yyyy" );
        return simpleDateFormat.format ( date );
    }
}
