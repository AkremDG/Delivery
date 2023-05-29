package com.example.deliveryboy.Model;

public class Produit {
    private int imageProduit;
    private String nomProduit;
    private double prixProduit;
    private int quantiteProduit;
    private String typePromotion;

    public Produit(int imageProduit, String nomProduit, double prixProduit, int quantiteProduit, String typePromotoin) {
        this.imageProduit = imageProduit;
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.quantiteProduit = quantiteProduit;
        this.typePromotion = typePromotoin;
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
