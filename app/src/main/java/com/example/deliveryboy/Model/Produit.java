package com.example.deliveryboy.Model;

import java.io.Serializable;

public class Produit implements Serializable {
    private int imageProduit;
    private String nomProduit;
    private double prixProduit;
    private int quantiteProduit;
    private String typePromotion;
    private Boolean dispProduit;

    private double totalPrix;


    public Produit(int imageProduit, String nomProduit, double prixProduit, int quantiteProduit, String typePromotoin, Boolean dispProduit, double
                   totalPrix) {
        this.imageProduit = imageProduit;
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.quantiteProduit = quantiteProduit;
        this.typePromotion = typePromotoin;
        this.dispProduit=dispProduit;
        this.totalPrix=totalPrix;
    }

    public double getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(double totalPrix) {
        this.totalPrix = totalPrix;
    }

    public Boolean getDispProduit() {
        return dispProduit;
    }

    public void setDispProduit(Boolean dispProduit) {
        this.dispProduit = dispProduit;
    }

    public int getImageProduit() {
        return imageProduit;
    }

    public void setImageProduit(int imageProduit) {
        this.imageProduit = imageProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public double getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public String getTypePromotion() {
        return typePromotion;
    }

    public void setTypePromotion(String typePromotion) {
        this.typePromotion = typePromotion;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "imageProduit=" + imageProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", prixProduit=" + prixProduit +
                ", quantiteProduit=" + quantiteProduit +
                ", typePromotion='" + typePromotion + '\'' +
                '}';
    }
}
