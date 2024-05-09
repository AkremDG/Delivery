package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Produit implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localArticleId")
    private Integer localArticleId;


    @ColumnInfo(name ="boId")
    @SerializedName("UniqueID")
    private String boId;


    @SerializedName("AR_Ref")
    @ColumnInfo(name = "AR_Ref")
    private String AR_Ref;

    @SerializedName("AR_Design")
    @ColumnInfo(name = "AR_Design")
    private String AR_Design;


    @SerializedName("FA_CodeFamille")
    @ColumnInfo(name = "FA_CodeFamille")
    private String FA_CodeFamille;


    @SerializedName("conditions")
    @Ignore
    private List<ProduitCondition> articleConditionsList;



    public Produit(){

    }

    public Produit(Integer localArticleId, String boId, String AR_Ref, String AR_Design, String FA_CodeFamille, List<ProduitCondition> articleConditionsList) {
        this.localArticleId = localArticleId;
        this.boId = boId;
        this.AR_Ref = AR_Ref;
        this.AR_Design = AR_Design;
        this.FA_CodeFamille = FA_CodeFamille;
        this.articleConditionsList = articleConditionsList;
    }

    public Integer getLocalArticleId() {
        return localArticleId;
    }

    public void setLocalArticleId(Integer localArticleId) {
        this.localArticleId = localArticleId;
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

    public String getFA_CodeFamille() {
        return FA_CodeFamille;
    }

    public void setFA_CodeFamille(String FA_CodeFamille) {
        this.FA_CodeFamille = FA_CodeFamille;
    }

    public List<ProduitCondition> getArticleConditionsList() {
        return articleConditionsList;
    }

    public void setArticleConditionsList(List<ProduitCondition> articleConditionsList) {
        this.articleConditionsList = articleConditionsList;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "localArticleId=" + localArticleId +
                ", boId='" + boId + '\'' +
                ", AR_Ref='" + AR_Ref + '\'' +
                ", AR_Design='" + AR_Design + '\'' +
                ", FA_CodeFamille='" + FA_CodeFamille + '\'' +
                ", articleConditionsList=" + articleConditionsList +
                '}';
    }
}
