package com.example.deliveryboy.Repository;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Apis.ProductsApi;
import com.example.deliveryboy.Database.DatabaseInstance;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.Responses.LocalPriceAndQuantity;
import com.example.deliveryboy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemandeChargRepository {
    Executor executor;

    public DemandeChargRepository() {
        executor = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<Boolean> getApiProductsAndInsertLocally(Context context){
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();

        RetrofitClientInstance.getRetrofitClient().create(ProductsApi.class).getProducts(SessionManager.getInstance().getToken(context))
                .clone().enqueue(new Callback<List<Produit>>() {
                    @Override
                    public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                        if(response.isSuccessful()){

                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    boolean isProductsDeleted;

                                    try {
                                        DatabaseInstance.getInstance(context).demadesChargDao().deleteAllProducts();
                                        isProductsDeleted = true;
                                    }catch (Exception e){
                                        isProductsDeleted = false;

                                    }

                                    if(isProductsDeleted){
                                        try {
                                            DatabaseInstance.getInstance(context).demadesChargDao().insertAllProducts(response.body());




                                            /*

                                            for(Produit produit : response.body()){
                                                Log.i("ENTERRRRRRRRRRRRRRR", "STARTTTTTTTTTTT");

                                                for(ProduitCondition produitCondition : produit.getArticleConditionsList()){
                                                    String productBoId = produit.getBoId();

                                                    produitCondition.setProduitBoId(productBoId);
                                                    DatabaseInstance.getInstance(context).demadesChargDao().insertProductCondition(produitCondition);

                                                }
                                            }
                                            Log.i("ENTERRRRRRRRRRRRRRR", "ENDDDDDDDDD");

                                             */







// Assuming response.body() returns a list of Produit objects
                                            List<Produit> produits = response.body();
                                            if (produits != null && !produits.isEmpty()) {

                                                Log.i("ENTERRRRRRRRRRRRRRR", "STARTTTTTTTTTTT");

                                                List<ProduitCondition> allProduitConditions = new ArrayList<>();

                                                for (Produit produit : produits) {
                                                    String productBoId = produit.getBoId();

                                                    for (ProduitCondition produitCondition : produit.getArticleConditionsList()) {
                                                        produitCondition.setProduitBoId(productBoId);
                                                        allProduitConditions.add(produitCondition);
                                                    }
                                                }

                                                DatabaseInstance.getInstance(context)
                                                        .demadesChargDao()
                                                        .insertAllProductConditions(allProduitConditions);

                                                Log.i("ENTERRRRRRRRRRRRRRR", "ENDDDDDDDDDDD");

                                            }



                                            resultLiveData.postValue(true);

                                        }catch (Exception e){
                                            resultLiveData.postValue(false);

                                        }

                                    }else {
                                        resultLiveData.postValue(false);

                                    }

                                }
                            });


                        }else {
                            resultLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Produit>> call, Throwable t) {

                        resultLiveData.postValue(false);

                    }
                });

        return resultLiveData;

    }

    public MutableLiveData<List<Produit>> getLocalProducts(Context context){

        MutableLiveData<List<Produit>> resultLiveData= new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    resultLiveData.postValue(DatabaseInstance.getInstance(context).demadesChargDao().getAllProducts());

                }catch (Exception e){
                    resultLiveData.postValue(null);
                }
            }
        });

        return resultLiveData;
    }


    public MutableLiveData<List<ProduitCondition>> getLocalProductConditionByProductBoId(Context context, String idBo){

        MutableLiveData<List<ProduitCondition>> listMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listMutableLiveData.postValue(DatabaseInstance.getInstance(context).demadesChargDao().getAllProductsConditionsByBoid(idBo));

                }catch (Exception e){
                    listMutableLiveData.postValue(null);
                }
            }
        });
        return listMutableLiveData;
    }


    public MutableLiveData<ProduitCondition> getLocalPriceByIdAndProductId(Context context, String productIddBo, String ecEnumere){

        MutableLiveData<ProduitCondition> listMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listMutableLiveData.postValue(DatabaseInstance.getInstance(context).demadesChargDao().getPriceById(productIddBo,ecEnumere));

                }catch (Exception e){
                    listMutableLiveData.postValue(null);
                }
            }
        });
        return listMutableLiveData;
    }
}