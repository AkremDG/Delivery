package com.example.deliveryboy.Model.Responses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class CmdLigne {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idLocal")
    private int idLocal;

    @ColumnInfo(name = "demandeBoId")
    private int demandeBoId;

    @SerializedName("Quantite")
    @ColumnInfo(name = "demandedQuantity")
    private int demandedQuantity;

    @SerializedName("idart")
    @ColumnInfo(name = "idart")
    private int idart;


    @SerializedName("AS_QteSto")
    @ColumnInfo(name = "stock")
    private int stock;

    @ColumnInfo(name = "puht")
    @SerializedName("Puht")
    private Double puht;

    @ColumnInfo(name = "demandedTotalPrice")
    @SerializedName("MHT")
    private Double demandedTotalPrice;


    @ColumnInfo(name = "articleConditionnement")
    @SerializedName("Conditionnement")
    private String articleConditionnement;



    @ColumnInfo(name = "articleReference")
    @SerializedName("AR_Ref")
    private String articleReference;

    @ColumnInfo(name = "articleDesignation")
    @SerializedName("AR_Design")
    private String articleDesignation;


    public CmdLigne(){

    }

    public CmdLigne(int idLocal, int demandeBoId, int demandedQuantity, int idart, int stock, Double puht, Double demandedTotalPrice, String articleConditionnement, String articleReference, String articleDesignation) {
        this.idLocal = idLocal;
        this.demandeBoId = demandeBoId;
        this.demandedQuantity = demandedQuantity;
        this.idart = idart;
        this.stock = stock;
        this.puht = puht;
        this.demandedTotalPrice = demandedTotalPrice;
        this.articleConditionnement = articleConditionnement;
        this.articleReference = articleReference;
        this.articleDesignation = articleDesignation;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getDemandeBoId() {
        return demandeBoId;
    }

    public void setDemandeBoId(int demandeBoId) {
        this.demandeBoId = demandeBoId;
    }

    public int getDemandedQuantity() {
        return demandedQuantity;
    }

    public void setDemandedQuantity(int demandedQuantity) {
        this.demandedQuantity = demandedQuantity;
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPuht() {
        return puht;
    }

    public void setPuht(Double puht) {
        this.puht = puht;
    }

    public Double getDemandedTotalPrice() {
        return demandedTotalPrice;
    }

    public void setDemandedTotalPrice(Double demandedTotalPrice) {
        this.demandedTotalPrice = demandedTotalPrice;
    }

    public String getArticleConditionnement() {
        return articleConditionnement;
    }

    public void setArticleConditionnement(String articleConditionnement) {
        this.articleConditionnement = articleConditionnement;
    }

    public String getArticleReference() {
        return articleReference;
    }

    public void setArticleReference(String articleReference) {
        this.articleReference = articleReference;
    }

    public String getArticleDesignation() {
        return articleDesignation;
    }

    public void setArticleDesignation(String articleDesignation) {
        this.articleDesignation = articleDesignation;
    }

    @Override
    public String toString() {
        return "CmdLigne{" +
                "idLocal=" + idLocal +
                ", demandeBoId=" + demandeBoId +
                ", demandedQuantity=" + demandedQuantity +
                ", idart=" + idart +
                ", stock=" + stock +
                ", puht=" + puht +
                ", demandedTotalPrice=" + demandedTotalPrice +
                ", articleConditionnement='" + articleConditionnement + '\'' +
                ", articleReference='" + articleReference + '\'' +
                ", articleDesignation='" + articleDesignation + '\'' +
                '}';
    }
}

