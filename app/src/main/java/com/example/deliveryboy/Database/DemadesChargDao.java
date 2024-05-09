package com.example.deliveryboy.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;

import java.util.List;

@Dao
public interface DemadesChargDao {

    @Insert
    void insertAllProducts(List<Produit> produitList);

    @Query("DELETE FROM Produit")
    void deleteAllProducts();

    @Query("SELECT * FROM Produit")
    List<Produit> getAllProducts();


    @Insert
    void insertProductCondition(ProduitCondition produitCondition);

    @Query("SELECT * FROM ProduitCondition where produitBoId=:boId")
    List<ProduitCondition> getAllProductsConditionsByBoid(String boId);

}
