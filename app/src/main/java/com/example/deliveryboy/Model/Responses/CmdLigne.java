package com.example.deliveryboy.Model.Responses;

import com.google.gson.annotations.SerializedName;

public class CmdLigne {

    @SerializedName("Quantite")
    private int demandedQuantity;
    @SerializedName("Puht")

    private Double puht;

    @SerializedName("MHT")
    private Double demandedTotalPrice;

    @SerializedName("Conditionnement")
    private String articleConditionnement;

    @SerializedName("AR_Ref")

    private String articleReference;
    @SerializedName("AR_Design")
    private String articleDesignation;

    public CmdLigne(int demandedQuantity, Double puht, Double demandedTotalPrice, String articleConditionnement, String articleReference, String articleDesignation) {
        this.demandedQuantity = demandedQuantity;
        this.puht = puht;
        this.demandedTotalPrice = demandedTotalPrice;
        this.articleConditionnement = articleConditionnement;
        this.articleReference = articleReference;
        this.articleDesignation = articleDesignation;
    }


    public int getDemandedQuantity() {
        return demandedQuantity;
    }

    public void setDemandedQuantity(int demandedQuantity) {
        this.demandedQuantity = demandedQuantity;
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
                "demandedQuantity=" + demandedQuantity +
                ", puht=" + puht +
                ", demandedTotalPrice=" + demandedTotalPrice +
                ", articleConditionnement='" + articleConditionnement + '\'' +
                ", articleReference='" + articleReference + '\'' +
                ", articleDesignation='" + articleDesignation + '\'' +
                '}';
    }
}
