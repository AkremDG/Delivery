package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SelectedProduit implements Serializable {


    private Integer localArticleId;

    private Double unitProductPrice;

    private Double selectedProductPrice;


    private Integer selectedProductQuantity;


    private String boId;


    private String AR_Ref;


    private String AR_Design;


    private List<ProduitCondition> articleConditionsList;

    private List<String> articlesConditionsStrings ;

    private Double selectedProductStock;

    private Double selectedProductTotalPrice;


    public SelectedProduit(Integer localArticleId, Double unitProductPrice, Double selectedProductPrice, Integer selectedProductQuantity, String boId, String AR_Ref, String AR_Design, List<ProduitCondition> articleConditionsList, List<String> articlesConditionsStrings, Double selectedProductStock, Double selectedProductTotalPrice) {
        this.localArticleId = localArticleId;
        this.unitProductPrice = unitProductPrice;
        this.selectedProductPrice = selectedProductPrice;
        this.selectedProductQuantity = selectedProductQuantity;
        this.boId = boId;
        this.AR_Ref = AR_Ref;
        this.AR_Design = AR_Design;
        this.articleConditionsList = articleConditionsList;
        this.articlesConditionsStrings = articlesConditionsStrings;
        this.selectedProductStock = selectedProductStock;
        this.selectedProductTotalPrice = selectedProductTotalPrice;
    }

    public Integer getLocalArticleId() {
        return localArticleId;
    }

    public void setLocalArticleId(Integer localArticleId) {
        this.localArticleId = localArticleId;
    }

    public Double getUnitProductPrice() {
        return unitProductPrice;
    }

    public void setUnitProductPrice(Double unitProductPrice) {
        this.unitProductPrice = unitProductPrice;
    }

    public Double getSelectedProductPrice() {
        return selectedProductPrice;
    }

    public void setSelectedProductPrice(Double selectedProductPrice) {
        this.selectedProductPrice = selectedProductPrice;
    }

    public Integer getSelectedProductQuantity() {
        return selectedProductQuantity;
    }

    public void setSelectedProductQuantity(Integer selectedProductQuantity) {
        this.selectedProductQuantity = selectedProductQuantity;
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public String getAR_Design() {
        return AR_Design;
    }

    public void setAR_Design(String AR_Design) {
        this.AR_Design = AR_Design;
    }

    public List<ProduitCondition> getArticleConditionsList() {
        return articleConditionsList;
    }

    public void setArticleConditionsList(List<ProduitCondition> articleConditionsList) {
        this.articleConditionsList = articleConditionsList;
    }

    public List<String> getArticlesConditionsStrings() {
        return articlesConditionsStrings;
    }

    public void setArticlesConditionsStrings(List<String> articlesConditionsStrings) {
        this.articlesConditionsStrings = articlesConditionsStrings;
    }

    public Double getSelectedProductStock() {
        return selectedProductStock;
    }

    public void setSelectedProductStock(Double selectedProductStock) {
        this.selectedProductStock = selectedProductStock;
    }

    public Double getSelectedProductTotalPrice() {
        return selectedProductTotalPrice;
    }

    public void setSelectedProductTotalPrice(Double selectedProductTotalPrice) {
        this.selectedProductTotalPrice = selectedProductTotalPrice;
    }
}
