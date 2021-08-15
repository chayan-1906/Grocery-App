package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapp.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private static final String TAG = "ReviewsAdapter";

    private ArrayList<Review> reviewArrayList = new ArrayList<> (  );

    public ReviewsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.review_adapter_list_item, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewUsername.setText ( reviewArrayList.get ( position ).getUserName () );
        holder.textViewReview.setText ( reviewArrayList.get ( position ).getText () );
        holder.textViewDate.setText ( reviewArrayList.get ( position ).getDate () );

    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsername;
        private TextView textViewReview;
        private TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );

            textViewUsername = itemView.findViewById ( R.id.textViewUsername );
            textViewReview = itemView.findViewById ( R.id.textViewReview );
            textViewDate = itemView.findViewById ( R.id.textViewDate );
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
        notifyDataSetChanged ();
    }
}
