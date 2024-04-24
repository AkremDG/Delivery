package com.example.deliveryboy.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Apis.AuthApi;
import com.example.deliveryboy.Model.Requests.AuthRequest;
import com.example.deliveryboy.Model.Responses.AuthResponse;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.Utils.SessionManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    public AuthRepository() {
    }

    public MutableLiveData<String> authUser(Context context, AuthRequest authRequest){
        
        MutableLiveData<String> authUserLiveData = new MutableLiveData<>();

        AuthApi authRepository = RetrofitClientInstance.getRetrofitClient().create(AuthApi.class);
        authRepository.authUser(authRequest).clone().enqueue(new Callback<AuthResponse>() {
           @Override
           public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {


               if(response.isSuccessful()){



                       Calendar calendar = Calendar.getInstance();
                       calendar.add(Calendar.DAY_OF_MONTH,1);

                       SessionManager.getInstance().setToken(context,response.body().getToken());
                       SessionManager.getInstance().setExpireDate(context,calendar);
                       SessionManager.getInstance().setUSER_ID(context,response.body().getUser().getId());
                       SessionManager.getInstance().setROLE_ID(context,response.body().getUser().getRoleId());


                       authUserLiveData.postValue("success");

                   }

               else {
                   authUserLiveData.postValue("Échec d'authentification");

               }


           }

           @Override
           public void onFailure(Call<AuthResponse> call, Throwable t) {
               authUserLiveData.postValue("Échec de l'authentification. Veuillez vérifier votre connexion Internet et réessayer.");

           }
       });
        return authUserLiveData;
    }

}
