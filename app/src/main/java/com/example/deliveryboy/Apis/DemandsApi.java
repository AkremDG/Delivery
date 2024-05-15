package com.example.deliveryboy.Apis;

import androidx.room.AutoMigration;

import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.DemandeProduitItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DemandsApi {

    @POST("/api/cmd/mobile")
   Call<Object> postDemande(@Body Demande demande, @Header("Authorization")String auth);


}
