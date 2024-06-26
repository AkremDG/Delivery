package com.example.deliveryboy.Repository;

import android.content.Context;
import android.se.omapi.Session;
import android.util.Log;
import android.widget.Toast;

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

                Log.i("VVVVVVVV", "0 OK: ");

                if(response.isSuccessful()){

                    Log.i("RESULTTTTTTTTTTTTTTT", response.body().toString());

                    Log.i("VVVVVVVV", "1 OK: ");

                    if(response.body()!=null){
                        Log.i("VVVVVVVV", "2 OK: ");

                        if(response.body().size()>0)
                        {


                            Log.i("VVVVVVVV", "3 OK");

                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Boolean isDeleted = false;

                                    try {
                                        DatabaseInstance.getInstance(context).missionsDao().deleteAllMissions();
                                        DatabaseInstance.getInstance(context).missionsDao().deleteAllClientsMissions();

                                        isDeleted = true;

                                    }catch (Exception e){

                                        isDeleted = false;
                                    }


                                    if(isDeleted==true) {
                                        try {

                                            DatabaseInstance.getInstance(context).missionsDao().insertAllMissions(response.body());


                                            for(Mission mission : response.body()){


                                                if(mission.getClientsList()!=null && mission.getClientsList().size()>0){

                                                    for(Client client : mission.getClientsList()){

                                                        client.setMissionId(mission.getMissionId());
                                                        DatabaseInstance.getInstance(context).missionsDao().insertMissionClient(client);


                                                    }

                                                }

                                            }

                                            isRefreshedLiveData.postValue(true);
                                        }catch (Exception e){

                                            isRefreshedLiveData.postValue(false);
                                        }

                                    }else {
                                        isRefreshedLiveData.postValue(false);

                                    }

                                }

                            });

                        }else {
                            Log.i("VVVVVVVV", "3 NO: ");

                            isRefreshedLiveData.postValue(false);

                        }


                    }else {
                        Log.i("VVVVVVVV", "2 NO: ");

                        isRefreshedLiveData.postValue(false);
                    }




                }else {
                    Log.i("VVVVVVVV", "1 NO: ");

                    isRefreshedLiveData.postValue(false);

                }

            }

            @Override
            public void onFailure(Call<List<Mission>> call, Throwable t) {
                Log.i("VVVVVVVV", "FAILURE YES RETARD IS HERE: ");

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

                    Log.i("HEREEEE", "OKKKKKKKK: ");
                }catch (Exception e){

                    Log.i("HEREEEE", "NOOOOOOO: "+String.valueOf(e.getMessage()));

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
