package com.example.deliveryboy.Apis;

import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Requests.AuthRequest;
import com.example.deliveryboy.Model.Responses.AuthResponse;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.Utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/api/user/loginmobile")
    Call<AuthResponse> authUser(@Body AuthRequest authRequest);



}
