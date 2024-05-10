package com.example.deliveryboy.Adapters;

import com.example.deliveryboy.Model.CustomProduit;
import com.example.deliveryboy.Model.Produit;

public interface RvInterface {
    void onItemClick(int position);
    void onCommanderClick(int position);

    void onItemClickReturnObject(CustomProduit customProduit, int position);

}
