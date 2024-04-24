package com.example.deliveryboy.Apis;

import com.example.deliveryboy.Model.Mission;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface MissionsApi {

    @GET("/api/plandt/parv")
    Call<List<Mission>> getMissions(@Header("Authorization") String auth);
}
