package com.example.deliveryboy.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {


    public static Retrofit getRetrofitClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://c98e-102-156-61-220.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
