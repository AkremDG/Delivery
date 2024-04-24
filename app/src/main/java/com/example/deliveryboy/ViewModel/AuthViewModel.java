package com.example.deliveryboy.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Model.Requests.AuthRequest;
import com.example.deliveryboy.Repository.AuthRepository;

public class AuthViewModel {

    AuthRepository authRepository;

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    public MutableLiveData<String> authUser(Context context, AuthRequest authRequest){
        return authRepository.authUser(context,authRequest);
    }
}
