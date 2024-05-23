package com.example.deliveryboy.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {


    public static Retrofit getRetrofitClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d56d-41-230-217-E.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;


    }

}
