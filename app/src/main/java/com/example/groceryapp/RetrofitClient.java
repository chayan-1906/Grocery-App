package com.example.groceryapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitClient {
    @POST("posts")
    Call<Order> goToFakePayment(@Body Order order);
}