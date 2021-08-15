package com.example.groceryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    private static final String TAG = "Utils";

    private static int ID = 0;
    private static int ORDER_ID = 0;
    public static final String DATABASE_NAME = "fake_database";

    private final Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public void addItemToCart(int id) {
        Log.i ( TAG, "addItemToCart: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<Integer>> ( ) {
        }.getType ( );
        ArrayList<Integer> cartItems = gson.fromJson ( sharedPreferences.getString ( "cartItems", null ), type );
        if (cartItems == null) {
            cartItems = new ArrayList<> ( );
        }
        Objects.requireNonNull ( cartItems ).add ( id );
        SharedPreferences.Editor editor = sharedPreferences.edit ( );
        editor.putString ( "cartItems", gson.toJson ( cartItems ) );
        editor.apply ( );
    }

    public void updateRate(@NonNull GroceryItem groceryItem, int newRate) {
        Log.i ( TAG, "updateRate: started for item" + groceryItem.getName ( ) );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> groceryItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        if (groceryItems != null) {
            ArrayList<GroceryItem> newItems = new ArrayList<> ( );
            for (GroceryItem i : groceryItems) {
                if (i.getId ( ) == groceryItem.getId ( )) {
                    i.setRate ( newRate );
                }
                newItems.add ( i );
            }
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit ( );
            editor.putString ( "allItems", gson.toJson ( newItems ) );
            editor.apply ( );
        }
    }

    public boolean addReview(Review review) {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> groceryItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        if (groceryItems != null) {
            ArrayList<GroceryItem> newItems = new ArrayList<> ( );
            for (GroceryItem groceryItem : groceryItems) {
                if (groceryItem.getId ( ) == review.getGroceryItemId ( )) {
                    ArrayList<Review> reviews = groceryItem.getReviewArrayList ( );
                    reviews.add ( review );
                    groceryItem.setReviewArrayList ( reviews );
                }
                newItems.add ( groceryItem );
            }
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit ( );
            editor.putString ( "allItems", gson.toJson ( newItems ) );
            editor.apply ( );
            return true;
        }
        return false;
    }

    public ArrayList<Review> getReviewForItem(int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> groceryItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        if (groceryItems != null) {
            for (GroceryItem groceryItem : groceryItems) {
                if (groceryItem.getId ( ) == id) {
                    return groceryItem.getReviewArrayList ( );
                }
            }
        }
        return null;
    }

    public void initDatabase() {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> possibleItems = gson.fromJson ( sharedPreferences.getString ( "allItems", "" ), type );
        if (possibleItems == null) {
            initAllItems ( );
        }
//        initAllItems (  );
    }

    public ArrayList<GroceryItem> getAllItems() {
        Log.d ( TAG, "getAllItems: started" );
        Gson gson = new Gson ( );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, MODE_PRIVATE );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        return gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
    }

    private void initAllItems() {
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ( );
        Gson gson = new Gson ( );
        ArrayList<GroceryItem> allItems = new ArrayList<> ( );
        GroceryItem iceCream = new GroceryItem ( "Ice cream", "produced of fresh milk", "https://bloximages.newyork1.vip.townnews.com/virginislandsdailynews.com/content/tncms/assets/v3/editorial/c/f9/cf961d35-5e81-58c5-ae8b-63c27b2020ef/5b57bcd072ed3.image.jpg", "food", 15, 2.5 );
        allItems.add ( new GroceryItem ( "Cheese", "Best cheese possible", "https://www.culturesforhealth.com/learn/wp-content/uploads/2019/06/shutterstock_343034981.jpg", "food", 3, 4.45 ) );
        allItems.add ( new GroceryItem ( "Cucumber", "it is fresh", "https://st.depositphotos.com/1000350/1936/i/600/depositphotos_19364449-stock-photo-cucumber-and-slices-isolated-over.jpg", "vegetables", 10, 0.8 ) );
        allItems.add ( new GroceryItem ( "Coca cola", "it is a tasty drink", "https://www.myamericanmarket.com/873-large_default/coca-cola-classic.jpg", "drinks", 100, 1 ) );
        allItems.add ( new GroceryItem ( "Spaghetti", "it is an easy to cook meal", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/barilla-rotini-pasta-1527694739.jpg", "food", 16, 2.5 ) );
        allItems.add ( new GroceryItem ( "Chicken nugget", "usually enough for 4 person", "https://www.momontimeout.com/wp-content/uploads/2021/02/chicken-nuggets-square.jpg", "food", 15, 10.8 ) );
        allItems.add ( new GroceryItem ( "Clear Shampoo", "you won't experience hair fall with this", "https://100comments.com/wp-content/uploads/2018/02/Untitled-10-3.jpg", "hygiene", 42, 12.3 ) );
        allItems.add ( new GroceryItem ( "Axe body spray", "is hot and sweaty? not any more", "https://pics.drugstore.com/prodimg/519930/900.jpg", "hygiene", 9, 8.5 ) );
        allItems.add ( new GroceryItem ( "Kleenex", "soft and famous!", "https://images-na.ssl-images-amazon.com/images/I/91ZyGoGBMAL._SY355_.jpg", "hygiene", 12, 3.2 ) );
        allItems.add ( iceCream );
        String finalString = gson.toJson ( allItems );
        editor.putString ( "allItems", finalString );
        editor.apply ( );
    }

    public ArrayList<GroceryItem> searchForItem(String text) {
        Log.i ( TAG, "searchForItem: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        ArrayList<GroceryItem> searchItems = new ArrayList<> ( );
        if (allItems != null) {
            for (GroceryItem item : allItems) {
                if (item.getName ( ).equalsIgnoreCase ( text )) {
                    searchItems.add ( item );
                }
                String[] splittedString = item.getName ( ).split ( " " );
                for (String s : splittedString) {
                    if (s.equalsIgnoreCase ( text )) {
                        boolean doesExist = false;
                        for (GroceryItem searchItem : searchItems) {
                            if (searchItem.equals ( item )) {
                                doesExist = true;
                            }
                        }
                        if (!doesExist) {
                            searchItems.add ( item );
                        }
                    }
                }
            }
        }
        return searchItems;
    }

    public ArrayList<String> getThreeCategories() {
        Log.i ( TAG, "getThreeCategories: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        ArrayList<String> categories = new ArrayList<> ( );
        if (allItems != null) {
            for (int i = 0; i < allItems.size ( ); i++) {
                if (categories.size ( ) < 3) {
                    boolean doesExist = false;
                    for (String s : categories) {
                        if (allItems.get ( i ).getCategory ( ).equals ( s )) {
                            doesExist = true;
                        }
                    }
                    if (!doesExist) {
                        categories.add ( allItems.get ( i ).getCategory ( ) );
                    }
                }
            }
        }
        return categories;
    }

    public ArrayList<String> getAllCategories() {
        Log.d ( TAG, "getAllCategories: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        ArrayList<String> categories = new ArrayList<> ( );
        if (allItems != null) {
            for (int i = 0; i < allItems.size ( ); i++) {
                boolean doesExist = false;
                for (String s : categories) {
                    if (allItems.get ( i ).getCategory ( ).equals ( s )) {
                        doesExist = true;
                    }
                }
                if (!doesExist) {
                    categories.add ( allItems.get ( i ).getCategory ( ) );
                }
            }
        }
        return categories;
    }

    public ArrayList<GroceryItem> getItemsByCategory(String category) {
        Log.d ( TAG, "getItemsByCategory: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        ArrayList<GroceryItem> newItems = new ArrayList<> ( );
        if (allItems != null) {
            for (GroceryItem item : allItems) {
                if (item.getCategory ( ).equals ( category )) {
                    newItems.add ( item );
                }
            }
        }
        return newItems;
    }

    public ArrayList<Integer> getCartItems() {
        Log.d ( TAG, "getCartItems: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<Integer>> ( ) {
        }.getType ( );
        return gson.fromJson ( sharedPreferences.getString ( "cartItems", null ), type );
    }

    public ArrayList<GroceryItem> getItemsById(ArrayList<Integer> ids) {
        Log.i ( TAG, "getItemsById: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        ArrayList<GroceryItem> resultItems = new ArrayList<> ( );
        for (int id : ids) {
            if (allItems != null) {
                for (GroceryItem item : allItems) {
                    if (item.getId ( ) == id) {
                        resultItems.add ( item );
                    }
                }
            }
        }
        return resultItems;
    }

    public ArrayList<Integer> deleteCartItem(GroceryItem item) {
        Log.i ( TAG, "deleteCartItem: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<Integer>> ( ) {
        }.getType ( );
        ArrayList<Integer> cartItems = gson.fromJson ( sharedPreferences.getString ( "cartItems", null ), type );
        ArrayList<Integer> newItems = new ArrayList<> ( );
        if (cartItems != null) {
            for (int i : cartItems) {
                if (item.getId ( ) != i) {
                    newItems.add ( i );
                }
            }
            SharedPreferences.Editor editor = sharedPreferences.edit ( );
            editor.putString ( "cartItems", gson.toJson ( newItems ) );
            editor.apply ( );
        }
        return newItems;
    }

    public void removeCartItems() {
        Log.d ( TAG, "removeCartItems: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit ( );
        editor.remove ( "cartItems" );
        editor.apply ( );
    }

    public void addPopularityPoint(ArrayList<Integer> items) {
        Log.d ( TAG, "addPopularityPoint: started" );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        ArrayList<GroceryItem> newItems = new ArrayList<> ( );
        for (GroceryItem i : allItems) {
            boolean doesExist = false;
            for (int j : items) {
                if (i.getId ( ) == j) {
                    doesExist = true;
                }
            }
            if (doesExist) {
                i.setPopularityPoint ( i.getPopularityPoint ( ) + 1 );
            }
            newItems.add ( i );
        }
        SharedPreferences.Editor editor = sharedPreferences.edit ( );
        editor.putString ( "allItems", gson.toJson ( newItems ) );
        editor.apply ( );
    }

    public void increaseUserPoint(GroceryItem item, int points) {
        Log.d ( TAG, "increaseUserPoint: started to increase user point for: " + item.getName ( ) + " for " + points );
        SharedPreferences sharedPreferences = context.getSharedPreferences ( DATABASE_NAME, Context.MODE_PRIVATE );
        Gson gson = new Gson ( );
        Type type = new TypeToken<ArrayList<GroceryItem>> ( ) {
        }.getType ( );
        ArrayList<GroceryItem> allItems = gson.fromJson ( sharedPreferences.getString ( "allItems", null ), type );
        if (allItems != null) {
            ArrayList<GroceryItem> newItems = new ArrayList<> ( );
            for (GroceryItem i : allItems) {
                if (item.getId ( ) == i.getId ( )) {
                    i.setUserPoint ( i.getUserPoint ( ) + points );
                }
                newItems.add ( i );
            }
            SharedPreferences.Editor editor = sharedPreferences.edit ( );
            editor.putString ( "allItems", gson.toJson ( newItems ) );
            editor.apply ( );
        }
    }
}