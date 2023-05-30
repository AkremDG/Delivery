package com.example.deliveryboy.Model;

import java.util.List;

public class Panier {
    private String idPanier;
    private User user;
    private List<Produit> listProduit;
    private double totalPanier;

    public Panier(String idPanier, User user, List<Produit> listProduit, double totalPanier) {
        this.idPanier = idPanier;
        this.user = user;
        this.listProduit = listProduit;
        this.totalPanier = totalPanier;
    }

    public String getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(String idPanier) {
        this.idPanier = idPanier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(List<Produit> listProduit) {
        this.listProduit = listProduit;
    }

    public double getTotalPanier() {
        return totalPanier;
    }

    public void setTotalPanier(double totalPanier) {
        this.totalPanier = totalPanier;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier='" + idPanier + '\'' +
                ", user=" + user +
                ", listProduit=" + listProduit +
                ", totalPanier=" + totalPanier +
                '}';
    }
}
