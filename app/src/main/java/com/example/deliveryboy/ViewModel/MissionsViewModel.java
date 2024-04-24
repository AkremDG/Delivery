package com.example.deliveryboy.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Repository.MissionsRepository;

import java.util.List;

public class MissionsViewModel {
    MissionsRepository missionsRepository;

    public MissionsViewModel() {
        missionsRepository = new MissionsRepository();
    }

    public MutableLiveData<Boolean> getMissionsApi(Context context){
         return missionsRepository.getApiMissionsAndInsertLocally(context);
    }

    public MutableLiveData<List<Mission>> getLocalMissions(Context context){
        return missionsRepository.getLocalMissions(context);
    }
}
