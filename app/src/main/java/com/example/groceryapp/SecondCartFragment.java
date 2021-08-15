package com.example.groceryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.groceryapp.R;

public class SecondCartFragment extends Fragment {

    private static final String TAG = "SecondCartFragment";

    private RelativeLayout parent;
    private NestedScrollView nestedScrollView;
    private RelativeLayout addressRelativeLayout;
    private EditText editTextAddress;
    private RelativeLayout zipCodeRelativeLayout;
    private EditText editTextZipCode;
    private RelativeLayout phoneNumberRelativeLayout;
    private EditText editTextPhoneNumber;
    private RelativeLayout emailRelativeLayout;
    private EditText editTextEmail;
    private Button btnBack;
    private Button btnNext;

    private Order incomingOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_second_cart, container, false );

        parent = view.findViewById ( R.id.parent );
        nestedScrollView = view.findViewById ( R.id.nestedScrollView );
        addressRelativeLayout = view.findViewById ( R.id.addressRelativeLayout );
        editTextAddress = view.findViewById ( R.id.editTextAddress );
        zipCodeRelativeLayout = view.findViewById ( R.id.zipCodeRelativeLayout );
        editTextZipCode = view.findViewById ( R.id.editTextZipCode );
        phoneNumberRelativeLayout = view.findViewById ( R.id.phoneNumberRelativeLayout );
        editTextPhoneNumber = view.findViewById ( R.id.editTextPhoneNumber );
        emailRelativeLayout = view.findViewById ( R.id.emailRelativeLayout );
        editTextEmail = view.findViewById ( R.id.editTextEmail );
        btnBack = view.findViewById ( R.id.btnBack );
        btnNext = view.findViewById ( R.id.btnNext );

        Bundle bundle = getArguments ( );
        if (null != bundle) {
            incomingOrder = bundle.getParcelable ( "order" );
        }

        btnBack.setOnClickListener ( v -> getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( ).setCustomAnimations ( R.anim.out, R.anim.in ).replace ( R.id.fragment_container, new FirstCartFragment ( ) ).commit ( ) );

        btnNext.setOnClickListener ( v -> {
            if (validateData ( )) {
                passData ( );
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity ( ) )
                        .setTitle ( "Error" )
                        .setMessage ( "Please fill all of the fields" )
                        .setPositiveButton ( "Dismiss", new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        } );
                builder.create ( ).show ( );
            }
        } );
        initRelativeLayouts ( );
        return view;
    }

    private void passData() {
        Log.d ( TAG, "passData: started" );
        Bundle bundle = new Bundle ( );
        bundle.putParcelable ( "order", incomingOrder );
        ThirdCartFragment thirdCartFragment = new ThirdCartFragment ( );
        thirdCartFragment.setArguments ( bundle );
        getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( ).setCustomAnimations ( R.anim.in, R.anim.out ).replace ( R.id.fragment_container, thirdCartFragment ).commit ( );
    }

    private boolean validateData() {
        Log.d ( TAG, "validateData: started" );
        if (editTextAddress.getText ( ).toString ( ).equals ( "" )) {
            return false;
        } else if (editTextEmail.getText ( ).toString ( ).equals ( "" )) {
            return false;
        } else if (editTextZipCode.getText ( ).toString ( ).equals ( "" )) {
            return false;
        } else if (editTextPhoneNumber.getText ( ).toString ( ).equals ( "" )) {
            return false;
        }
        return true;
    }

    private void closeKeyboard() {
        View view = getActivity ( ).getCurrentFocus ( );
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity ( ).getSystemService ( Context.INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow ( view.getWindowToken ( ), 0 );
        }
    }

    private void initRelativeLayouts() {
        Log.d ( TAG, "initRelLayouts: started" );
        parent.setOnClickListener ( v -> closeKeyboard ( ) );
//        addressRelativeLayout.setOnClickListener ( v -> closeKeyboard ( ) );
//        emailRelativeLayout.setOnClickListener ( v -> closeKeyboard ( ) );
//        phoneNumberRelativeLayout.setOnClickListener ( v -> closeKeyboard ( ) );
//        zipCodeRelativeLayout.setOnClickListener ( v -> closeKeyboard ( ) );
//        nestedScrollView.setOnScrollChangeListener ( (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> closeKeyboard ( ) );
    }
}
