package com.example.groceryapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GroceryItem implements Parcelable {

    private int id;     // for new items
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private int availableAmount;
    private double price;
    private int popularityPoint;    // for popular items
    private int userPoint;  // for suggested items
    private int rate;
    private ArrayList<Review> reviewArrayList;

    public GroceryItem(String name, String description, String imageUrl, String category, int availableAmount, double price) {
        this.id = Utils.getID ( );
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.availableAmount = availableAmount;
        this.price = price;
        this.popularityPoint = 0;
        this.userPoint = 0;
        this.rate = 0;
        this.reviewArrayList = new ArrayList<> ( );
    }


    protected GroceryItem(Parcel in) {
        id = in.readInt ( );
        name = in.readString ( );
        description = in.readString ( );
        imageUrl = in.readString ( );
        category = in.readString ( );
        availableAmount = in.readInt ( );
        price = in.readDouble ( );
        popularityPoint = in.readInt ( );
        userPoint = in.readInt ( );
        rate = in.readInt ( );
        reviewArrayList = in.createTypedArrayList ( Review.CREATOR );
    }

    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem> ( ) {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem ( in );
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[ size ];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ArrayList<Review> getReviewArrayList() {
        return reviewArrayList;
    }

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public String toString() {
        return "GroceryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", availableAmount=" + availableAmount +
                ", price=" + price +
                ", popularityPoint=" + popularityPoint +
                ", userPoint=" + userPoint +
                ", rate=" + rate +
                ", reviewArrayList=" + reviewArrayList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt ( id );
        parcel.writeString ( name );
        parcel.writeString ( description );
        parcel.writeString ( imageUrl );
        parcel.writeString ( category );
        parcel.writeInt ( availableAmount );
        parcel.writeDouble ( price );
        parcel.writeInt ( popularityPoint );
        parcel.writeInt ( userPoint );
        parcel.writeInt ( rate );
        parcel.writeTypedList ( reviewArrayList );
    }
}