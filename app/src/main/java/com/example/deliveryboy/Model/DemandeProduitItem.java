package com.example.deliveryboy.Model;

import com.google.gson.annotations.SerializedName;

public class DemandeProduitItem {

    @SerializedName("id")
    private int id;


    @SerializedName("idArt")
    private int idArt;


    @SerializedName("priceU")
    private Double priceU;

    @SerializedName("quantity")
    private int quantity;

    public DemandeProduitItem(int id, int idArt, Double priceU, int quantity) {
        this.id = id;
        this.idArt = idArt;
        this.priceU = priceU;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public Double getPriceU() {
        return priceU;
    }

    public void setPriceU(Double priceU) {
        this.priceU = priceU;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DemandeProduitItem{" +
                "id=" + id +
                ", idArt=" + idArt +
                ", priceU=" + priceU +
                ", quantity=" + quantity +
                '}';
    }
}
