package com.example.deliveryboy.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.Model.Responses.LocalPriceAndQuantity;
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


    public MutableLiveData<List<ProduitCondition>> getLocalProductsConditions(Context context, String idBo){
        return demandeChargRepository.getLocalProductConditionByProductBoId(context, idBo);
    }

    public MutableLiveData<ProduitCondition> getLocalPriceByIdAndProductId(Context context, String idBo, String ecEnumere){
        return demandeChargRepository.getLocalPriceByIdAndProductId(context,idBo, ecEnumere);
    }

    public MutableLiveData<Boolean> sendDemandApi(Context context, Demande demande){
        return demandeChargRepository.sendDemandApi(context, demande);
    }
    public MutableLiveData<Boolean> getDemandesApi(Context context){
        return demandeChargRepository.getAllDemandesApiAndInsertLocally(context);
    }

    public MutableLiveData<List<GETDemandeChargementRes>> getLocalDemandes(Context context){
        return demandeChargRepository.getAllLocalDemandes(context);
    }
}
