package com.example.deliveryboy.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Repository.DemandeChargRepository;

import java.util.List;

public class DemandeChargViewModel {
    DemandeChargRepository demandeChargRepository;

    public DemandeChargViewModel() {
        demandeChargRepository = new DemandeChargRepository();
    }

    public MutableLiveData<Boolean> getProductsApi(Context context){
        return demandeChargRepository.getApiProductsAndInsertLocally(context);
    }


    public MutableLiveData<List<Produit>> getLocalProducts(Context context){
        return demandeChargRepository.getLocalProducts(context);
    }

}
