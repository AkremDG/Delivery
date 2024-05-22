package com.example.deliveryboy.Repository;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Apis.DemandsApi;
import com.example.deliveryboy.Apis.ProductsApi;
import com.example.deliveryboy.Database.DatabaseInstance;
import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.Requests.PointagePostBody;
import com.example.deliveryboy.Model.Responses.CmdLigne;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.Model.Responses.LocalPriceAndQuantity;
import com.example.deliveryboy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

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



    public MutableLiveData<Boolean> sendDemandApi(Context context, Demande demande){
        MutableLiveData<Boolean> returnedResult = new MutableLiveData<>();

        RetrofitClientInstance.getRetrofitClient().create(DemandsApi.class).postDemande(demande, SessionManager.getInstance().getToken(context
        )).clone().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){

                    Log.i("sendDemandApi","success"+ response.message());

                    returnedResult.postValue(true);
                }else {
                    returnedResult.postValue(false);
                    Log.i("sendDemandApi","FAIL"+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                returnedResult.postValue(false);
                Log.i("sendDemandApi", t.getMessage());

            }
        });
        return returnedResult;

    }


    public MutableLiveData<Boolean> getAllDemandesApiAndInsertLocally(Context context){
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();

        RetrofitClientInstance.getRetrofitClient().create(DemandsApi.class).getAllDemandes(SessionManager.getInstance().getToken(context)).clone()
                .enqueue(new Callback<List<GETDemandeChargementRes>>() {
                    @Override
                    public void onResponse(Call<List<GETDemandeChargementRes>> call, Response<List<GETDemandeChargementRes>> response) {
                        if(response.isSuccessful()){


                            executor.execute(new Runnable() {
                                @Override
                                public void run() {


                            boolean isOldDemandesDeleted;

                            try {
                                DatabaseInstance.getInstance(context).demadesChargDao().deleteAllDemandes();
                                Log.i("DELETEEEEEEEEEEEEEEE","FAILLLLLLL");

                                isOldDemandesDeleted = true;
                            }catch (Exception e){
                                Log.i("DELETEEEEEEEEEEEEEEE",e.getMessage());

                                isOldDemandesDeleted = false;

                            }

                            if(isOldDemandesDeleted){

                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            DatabaseInstance.getInstance(context).demadesChargDao().insertAllDemandes(response.body());
                                            DatabaseInstance.getInstance(context).demadesChargDao().deleteAllCmdLignes();



                                            List<GETDemandeChargementRes> demandes = response.body();
                                            if (demandes != null && !demandes.isEmpty()) {

                                                Log.i("STARTTTTINSERTION", "STARTTTTTTTTTTT");

                                                List<CmdLigne> allCmdLignes = new ArrayList<>();

                                                for (GETDemandeChargementRes demande : demandes) {

                                                    int demandeBoId = demande.getBoId();

                                                    for (CmdLigne cmdLigne : demande.getCmdLigneList()) {
                                                        cmdLigne.setDemandeBoId(demandeBoId);
                                                        allCmdLignes.add(cmdLigne);
                                                    }
                                                }

                                                DatabaseInstance.getInstance(context)
                                                        .demadesChargDao()
                                                        .insertAllCmdLignes(allCmdLignes);

                                                Log.i("STARTTTTINSERTION", "ENDDDDDDDDDDD");

                                            }


                                            resultLiveData.postValue(true);
                                            Log.i("INSERTTTTTTTTTTTT","SUCCESSSSS");

                                        }catch (Exception e){
                                            resultLiveData.postValue(false);
                                            Log.i("INSERTTTTTTTTTTTT", e.getMessage());

                                        }

                                    }
                                });


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
                    public void onFailure(Call<List<GETDemandeChargementRes>> call, Throwable t) {

                        resultLiveData.postValue(false);
                        Log.i("GEEEEEEEEET", t.getMessage());


                    }
                });

        return resultLiveData;

    }


    public MutableLiveData<List<GETDemandeChargementRes>> getAllLocalDemandes(Context context){

        MutableLiveData<List<GETDemandeChargementRes>> listMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listMutableLiveData.postValue(DatabaseInstance.getInstance(context).demadesChargDao().getAllLocalDemandes());
                    Log.i("GETTTTTTTALLLLLL", "TRUEEEEEEEE: ");

                }catch (Exception e){
                    listMutableLiveData.postValue(null);
                    Log.i("GETTTTTTTALLLLLL", e.getMessage());
                }
            }
        });
        return listMutableLiveData;
    }




    public MutableLiveData<List<GETDemandeChargementRes>> getAllLocalDemandesClotures(Context context){

        MutableLiveData<List<GETDemandeChargementRes>> listMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listMutableLiveData.postValue(DatabaseInstance.getInstance(context).demadesChargDao().getAllLocalCloturesDemandes());
                    Log.i("GETTTTTTTALLLLLL", "TRUEEEEEEEE: ");

                }catch (Exception e){
                    listMutableLiveData.postValue(null);
                    Log.i("GETTTTTTTALLLLLL", e.getMessage());
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<List<CmdLigne>> getAllLocalCmdLignes(Context context, int boId){

        MutableLiveData<List<CmdLigne>> listMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listMutableLiveData.postValue(DatabaseInstance.getInstance(context).demadesChargDao().getAllCmdLignes(boId));

                    Log.i("CMDLIGNEEEEEEEEEEEEEEE", "TRUEEEEEEEE: ");

                }catch (Exception e){
                    listMutableLiveData.postValue(null);
                    Log.i("CMDLIGNEEEEEEEEEEEEEEE", e.getMessage());
                }
            }
        });

        return listMutableLiveData;
    }


    public MutableLiveData<Boolean> sendPointageArticles(Context context, PointagePostBody pointagePostBody){

        MutableLiveData<Boolean> returnedResult = new MutableLiveData<>();

        RetrofitClientInstance.getRetrofitClient().create(DemandsApi.class).
                postPointageArticles(pointagePostBody, SessionManager.getInstance().getToken(context
        )).clone().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    Log.i("SENDDDDDDDDDPOINTAGE","success"+ response.message());

                    returnedResult.postValue(true);
                }else {
                    returnedResult.postValue(false);
                    Log.i("SENDDDDDDDDDPOINTAGE","FAIL"+ response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnedResult.postValue(false);
                Log.i("SENDDDDDDDDDPOINTAGE", t.getMessage());

            }
        });
        return returnedResult;

    }


}
