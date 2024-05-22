package com.example.deliveryboy.Model.Requests;

import com.google.gson.annotations.SerializedName;

public class PointageItems {

    @SerializedName("idart")
    private int idArt;

    @SerializedName("DL_QteBC")
    private  int quantiteArticle;

    @SerializedName("DL_MontantTTC")
    private Double totalArticle;

    public PointageItems(){

    }

    public PointageItems(int idArt, int quantiteArticle, Double totalArticle) {
        this.idArt = idArt;
        this.quantiteArticle = quantiteArticle;
        this.totalArticle = totalArticle;
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public int getQuantiteArticle() {
        return quantiteArticle;
    }

    public void setQuantiteArticle(int quantiteArticle) {
        this.quantiteArticle = quantiteArticle;
    }

    public Double getTotalArticle() {
        return totalArticle;
    }

    public void setTotalArticle(Double totalArticle) {
        this.totalArticle = totalArticle;
    }

    @Override
    public String toString() {
        return "PointageItems{" +
                "idArt=" + idArt +
                ", quantiteArticle=" + quantiteArticle +
                ", totalArticle=" + totalArticle +
                '}';
    }
}
