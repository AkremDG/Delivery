package com.example.deliveryboy.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {


    public static Retrofit getRetrofitClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6dd7-197-2-86-186.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
