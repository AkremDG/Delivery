package com.example.deliveryboy.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Apis.ProductsApi;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Utils.SessionManager;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRepository {
    Executor executor;

    public ProductsRepository() {
        executor = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<Boolean> getApiProductsAndInsertLocally(Context context){
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();

        RetrofitClientInstance.getRetrofitClient().create(ProductsApi.class).getProducts(SessionManager.getInstance().getToken(context))
                .clone().enqueue(new Callback<List<Produit>>() {
                    @Override
                    public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                        if(response.isSuccessful()){

                            for(Produit produit : response.body()){
                                Log.i("PRODUIT", produit.toString());
                            }

                            resultLiveData.postValue(true);
                        }else {
                            Log.i("PRODUIT", response.message());

                            resultLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Produit>> call, Throwable t) {
                        Log.i("PRODUIT", t.getMessage());

                        resultLiveData.postValue(false);

                    }
                });

        return resultLiveData;

    }
}
