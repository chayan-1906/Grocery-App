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

public class FailedPaymentFragment extends Fragment {

    private static final String TAG = "FailedPaymentFragment";

    private Button btnGoBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_failure_payment, container, false );
        btnGoBack = (Button) view.findViewById ( R.id.btnGoBack );
        btnGoBack.setOnClickListener ( v -> {
            Intent intent = new Intent ( getActivity ( ), MainActivity.class );
            intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity ( intent );
        } );
        return view;
    }
}