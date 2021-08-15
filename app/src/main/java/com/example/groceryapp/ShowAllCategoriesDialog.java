package com.example.groceryapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.groceryapp.R;

import java.util.ArrayList;

public class ShowAllCategoriesDialog extends DialogFragment {

    private static final String TAG = "ShowAllCategoriesDialog";

    private SelectCategory selectCategory;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity ( ).getLayoutInflater ( ).inflate ( R.layout.dialog_show_all_categories, null );
        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity ( ) )
                .setView ( view )
                .setTitle ( "All Categories" );
        ListView listView = view.findViewById ( R.id.categoriesListView );
        Utils utils = new Utils ( getActivity ( ) );
        final ArrayList<String> categories = utils.getAllCategories ( );
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<> ( getActivity ( ), android.R.layout.simple_list_item_1, categories );
        listView.setAdapter ( stringArrayAdapter );
        try {
            selectCategory = (SelectCategory) getActivity ( );
        } catch (ClassCastException e) {
            e.printStackTrace ( );
        }
        listView.setOnItemClickListener ( (parent, view1, position, id) -> selectCategory.onSelectCategoryResult ( categories.get ( position ) ) );
        return builder.create ( );
    }
}