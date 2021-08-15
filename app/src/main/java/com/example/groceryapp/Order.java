package com.example.groceryapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable {

    private int id;
    private ArrayList<Integer> integerArrayList;
    private String address;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private String paymentMethod;
    private boolean success;
    private double totalPrice;

    public Order() {
    }

    public Order(ArrayList<Integer> groceryItems, String address, String email, String phoneNumber, String zipCode, String paymentMethod, boolean success, double totalPrice) {
        this.id = Utils.getOrderId ( );
        this.integerArrayList = groceryItems;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.paymentMethod = paymentMethod;
        this.success = success;
        this.totalPrice = totalPrice;
    }

    protected Order(Parcel in) {
        id = in.readInt ( );
        address = in.readString ( );
        email = in.readString ( );
        phoneNumber = in.readString ( );
        zipCode = in.readString ( );
        paymentMethod = in.readString ( );
        success = in.readByte ( ) != 0;
        totalPrice = in.readDouble ( );
    }

    public static final Creator<Order> CREATOR = new Creator<Order> ( ) {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order ( in );
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[ size ];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getIntegerArrayList() {
        return integerArrayList;
    }

    public void setIntegerArrayList(ArrayList<Integer> integerArrayList) {
        this.integerArrayList = integerArrayList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt ( id );
        parcel.writeString ( address );
        parcel.writeString ( email );
        parcel.writeString ( phoneNumber );
        parcel.writeString ( zipCode );
        parcel.writeString ( paymentMethod );
        parcel.writeByte ( (byte) (success ? 1 : 0) );
        parcel.writeDouble ( totalPrice );
    }
}