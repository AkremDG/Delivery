package com.example.deliveryboy.Repository;

import android.content.Context;
import android.se.omapi.Session;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Apis.MissionsApi;
import com.example.deliveryboy.Database.DatabaseInstance;
import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Region;
import com.example.deliveryboy.Utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionsRepository {
    Boolean isInserted = false;
    Boolean localInsertionRes=false;

    Executor executor;
    public MissionsRepository() {
        executor = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<Boolean> getApiMissionsAndInsertLocally(Context context){

        MutableLiveData<Boolean> isRefreshedLiveData = new MutableLiveData<>();

        MissionsApi missionsApi = RetrofitClientInstance.getRetrofitClient().create(MissionsApi.class);
        missionsApi.getMissions(SessionManager.getInstance().getToken(context)).clone().enqueue(new Callback<List<Mission>>() {
            @Override
            public void onResponse(Call<List<Mission>> call, Response<List<Mission>> response) {


                if(response.isSuccessful()){

                    if(response.body()!=null){

                        if(response.body().size()>0)
                        {

                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Boolean isDeleted = false;

                                    try {
                                        DatabaseInstance.getInstance(context).missionsDao().deleteAllMissions();
                                        try{

                                            DatabaseInstance.getInstance(context).missionsDao().deleteAllClientsMissions();

                                        }catch (Exception e){



                                        }

                                        isDeleted = true;

                                    }catch (Exception e){

                                        isDeleted = false;
                                    }


                                    if(isDeleted==true) {
                                        try {

                                            DatabaseInstance.getInstance(context).missionsDao().insertAllMissions(response.body());

                                            for(Mission mission : response.body()){
                                                for(Client client : mission.getClientsList()){
                                                    client.setMissionId(mission.getMissionId());
                                                    DatabaseInstance.getInstance(context).missionsDao().insertMissionClient(client);
                                                }
                                            }

                                            isRefreshedLiveData.postValue(true);
                                        }catch (Exception e){


                                            isRefreshedLiveData.postValue(false);
                                        }

                                    }

                                }

                            });

                        }else {
                            isRefreshedLiveData.postValue(false);

                        }


                    }else {
                        isRefreshedLiveData.postValue(false);
                    }




                }else {
                    isRefreshedLiveData.postValue(false);

                }

            }

            @Override
            public void onFailure(Call<List<Mission>> call, Throwable t) {
                isRefreshedLiveData.postValue(false);

            }

        });
        return isRefreshedLiveData;

    }



    public MutableLiveData<List<Mission>> getLocalMissions(Context context){
        MutableLiveData<List<Mission>> localMissionMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    localMissionMutableLiveData.postValue(DatabaseInstance.getInstance(context).missionsDao().getAllMissionsByUserId
                            (SessionManager.getInstance().getUSER_ID(context)));


                }catch (Exception e){
                    localMissionMutableLiveData.postValue(null);
                }
            }
        });

        return localMissionMutableLiveData;

    }

    public MutableLiveData<List<Client>> getLocalClientsByMissionId(Context context,Integer missionId){

        MutableLiveData<List<Client>> localClientsMutableLiveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    localClientsMutableLiveData.postValue(DatabaseInstance.getInstance(context).missionsDao().getAllClientsByMissionId(missionId));


                }catch (Exception e){
                    localClientsMutableLiveData.postValue(null);
                }
            }
        });

        return localClientsMutableLiveData;

    }

}
