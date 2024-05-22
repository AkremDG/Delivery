package com.example.deliveryboy.Apis;

import androidx.room.AutoMigration;

import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.DemandeProduitItem;
import com.example.deliveryboy.Model.Requests.PointagePostBody;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DemandsApi {

    @POST("/api/cmd/mobile")
   Call<Object> postDemande(@Body Demande demande, @Header("Authorization")String auth);

    @GET("/api/cmd/mobile")
    Call<List<GETDemandeChargementRes>> getAllDemandes(@Header("Authorization")String auth);


    @POST("/api/cmd/archive")
    Call<String> postPointageArticles(@Body PointagePostBody pointagePostBody, @Header("Authorization")String auth);

}
