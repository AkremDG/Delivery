package com.example.deliveryboy.Apis;

import android.se.omapi.Session;

import com.example.deliveryboy.Model.Produit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProductsApi {


    @GET("/api/article/mobile")
    Call<List<Produit>> getProducts(@Header("Authorization")String auth);
}
