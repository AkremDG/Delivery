package com.example.deliveryboy.Model;

public class Commande {

    private User user;
    private String idCommande;
    private double prixCommande;

    public Commande(User user, String idCommande, double prixCommande) {
        this.user = user;
        this.idCommande = idCommande;
        this.prixCommande = prixCommande;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(double prixCommande) {
        this.prixCommande = prixCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "user=" + user +
                ", idCommande='" + idCommande + '\'' +
                ", prixCommande=" + prixCommande +
                '}';
    }
}
